package com.intuit.foodorderingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "pin_code", nullable = false)
    private String pinCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "contact_number", nullable = false, unique = true)
    private String contactNumber;

    @OneToMany(mappedBy = "restaurantEntity", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<RestaurantMenuEntity> restaurantMenuEntities;

    @OneToMany(mappedBy = "restaurantEntity", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<RestaurantCapacityEntity> restaurantCapacityEntity;


}
