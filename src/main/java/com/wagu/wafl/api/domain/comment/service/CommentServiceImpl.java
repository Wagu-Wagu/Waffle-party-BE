package com.wagu.wafl.api.domain.comment.service;

import com.wagu.wafl.api.common.exception.AlertException;
import com.wagu.wafl.api.common.exception.CommentException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.domain.alert.entity.Alert;
import com.wagu.wafl.api.domain.alert.entity.AlertType;
import com.wagu.wafl.api.domain.alert.repository.AlertRepository;
import com.wagu.wafl.api.domain.comment.dto.request.CreateCommentReplyDTO;
import com.wagu.wafl.api.domain.comment.dto.request.CreatePostCommentDTO;
import com.wagu.wafl.api.domain.comment.dto.request.EditCommentRequestDTO;
import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.comment.repository.CommentRepository;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.post.repository.PostRepository;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AlertRepository alertRepository;
    @Transactional
    @Override
    public void createPostComment(Long userId, CreatePostCommentDTO request) {
        User commentCreateUser = findUser(userId);
        Post commentPost = findPost(request.postId());

        Comment newComment = CreatePostCommentDTO.
                toEntity(commentCreateUser, commentPost, request.content(), request.isSecret());
        commentRepository.save(newComment);
        commentPost.upCommentCount();

        updateAlert(newComment, AlertType.COMMENT);
    }

    @Transactional
    @Override
    public void createCommentReply(Long userId, CreateCommentReplyDTO request) {
        User commentReplyCreateUser = findUser(userId);
        Post commentReplyPost = findPost(request.postId());
        Comment commentReplyComment = findComment(request.parentCommentId());

        Comment newComment = CreateCommentReplyDTO.
                toEntity(
                        commentReplyCreateUser,
                        commentReplyPost,
                        commentReplyComment,
                        request);
        commentRepository.save(newComment);
        commentReplyPost.upCommentCount();

        updateAlert(commentReplyComment, AlertType.REPLY);
    }

    @Transactional
    @Override
    public void editComment(Long userId, EditCommentRequestDTO request) {
        Post post = findPost(request.postId());

        Comment comment = findComment(request.commentId());
        validatePostComment(post, request.commentId());
        validateCommentOwner(request.commentUserId(), comment);

        comment.setContent(request.content());
        comment.setIsSecret(request.isSecret());
    }

    @Transactional
    @Override
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = findComment(commentId);
        validateCommentOwner(userId, comment);
        comment.setIsActive(false);
        Post post = comment.getPost();
        post.downCommentCount();
    }

    private void validatePostComment(Post post, Long commentId) {
        boolean commentExists = post.getComments().stream()
                .anyMatch(comment -> comment.getId().equals(commentId));
        if (!commentExists) {
            throw new CommentException(ExceptionMessage.IS_NOT_POST_COMMENT.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void validateCommentOwner(Long userId, Comment comment) {
        if(!(comment.getUser().getId()==userId)) {
            throw new CommentException(ExceptionMessage.IS_NOT_COMMENT_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_POST.getMessage()));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_COMMENT.getMessage()));
    }

    private void updateAlert(Comment comment, AlertType alertType) {
        try{
            if (isFirstAlert(comment, alertType)) {
                createAlert(comment, alertType);
            } else {
                updateExistingAlert(comment, alertType);
            }
        } catch (AlertException e) {
            throw new AlertException(ExceptionMessage.NOT_CREATE_ALERT.getMessage(), HttpStatus.BAD_REQUEST); // 어떤 상태코드를 줘야하지?
        }
    }

    private boolean isFirstAlert(Comment comment, AlertType alertType) {
        boolean isFirst = false;
        User user = comment.getPost().getUser();
        List<Alert> alerts = user.getAlerts();
        if (Objects.equals(alertType, AlertType.COMMENT)) {
            Optional<Alert> targetAlert = alerts.stream()
                    .filter(alert -> alert.getPost().equals(comment.getPost()) && alert.getAlertType().equals(AlertType.COMMENT))
                    .findAny();
            if(targetAlert.isEmpty()) {
                isFirst = true;
            }
        }
        if (Objects.equals(alertType, AlertType.REPLY)){
            Optional<Alert> targetAlert = alerts.stream()
                    .filter(alert -> alert.getPost().equals(comment.getPost()) && alert.getAlertType().equals(AlertType.REPLY))
                    .findAny();
            if(targetAlert.isEmpty()) {
                isFirst = true;
            }
        }
        return isFirst;
    }

    private void createAlert(Comment comment, AlertType alertType) {
        Post post = comment.getPost();
        if (Objects.equals(alertType, AlertType.COMMENT)) {
            alertRepository.save(Alert.builder()
                    .user(post.getUser())
                    .post(post)
                    .content(post.getTitle())
                    .alertType(alertType)
                    .build());
        }
        if (Objects.equals(alertType, AlertType.REPLY)) {
            alertRepository.save(Alert.builder()
                    .user(post.getUser())
                    .post(post)
                    .content(comment.getContent())
                    .alertType(alertType)
                    .build());
        }
    }

    private void updateExistingAlert(Comment comment, AlertType alertType) {
        if (Objects.equals(alertType, AlertType.COMMENT)) {
            Post post = comment.getPost();
            List<Alert> alerts = post.getUser().getAlerts();
            Optional<Alert> targetAlert = alerts.stream()
                    .filter(alert -> alert.getPost().equals(post) && alert.getAlertType().equals(AlertType.COMMENT))
                    .findAny();

            targetAlert.ifPresent(alert -> {
                if (alert.getIsRead()) {
                    alert.setIsRead(false);
                    alert.setNewAlertCount(0L);
                }
                alert.plusNewAlertCount();
            });
        }

        if(Objects.equals(alertType, AlertType.REPLY)) {
            Post post = comment.getPost();
            List<Alert> alerts = post.getUser().getAlerts();
            Optional<Alert> targetAlert = alerts.stream()
                    .filter(alert -> alert.getPost().equals(post) && alert.getAlertType().equals(AlertType.REPLY))
                    .findAny();

            targetAlert.ifPresent(alert -> {
                if (alert.getIsRead()) {
                    alert.setIsRead(false);
                    alert.setNewAlertCount(0L);
                }
                alert.plusNewAlertCount();
            });
        }

    }

}
