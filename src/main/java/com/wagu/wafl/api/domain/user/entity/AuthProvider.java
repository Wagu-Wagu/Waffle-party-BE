package com.wagu.wafl.api.domain.user.entity;


import com.wagu.wafl.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AuthProvider extends BaseEntity {

    @Id
    @Column(name = "auth_provider_id")
    private String authProviderId;

    private String providerType;

//    @OneToOne(fetch = FetchType.LAZY) // todo 논의
//    @JoinColumn(name = "user_id")
//    private User user;
}
