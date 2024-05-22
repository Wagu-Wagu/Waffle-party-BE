package com.wagu.wafl.api.domain.comment.entity;

import com.wagu.wafl.api.common.entity.BaseEntity;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.user.entity.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "\"Comment\"")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> subComments = new ArrayList<>();

    @Column(name = "is_secret")
    private Boolean isSecret = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public void setPost(Post post) {
        if (this.post != null) {
            post.getComments().remove(this);
        }
        this.post = post;
        post.getComments().add(this);
    }

    public void setParentComment(Comment parentComment) {
        if(this.parentComment!=null) {
            parentComment.getSubComments().remove(this);
        }
        parentComment.getSubComments().add(this);
    }

    @Builder
    Comment(User user, Post post, String content, Comment parentComment, boolean isSecret) {
        this.user = user;
        this.content = content;
        if(Objects.isNull(parentComment)) {
            this.parentComment = null;
        }
        else{
            setParentComment(parentComment);
        }
        setPost(post);
        this.isSecret = isSecret;
    }

}
