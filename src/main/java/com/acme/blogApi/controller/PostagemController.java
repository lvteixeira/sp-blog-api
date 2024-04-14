package com.acme.blogApi.controller;

import com.acme.blogApi.dto.PostagemDTO;
import com.acme.blogApi.model.PostagemEntity;
import com.acme.blogApi.service.PostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        List<PostagemEntity> postagens = postagemService.getAll();
        List<PostagemDTO> dtos = postagemService.convertEntityListToDTOList(postagens);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostagemDTO> getPostagemById(@PathVariable("id") Long id) {
        Optional<PostagemEntity> postagem = postagemService.findById(id);

        if(postagem.isPresent()) {
            PostagemDTO dto = postagemService.convertEntityToDTO(postagem.get());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(dto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<PostagemDTO> createPostagem(@RequestBody PostagemDTO createPayload) throws Exception {
        PostagemEntity obj = postagemService.convertDTOToEntity(createPayload);
        postagemService.create(obj);
        PostagemDTO createdPostagem = postagemService.convertEntityToDTO(obj);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPostagem);
    }
}

