package com.wagu.wafl.api.domain.comment.service;

import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.domain.comment.dto.request.CreateCommentReplyDTO;
import com.wagu.wafl.api.domain.comment.dto.request.CreatePostCommentDTO;
import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.comment.repository.CommentRepository;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.post.repository.PostRepository;
import com.wagu.wafl.api.domain.user.entity.User;
import com.wagu.wafl.api.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
        Comment commentReplyComment = findComment(request.commentId());

        Comment newComment = CreateCommentReplyDTO.
                toEntity(
                        commentReplyCreateUser,
                        commentReplyPost,
                        commentReplyComment,
                        request.content(),
                        request.isSecret());
        commentRepository.save(newComment);
        commentReplyPost.upCommentCount();
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
