package com.wagu.wafl.api.domain.user.entity;

import com.wagu.wafl.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickName;

    private String email;

//    @OneToOne(mappedBy = "user") // todo - 논의
//    private AuthProvider authProvider;

    // 연관관계 아직 생성 안함 Todo
}
