package com.photography.demo.adapter.in.rest.photography;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreatedEntityIdResponse {

  private Integer id;

  private CreatedEntityIdResponse(Integer id) {
    this.id = id;
  }

  public static CreatedEntityIdResponse from(Integer id) {
    return new CreatedEntityIdResponse(id);
  }

}