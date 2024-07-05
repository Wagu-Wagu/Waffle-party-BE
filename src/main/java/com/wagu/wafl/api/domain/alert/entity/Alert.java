package com.wagu.wafl.api.domain.alert.entity;

import com.wagu.wafl.api.common.entity.BaseEntity;
import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "\"Alert\"")
public class Alert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "alert_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "new_alert_count")
    private Long newAlertCount;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    @Builder
    public Alert(User user, Post post, Comment comment, AlertType alertType) {
        setUser(user);
        this.post = post;
        this.comment = comment;
        this.newAlertCount = 1L;
        this.alertType = alertType;
    }

    public void setUser(User user) {
        if (this.user!=null) {
            user.getAlerts().remove(this);
        }
        this.user = user;
        user.getAlerts().add(this);
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public void setNewAlertCount(Long newAlertCount) {this.newAlertCount = newAlertCount; }

    public void plusNewAlertCount() {
        this.newAlertCount++;
    }
}
