package com.intuit.foodorderingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "restaurant_authentication")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantAuthenticationEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

}
