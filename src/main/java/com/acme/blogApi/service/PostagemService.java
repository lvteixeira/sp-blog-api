package com.acme.blogApi.service;

import com.acme.blogApi.dto.PostagemDTO;
import com.acme.blogApi.model.PostagemEntity;
import com.acme.blogApi.repository.PostagemRepository;
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

    public List<PostagemDTO> getAll() {
        List<PostagemEntity> entities = postagemRepository.findAll();
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
                    modelMapper.map(dto, entity);
                    entity.setEdited(true);
                    postagemRepository.save(entity);
                    return true;
                }).orElse(false);
    }
}
