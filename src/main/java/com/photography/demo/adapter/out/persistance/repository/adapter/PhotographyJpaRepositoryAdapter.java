package com.photography.demo.adapter.out.persistance.repository.adapter;

import com.photography.demo.adapter.out.persistance.entity.PhotographyDbo;
import com.photography.demo.adapter.out.persistance.entity.TagDbo;
import com.photography.demo.adapter.out.persistance.mapper.PhotographyMapper;
import com.photography.demo.adapter.out.persistance.mapper.TagMapper;
import com.photography.demo.adapter.out.persistance.repository.PhotographySpecification;
import com.photography.demo.adapter.out.persistance.repository.jparepository.PhotographyJpaRepository;
import com.photography.demo.adapter.out.persistance.repository.jparepository.TagJpaRepository;
import com.photography.demo.application.exception.PhotographyNotExistException;
import com.photography.demo.application.port.PhotographyRepository;
import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.search.SearchPhotography;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class PhotographyJpaRepositoryAdapter implements PhotographyRepository {

  private final PhotographyJpaRepository photographyJpaRepository;
  private final TagJpaRepository tagJpaRepository;
  private final PhotographyMapper photographyMapper;
  private final TagMapper tagMapper;

  public PhotographyJpaRepositoryAdapter(PhotographyJpaRepository photographyJpaRepository,
      TagJpaRepository tagJpaRepository,
      PhotographyMapper photographyMapper,
      TagMapper tagMapper) {
    this.photographyJpaRepository = photographyJpaRepository;
    this.tagJpaRepository = tagJpaRepository;
    this.photographyMapper = photographyMapper;
    this.tagMapper = tagMapper;
  }

  @Override
  public boolean isExistById(PhotographyId photographyId) {
    return photographyJpaRepository.existsById(photographyId.id());
  }

  @Override
  public Photography getById(PhotographyId photographyId) {
    PhotographyDbo photographyDbo = photographyJpaRepository.findById(photographyId.id())
        .orElseThrow(() -> new PhotographyNotExistException(photographyId));
    return photographyMapper.asPhotography(photographyDbo);
  }

  @Transactional
  @Override
  public void delete(PhotographyId photographyId) {
    // todo dodaj rollback
    tagJpaRepository.deleteByPhotographyId(photographyId.id());
    photographyJpaRepository.deleteById(photographyId.id());
  }

  @Transactional
  @Override
  public PhotographyId save(Photography photography) {
    // todo dodaj rollback
    PhotographyDbo photographyDbo = photographyMapper.asPhotographyDbo(photography);
    PhotographyDbo savedPhotographyDbo = photographyJpaRepository.save(photographyDbo);
    List<TagDbo> tagsDbo = photography.getTags().stream()
        .map(tag -> tagMapper.asTagDbo(tag, savedPhotographyDbo.getId()))
        .toList();
    tagJpaRepository.saveAll(tagsDbo);

    if (photography.hasId()) {
      List<Integer> tagsToNotDelete = tagsDbo.stream()
          .map(TagDbo::getId)
          .toList();
      tagJpaRepository.deleteByIdNotInAndPhotographyId(tagsToNotDelete, photography.getId().id());
    }

    return PhotographyId.of(savedPhotographyDbo.getId());
  }

  @Override
  public Page<Photography> search(SearchPhotography searchPhotography) {
    Specification<PhotographyDbo> searchSpecification = PhotographySpecification.createSearch(searchPhotography);
    Page<PhotographyDbo> photographiesDbo = photographyJpaRepository.findAll(searchSpecification, searchPhotography.getPageable());

    List<Photography> photographies = photographiesDbo.getContent().stream()
        .map(photographyMapper::asPhotography)
        .toList();

    return new PageImpl<>(photographies, photographiesDbo.getPageable(), photographiesDbo.getTotalElements());
  }
}
