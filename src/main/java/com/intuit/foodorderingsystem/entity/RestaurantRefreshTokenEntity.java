package com.intuit.foodorderingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity(name = "restaurant_refresh_token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRefreshTokenEntity extends BaseDateEntity{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name="is_active")
    private Boolean isActive;


}
