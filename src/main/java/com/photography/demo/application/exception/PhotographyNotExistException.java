package com.photography.demo.application.exception;

import com.photography.demo.domain.photography.PhotographyId;

public class PhotographyNotExistException extends PhotographyBaseException {

  public PhotographyNotExistException(PhotographyId photographyId) {
    super(String.format("Photography with id: %s is not existing.", photographyId.id()));
  }

}
