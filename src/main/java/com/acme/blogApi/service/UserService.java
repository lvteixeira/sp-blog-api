package com.acme.blogApi.service;

import com.acme.blogApi.dto.UserDTO;
import com.acme.blogApi.exception.AuthenticationException;
import com.acme.blogApi.exception.UserCreationException;
import com.acme.blogApi.model.UserEntity;
import com.acme.blogApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;

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

    @Transactional
    public UserDTO create(UserDTO dto) {
        UserEntity userEntity = convertDTOToEntity(dto);
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        try {
            UserEntity savedUser = userRepository.save(userEntity);
            return convertEntityToDTO(savedUser);
        } catch(Exception e) {
            throw new UserCreationException("Falha ao criar usuário");
        }
    }

    public Optional<UserDTO> authenticate(String username, String password) {
        return Optional.ofNullable(userRepository.findByUsername(username)
                .filter(user -> encoder.matches(password, user.getPassword()))
                .map(this::convertEntityToDTO)
                .orElseThrow(() -> new AuthenticationException("Usuário ou senha inválidos")));
    }
}
