package com.wagu.wafl.api.domain.comment.entity;

import com.wagu.wafl.api.common.entity.BaseEntity;
import com.wagu.wafl.api.domain.alert.entity.Alert;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.user.entity.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

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

    @SQLRestriction("is_active <> 'true'")
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> subComments = new ArrayList<>();

    @Column(name = "is_secret")
    private Boolean isSecret = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToOne(mappedBy = "comment")
    private Alert alert;

    public void setPost(Post post) {
        if (this.post != null) {
            post.getComments().remove(this);
        }
        this.post = post;
        post.getComments().add(this);
    }

    public void setUser(User user) {
        if(this.user != null) {
            user.getComments().remove(this);
        }
        this.user = user;
        user.getComments().add(this);
    }

    public void setParentComment(Comment parentComment) {
        if(this.parentComment!=null) {
            parentComment.getSubComments().remove(this);
        }
        this.parentComment = parentComment;
        parentComment.getSubComments().add(this);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsSecret(boolean isSecret) {
        this.isSecret = isSecret;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Builder
    Comment(User user, Post post, String content, Comment parentComment, boolean isSecret) {
        setUser(user);
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
