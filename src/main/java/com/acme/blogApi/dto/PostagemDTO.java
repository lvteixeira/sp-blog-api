package com.acme.blogApi.dto;

public class PostagemDTO {
    private Long id;
    private String content;
    private boolean isEdited;

    public PostagemDTO() {}

    public PostagemDTO(Long id, String content, boolean isEdited) {
        this.id = id;
        this.content = content;
        this.isEdited = isEdited;
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
}