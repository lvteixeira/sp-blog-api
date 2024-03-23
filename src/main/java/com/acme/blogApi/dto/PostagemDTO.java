package com.acme.blogApi.dto;

public class PostagemDTO {
    private String content;

    public PostagemDTO() {}

    public PostagemDTO(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}