package com.wagu.wafl.api.domain.user.entity;


import com.wagu.wafl.api.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"AuthProvider\"")
public class AuthProvider extends BaseEntity {

    @Id
    @Column(name = "auth_provider_id")
    private String id;

    private String providerType;

    @OneToOne(mappedBy = "authProvider") // todo 논의
    private User user;

}
