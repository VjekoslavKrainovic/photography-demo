package com.photography.demo.application.service;

import com.photography.demo.application.port.PhotographyRepository;
import com.photography.demo.application.validator.PhotographyDashboardValidator;
import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.search.SearchPhotography;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhotographyDashboardServiceImpl implements PhotographyDashboardService {

  private final PhotographyDashboardValidator photographyDashboardValidator;
  private final PhotographyRepository photographyRepository;

  public PhotographyDashboardServiceImpl(PhotographyDashboardValidator photographyDashboardValidator,
      PhotographyRepository photographyRepository) {
    this.photographyDashboardValidator = photographyDashboardValidator;
    this.photographyRepository = photographyRepository;
  }

  @Override
  public PhotographyId create(Photography photographyToSave) {
    log.debug("START: create()");
    log.debug("INPUT: {}", photographyToSave);

    photographyToSave.created();
    PhotographyId createdPhotographyId = photographyRepository.save(photographyToSave);

    log.debug("OUTPUT: {}", createdPhotographyId);
    log.info("END: create()");
    return createdPhotographyId;
  }

  @Override
  public void update(Photography photographyToUpdate) {
    log.debug("START: update()");
    log.debug("INPUT: {}", photographyToUpdate);

    photographyDashboardValidator.validatePhotographyUpdate(photographyToUpdate);
    Photography photographyFromDatabase = photographyRepository.getById(photographyToUpdate.getId());
    photographyToUpdate.update(photographyFromDatabase);
    photographyRepository.save(photographyToUpdate);

    log.info("END: update()");
  }

  @Override
  public void delete(PhotographyId photographyIdToDelete) {
    log.debug("START: delete()");
    log.debug("INPUT: {}", photographyIdToDelete);

    photographyDashboardValidator.validatePhotographyDelete(photographyIdToDelete);
    photographyRepository.delete(photographyIdToDelete);

    log.info("END: delete()");
  }

  @Override
  public Photography getById(PhotographyId photographyId) {
    log.debug("START: getById()");
    log.debug("INPUT: {}", photographyId);

    photographyDashboardValidator.validateGetPhotographyById(photographyId);
    Photography photography = photographyRepository.getById(photographyId);

    log.debug("OUTPUT: {}", photography);
    log.info("END: getById()");
    return photography;
  }

  @Override
  public Page<Photography> search(SearchPhotography searchPhotography) {
    log.debug("START: search()");
    log.debug("INPUT: {}", searchPhotography);

    Page<Photography> photographies = photographyRepository.search(searchPhotography);

    log.debug("OUTPUT: {}", photographies);
    log.info("END: getById()");
    return photographies;
  }
}
