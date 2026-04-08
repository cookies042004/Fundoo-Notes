package com.fundoonotes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    // [Prajwal]:UC7:Primary key mapping for Notes table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000) // Notes can be extremely long, increasing the MySQL varchar limit
    private String description;

    // [Prajwal]:UC7:Note lifecycle states as requested in your architecture guide
    private boolean isPinned = false;
    private boolean isArchived = false;
    private boolean isTrashed = false;
    
    private String color = "#FFFFFF"; // Standard default white background

    // [Prajwal]:UC7:Foreign Key. Using Long userId completely decouples notes from the User entity avoiding infinite loops
    @Column(nullable = false, name = "user_id")
    private Long userId;

    // Automatically assigns timestamp upon insertion
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Automatically bumps timestamp upon any @Entity update
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
