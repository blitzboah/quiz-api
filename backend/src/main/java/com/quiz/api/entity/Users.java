package com.quiz.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId( Long userId) {
        this.userId = userId;
    }
}
