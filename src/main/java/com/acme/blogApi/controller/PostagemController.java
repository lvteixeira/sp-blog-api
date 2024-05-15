package com.acme.blogApi.controller;

import com.acme.blogApi.dto.PostagemDTO;
import com.acme.blogApi.service.PostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("postagem")
public class PostagemController {
    private PostagemService postagemService;

    @Autowired
    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }

    @GetMapping
    public ResponseEntity<List<PostagemDTO>> getAllPostagens() {
        List<PostagemDTO> postagens = postagemService.getAllOrderedByCreationDateTime();
        return ResponseEntity.ok(postagens);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostagemDTO> getPostagemById(@PathVariable("id") Long id) {
        return postagemService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostagemDTO> createPostagem(@RequestBody PostagemDTO createPayload) {
        PostagemDTO createdPostagem = postagemService.create(createPayload);
        return new ResponseEntity<>(createdPostagem, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePostagem(@PathVariable("id") Long id, @RequestBody PostagemDTO updatePayload) {
        boolean updated = postagemService.update(id, updatePayload);
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePostagem(@PathVariable("id") Long id) {
        boolean deleted = postagemService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}