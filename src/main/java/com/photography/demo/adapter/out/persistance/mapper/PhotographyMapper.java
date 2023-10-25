package com.photography.demo.adapter.out.persistance.mapper;

import com.photography.demo.adapter.out.persistance.entity.PhotographyDbo;
import com.photography.demo.adapter.out.persistance.entity.TagDbo;
import com.photography.demo.adapter.out.persistance.repository.jparepository.TagJpaRepository;
import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.photography.Size;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PhotographyMapper {

  private final TagMapper tagMapper;
  private final TagJpaRepository tagJpaRepository;

  public PhotographyMapper(TagMapper tagMapper, TagJpaRepository tagJpaRepository) {
    this.tagMapper = tagMapper;
    this.tagJpaRepository = tagJpaRepository;
  }

  public Photography asPhotography(PhotographyDbo photographyDbo) {

    PhotographyId id = PhotographyId.of(photographyDbo.getId());
    Size width = Size.of(photographyDbo.getWidth());
    Size height = Size.of(photographyDbo.getHeight());

    Photography photography = new Photography(id,
        photographyDbo.getName(),
        photographyDbo.getDescription(),
        photographyDbo.getAuthor(),
        photographyDbo.getImageUrl(),
        width,
        height,
        photographyDbo.getCreatedAt());

    List<TagDbo> tagsDbo = tagJpaRepository.findByPhotographyId(photographyDbo.getId());
    tagsDbo.stream()
        .map(tagMapper::asTag)
        .forEach(photography::addTag);

    return photography;
  }

  public PhotographyDbo asPhotographyDbo(Photography photography) {
    PhotographyDbo photographyDbo = new PhotographyDbo();

    if (photography.hasId()) {
      photographyDbo.setId(photography.getId().id());
    }

    photographyDbo.setName(photography.getName());
    photographyDbo.setDescription(photography.getDescription());
    photographyDbo.setAuthor(photography.getAuthor());
    photographyDbo.setImageUrl(photography.getImageUrl());
    photographyDbo.setWidth(photography.getWidth().size());
    photographyDbo.setHeight(photography.getHeight().size());
    photographyDbo.setCreatedAt(photography.getCreatedAt());

    return photographyDbo;
  }

}
