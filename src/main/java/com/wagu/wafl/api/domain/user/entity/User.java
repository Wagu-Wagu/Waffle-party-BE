package com.wagu.wafl.api.domain.user.entity;

import com.wagu.wafl.api.common.entity.BaseEntity;
import com.wagu.wafl.api.domain.comment.entity.Comment;
import com.wagu.wafl.api.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"User\"")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickName;

    private String email;

    private String userImage;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="auth_provider_id")
    private AuthProvider authProvider;

    public void setAuthProvider(AuthProvider authProvider) {
        if(this.authProvider != null) {
            this.authProvider = null;
        }
        this.authProvider = authProvider;
        authProvider.setUser(this);
    }

    @Builder
    public User(AuthProvider authProvider){
        this.userImage = "";
        setAuthProvider(authProvider);
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

}
