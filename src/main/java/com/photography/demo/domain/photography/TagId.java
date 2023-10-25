package com.photography.demo.domain.photography;

public record TagId(Integer id) {

  public static TagId of(Integer id) {
    return new TagId(id);
  }

}
