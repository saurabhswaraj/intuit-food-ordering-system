package com.intuit.foodorderingsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseDateEntity {

    @Column(name = "generated_at")
    private ZonedDateTime generatedAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;


    @PrePersist
    public void onPrePersist() {
        this.setGeneratedAt(ZonedDateTime.now());
        this.setUpdatedAt(ZonedDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdatedAt(ZonedDateTime.now());
    }
}
