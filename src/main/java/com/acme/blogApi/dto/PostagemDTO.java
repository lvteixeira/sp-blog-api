package com.acme.blogApi.dto;

import com.acme.blogApi.model.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public class PostagemDTO {
    private Long id;
    private String content;
    private boolean isEdited;
    private Long userId;
    private String username;
    private LocalDateTime createdAt;

    private List<Long> curtidas;

    public PostagemDTO() {}

    public PostagemDTO(Long id, String content, boolean isEdited, Long userId, String username, LocalDateTime createdAt, List<Long> curtidas) {
        this.id = id;
        this.content = content;
        this.isEdited = isEdited;
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.curtidas = curtidas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        this.isEdited = edited;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Long> getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(List<Long> curtidas) {
        this.curtidas = curtidas;
    }
}