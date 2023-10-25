package com.photography.demo.adapter.out.persistance.repository.jparepository;

import com.photography.demo.adapter.out.persistance.entity.PhotographyDbo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PhotographyJpaRepository extends JpaRepository<PhotographyDbo, Integer>,
    JpaSpecificationExecutor<PhotographyDbo> {

  boolean existsById(Integer photographyId);

  Page<PhotographyDbo> findAll(Specification<PhotographyDbo> searchSpecification, Pageable pageable);

}
