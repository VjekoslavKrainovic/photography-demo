package com.photography.demo.adapter.out.persistance.repository.adapter;

import com.photography.demo.adapter.out.persistance.repository.jparepository.TagJpaRepository;
import com.photography.demo.application.port.TagRepository;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.photography.TagId;
import org.springframework.stereotype.Repository;

@Repository
public class TagJpaRepositoryAdapter implements TagRepository {

  private final TagJpaRepository tagJpaRepository;

  public TagJpaRepositoryAdapter(TagJpaRepository tagJpaRepository) {
    this.tagJpaRepository = tagJpaRepository;
  }

  @Override
  public boolean isExistById(TagId tagId, PhotographyId photographyId) {
    return tagJpaRepository.existsByIdAndPhotographyId(tagId.id(), photographyId.id());
  }
}
