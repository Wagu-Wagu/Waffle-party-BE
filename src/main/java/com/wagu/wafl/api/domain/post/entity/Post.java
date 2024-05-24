package com.wagu.wafl.api.domain.post.entity;

import com.wagu.wafl.api.common.entity.BaseEntity;
import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.user.entity.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "\"Post\"")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "photoes")
    private String photoes;

    @Column(name = "ott_tag")
    @Enumerated(EnumType.STRING)
    private OttTag ottTag;

    @Column(name = "thumb_nail")
    private String thumbNail;

    @Column(name = "comment_count")
    private Long commentCount = 0L;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(User user, String title, String content, String photoes, OttTag ottTag, String thumbNail) {
        setUser(user);
        this.title = title;
        this.content = content;
        this.photoes = photoes;
        this.ottTag = ottTag;
        this.thumbNail = thumbNail;
    }

    public void setUser(User user) {
        if (this.user!=null) {
            user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void upCommentCount() {
        this.commentCount += 1;
    }
}
