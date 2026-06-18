package com.ermapsh.hospital.repository;

import com.ermapsh.hospital.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {
}