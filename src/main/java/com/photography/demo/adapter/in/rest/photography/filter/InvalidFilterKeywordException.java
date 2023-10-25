package com.photography.demo.adapter.in.rest.photography.filter;

import com.photography.demo.application.exception.PhotographyBaseException;

public class InvalidFilterKeywordException extends PhotographyBaseException {

  public InvalidFilterKeywordException() {
    super("Invalid filter keyword.");
  }
}
