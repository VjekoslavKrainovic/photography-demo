package com.photography.demo.application.exception;

import com.photography.demo.domain.photography.TagId;

public class TagNotExistException extends PhotographyBaseException {

  public TagNotExistException(TagId tagId) {
    super(String.format("Tag with id: %s is not existing.", tagId.id()));
  }

}
