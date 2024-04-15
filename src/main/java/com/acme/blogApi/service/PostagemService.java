package com.acme.blogApi.service;

import com.acme.blogApi.dto.PostagemDTO;
import com.acme.blogApi.dto.UserDTO;
import com.acme.blogApi.exception.UserCreationException;
import com.acme.blogApi.model.PostagemEntity;
import com.acme.blogApi.model.UserEntity;
import com.acme.blogApi.repository.PostagemRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostagemService {
    private PostagemRepository postagemRepository;

    private ModelMapper modelMapper;

    @Autowired
    public PostagemService(PostagemRepository postagemRepository, ModelMapper modelMapper) {
        this.postagemRepository = postagemRepository;
        this.modelMapper = modelMapper;

        TypeMap<PostagemDTO, PostagemEntity> typeMap = modelMapper.createTypeMap(PostagemDTO.class, PostagemEntity.class);

        typeMap.addMappings(mapper -> mapper.skip(PostagemEntity::setId));
    }

    public PostagemDTO convertEntityToDTO(PostagemEntity entity) {
        return modelMapper.map(entity, PostagemDTO.class);
    }

    public PostagemEntity convertDTOToEntity(PostagemDTO dto) {
        return modelMapper.map(dto, PostagemEntity.class);
    }

    public List<PostagemDTO> convertEntityListToDTOList(List<PostagemEntity> entityList) {
        java.lang.reflect.Type targetListType = new TypeToken<List<PostagemDTO>>() {
        }.getType();
        return modelMapper.map(entityList, targetListType);
    }

    public List<PostagemEntity> getAll() {
        return postagemRepository.findAll();
    }

    public Optional<PostagemEntity> findById(Long id) {
        return postagemRepository.findById(id);
    }

    public void create(PostagemEntity postagem) throws Exception {
        try {
            postagemRepository.save(postagem);
            postagemRepository.flush();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar usuário");
        }
    }

    public void update(PostagemEntity updated, Long id) {
        List<PostagemEntity> postagens = this.getAll();
        postagens.stream()
                .filter(postagem -> postagem.getId() == id)
                .findFirst()
                .ifPresent(postagem -> {
                    postagem.setContent(updated.getContent());
                    postagemRepository.flush();
                });
    }
}
