package com.photography.demo.adapter.out.persistance.mapper;

import com.photography.demo.adapter.out.persistance.entity.PhotographyDbo;
import com.photography.demo.adapter.out.persistance.entity.TagDbo;
import com.photography.demo.adapter.out.persistance.repository.jparepository.PhotographyJpaRepository;
import com.photography.demo.application.exception.PhotographyNotExistException;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.photography.Tag;
import com.photography.demo.domain.photography.TagId;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

  private final PhotographyJpaRepository photographyJpaRepository;

  public TagMapper(PhotographyJpaRepository photographyJpaRepository) {
    this.photographyJpaRepository = photographyJpaRepository;
  }

  public Tag asTag(TagDbo tagDbo) {
    TagId id = TagId.of(tagDbo.getId());
    return new Tag(id, tagDbo.getName());
  }

  public TagDbo asTagDbo(Tag tag, Integer photographyId) {
    TagDbo tagDbo = new TagDbo();

    if (tag.hasId()) {
      tagDbo.setId(tag.getId().id());
    }

    tagDbo.setName(tag.getName());

    PhotographyDbo photographyDbo = photographyJpaRepository.findById(photographyId)
        .orElseThrow(() -> new PhotographyNotExistException(PhotographyId.of(photographyId)));
    tagDbo.setPhotography(photographyDbo);

    return tagDbo;
  }

}
