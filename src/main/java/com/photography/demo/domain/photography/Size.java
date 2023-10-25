package com.photography.demo.domain.photography;

import com.photography.demo.application.exception.InvalidSizeNumberException;

public record Size(Double size) {

  public static Size of(Double size) {
    if (size == null || size <= 0) {
      throw new InvalidSizeNumberException();
    }
    return new Size(size);
  }

}
