package com.wagu.wafl.api.domain.alert.entity;

import com.wagu.wafl.api.common.entity.BaseEntity;
import com.wagu.wafl.api.domain.post.entity.Post;
import com.wagu.wafl.api.domain.user.User;
import jakarta.persistence.*;
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
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "comment_id")
    private Post commentId;

    @Column(name = "is_read")
    private Boolean isRead = false;
}