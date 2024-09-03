package com.intuit.foodorderingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "user_refresh_token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRefreshTokenEntity extends BaseDateEntity{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private UsersEntity usersEntity;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name="is_active")
    private Boolean isActive;


}
