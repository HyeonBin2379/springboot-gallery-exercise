package com.ssg.gallery.block.repository;

import com.ssg.gallery.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Integer> {

    Optional<Block> findByToken(String token);
}
