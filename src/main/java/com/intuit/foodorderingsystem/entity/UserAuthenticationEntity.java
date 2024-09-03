package com.intuit.foodorderingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "user_authentication")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private UsersEntity usersEntity;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Column(name = "refresh_token")
    private String refreshToken;


}
