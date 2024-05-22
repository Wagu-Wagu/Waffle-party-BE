package com.wagu.wafl.api.domain.user.entity;

import com.wagu.wafl.api.common.entity.BaseEntity;
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
@Table(name = "\"User\"")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickName;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="auth_provider_id")// todo - 논의
    private AuthProvider authProvider;

    @Builder
    public User(){}
    // 연관관계 아직 생성 안함 Todo
}
