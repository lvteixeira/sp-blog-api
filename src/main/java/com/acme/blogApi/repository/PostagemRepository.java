package com.acme.blogApi.repository;

import com.acme.blogApi.model.PostagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostagemRepository extends JpaRepository<PostagemEntity, Long> {
    List<PostagemEntity> findAllByOrderByCreatedAtDesc();
}
