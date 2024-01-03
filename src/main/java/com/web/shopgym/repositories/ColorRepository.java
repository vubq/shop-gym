package com.web.shopgym.repositories;

import com.web.shopgym.entities.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, String> {

    Page<Color> findAll(Specification<Color> spec, Pageable pageable);
}
