package com.photography.demo.application.validator;

import com.photography.demo.application.exception.PhotographyNotExistException;
import com.photography.demo.application.port.PhotographyRepository;
import com.photography.demo.domain.photography.PhotographyId;
import org.springframework.stereotype.Component;

@Component
public class PhotographyValidator {

  private final PhotographyRepository photographyRepository;

  public PhotographyValidator(PhotographyRepository photographyRepository) {
    this.photographyRepository = photographyRepository;
  }

  public void validatePhotographyId(PhotographyId photographyId) {
    boolean isPhotographyExist = photographyRepository.isExistById(photographyId);

    if (!isPhotographyExist) {
      throw new PhotographyNotExistException(photographyId);
    }
  }

}
