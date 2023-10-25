package com.photography.demo.application.validator;

import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.photography.Tag;
import org.springframework.stereotype.Component;

@Component
public class PhotographyDashboardValidator {

  private final TagValidator tagValidator;
  private final PhotographyValidator photographyValidator;

  public PhotographyDashboardValidator(TagValidator tagValidator, PhotographyValidator photographyValidator) {
    this.tagValidator = tagValidator;
    this.photographyValidator = photographyValidator;
  }


  public void validatePhotographyUpdate(Photography photography) {
    photographyValidator.validatePhotographyId(photography.getId());

    photography.getTags().stream()
        .filter(Tag::hasId)
        .forEach(tag -> tagValidator.validateTagId(tag.getId(), photography.getId()));
  }

  public void validatePhotographyDelete(PhotographyId photographyId) {
    photographyValidator.validatePhotographyId(photographyId);
  }

  public void validateGetPhotographyById(PhotographyId photographyId) {
    photographyValidator.validatePhotographyId(photographyId);
  }

}
