package com.acme.blogApi.service;

import com.acme.blogApi.dto.PostagemDTO;
import com.acme.blogApi.model.PostagemEntity;
import com.acme.blogApi.model.UserEntity;
import com.acme.blogApi.repository.PostagemRepository;
import com.acme.blogApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostagemService {
    private PostagemRepository postagemRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Autowired
    public PostagemService(PostagemRepository postagemRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postagemRepository = postagemRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

        TypeMap<PostagemDTO, PostagemEntity> typeMap = modelMapper.createTypeMap(PostagemDTO.class, PostagemEntity.class);

        typeMap.addMappings(mapper -> mapper.skip(PostagemEntity::setId));
    }

    public PostagemDTO convertEntityToDTO(PostagemEntity entity) {
        PostagemDTO dto = modelMapper.map(entity, PostagemDTO.class);
        dto.setUserId(entity.getUser().getId());
        dto.setUsername(entity.getUser().getUsername());
        dto.setCurtidas(entity.getCurtidas());
        return dto;
    }

    public PostagemEntity convertDTOToEntity(PostagemDTO dto) {
        PostagemEntity entity = modelMapper.map(dto, PostagemEntity.class);
        if(dto.getUserId() != null) {
            UserEntity user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id " + dto.getUserId()));
            entity.setUser(user);
        }
        return entity;
    }

    public List<PostagemDTO> getAll() {
        List<PostagemEntity> entities = postagemRepository.findAll();
        return entities.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public List<PostagemDTO> getAllOrderedByCreationDateTime() {
        List<PostagemEntity> entities = postagemRepository.findAllByOrderByCreatedAtDesc();
        return entities.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PostagemDTO> findById(Long id) {
        return postagemRepository.findById(id)
                .map(this::convertEntityToDTO);
    }

    @Transactional
    public PostagemDTO create(PostagemDTO dto) {
        PostagemEntity entity = convertDTOToEntity(dto);
        entity = postagemRepository.save(entity);
        return convertEntityToDTO(entity);
    }

    @Transactional
    public boolean update(Long id, PostagemDTO dto) {
        return postagemRepository.findById(id)
                .map(entity -> {
                    entity.setContent(dto.getContent());
                    entity.setEdited(true);
                    postagemRepository.save(entity);
                    return true;
                }).orElse(false);
    }

    @Transactional
    public void curtirPostagem(Long postId, Long userId) throws Exception {
        Optional<PostagemEntity> postagemOpt = postagemRepository.findById(postId);
        Optional<UserEntity> usuarioOpt = userRepository.findById(userId);

        if (postagemOpt.isPresent() && usuarioOpt.isPresent()) {
            PostagemEntity postagem = postagemOpt.get();
            UserEntity usuario = usuarioOpt.get();

            if (!postagem.getCurtidas().contains(userId)) {
                postagem.getCurtidas().add(userId);
                postagemRepository.save(postagem);
            } else {
                postagem.getCurtidas().remove(userId);
                postagemRepository.save(postagem);
            }
        } else {
            throw new Exception("Postagem ou usuário não encontrado");
        }
    }

    @Transactional
    public boolean delete(Long id) {
        return postagemRepository.findById(id)
                .map(entity -> {
                    postagemRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
