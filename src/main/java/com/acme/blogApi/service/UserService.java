package com.acme.blogApi.service;

import com.acme.blogApi.dto.UserDTO;
import com.acme.blogApi.model.UserEntity;
import com.acme.blogApi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

        TypeMap<UserDTO, UserEntity> typeMap = modelMapper.createTypeMap(UserDTO.class, UserEntity.class);

        typeMap.addMappings(mapper -> mapper.skip(UserEntity::setId));
        typeMap.addMappings(mapper -> mapper.skip(UserEntity::setUserId));

        TypeMap<UserEntity, UserDTO> typeMap1 = modelMapper.createTypeMap(UserEntity.class, UserDTO.class);

        typeMap1.addMappings(mapper -> mapper.skip(UserDTO::setEmail));
        typeMap1.addMappings(mapper -> mapper.skip(UserDTO::setId));
        typeMap1.addMappings(mapper -> mapper.skip(UserDTO::setPassword));
    }

    public UserDTO convertEntityToDTO(UserEntity entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    public UserEntity convertDTOToEntity(UserDTO dto) {
        return modelMapper.map(dto, UserEntity.class);
    }

    public void create(UserEntity user) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        userRepository.flush();
    }
}