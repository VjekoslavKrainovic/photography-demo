package com.photography.demo.application.exception;

public class MaximumNumberTagsReachedException extends PhotographyBaseException {

  public MaximumNumberTagsReachedException() {
    super("Maximum Tag number for each Photography is 100.");
  }

}
