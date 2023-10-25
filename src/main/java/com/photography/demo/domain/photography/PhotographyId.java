package com.photography.demo.domain.photography;

public record PhotographyId(Integer id) {

  public static PhotographyId of(Integer id) {
    return new PhotographyId(id);
  }

}
