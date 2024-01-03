package com.web.shopgym.repositories;

import com.web.shopgym.entities.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, String> {

    Page<Size> findAll(Specification<Size> spec, Pageable pageable);
}
