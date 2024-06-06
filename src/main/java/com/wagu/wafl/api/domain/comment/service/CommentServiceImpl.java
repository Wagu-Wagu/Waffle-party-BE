package com.wagu.wafl.api.domain.comment.service;

import com.wagu.wafl.api.common.exception.CommentException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
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

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public void createPostComment(Long userId, CreatePostCommentDTO request) {
        User commentCreateUser = findUser(userId);
        Post commentPost = findPost(request.postId());

        Comment newComment = CreatePostCommentDTO.
                toEntity(commentCreateUser, commentPost, request.content(), request.isSecret());
        commentRepository.save(newComment);
        commentPost.upCommentCount();
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
}
