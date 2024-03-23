package com.acme.blogApi.repository;

import com.acme.blogApi.model.PostagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostagemRepository extends JpaRepository<PostagemEntity, Long> {
}
