package com.example.NoteBook.DTO;

import java.time.LocalDateTime;

public class AppUserViewDTO {
    private long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;

    public AppUserViewDTO() {

    }

    public AppUserViewDTO(String email, String name, LocalDateTime createdAt) {
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
    }

    // getters
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
