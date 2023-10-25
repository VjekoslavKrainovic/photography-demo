package com.photography.demo.adapter.out.persistance.repository.jparepository;

import com.photography.demo.adapter.out.persistance.entity.TagDbo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagJpaRepository extends JpaRepository<TagDbo, Integer> {

  boolean existsByIdAndPhotographyId(Integer tagId, Integer photographyId);

  List<TagDbo> findByPhotographyId(Integer photographyId);

  void deleteByPhotographyId(Integer photographyId);

  void deleteByIdNotInAndPhotographyId(List<Integer> tagIds, Integer photographyId);

}
