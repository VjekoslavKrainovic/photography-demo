package com.photography.demo.application.exception;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhotographyBaseException extends RuntimeException {

  public PhotographyBaseException(String message) {
    super(message);
    log.error(message);
  }

}
