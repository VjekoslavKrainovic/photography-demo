package com.photography.demo.adapter.in.rest.photography.filter;

import com.photography.demo.application.exception.PhotographyBaseException;

public class InvalidFilterKeywordException extends PhotographyBaseException {

  public InvalidFilterKeywordException(String keyword) {
    super(String.format("Invalid filter keyword: %s .", keyword));
  }
}
