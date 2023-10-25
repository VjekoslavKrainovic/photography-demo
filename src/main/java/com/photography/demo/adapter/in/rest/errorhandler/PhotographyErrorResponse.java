package com.photography.demo.adapter.in.rest.errorhandler;

import com.photography.demo.application.exception.PhotographyBaseException;

public class PhotographyErrorResponse {

  private final String errorMessage;

  public PhotographyErrorResponse(PhotographyBaseException photographyBaseException) {
    this.errorMessage = photographyBaseException.getMessage();
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
