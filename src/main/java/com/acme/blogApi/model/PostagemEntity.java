package com.acme.blogApi.model;

import jakarta.persistence.*;

@Entity
@Table(name="postagens")
public class PostagemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }
}
