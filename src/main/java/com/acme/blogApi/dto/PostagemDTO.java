package com.acme.blogApi.dto;

public class PostagemDTO {
    private Long id;
    private String content;

    public PostagemDTO() {}

    public PostagemDTO(String content, Long id) {
        this.id = id;
        this.content = content;
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
}