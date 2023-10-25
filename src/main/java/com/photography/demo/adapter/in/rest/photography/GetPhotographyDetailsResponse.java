package com.photography.demo.adapter.in.rest.photography;

import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.Tag;
import com.photography.demo.util.Utility;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetPhotographyDetailsResponse {

  private final String name;
  private final String description;
  private final String author;
  private final String imageUrl;
  private final Double width;
  private final Double height;
  private final String createdAt;
  private final List<GetTagDetailsResponse> tags;

  private GetPhotographyDetailsResponse(String name, String description, String author, String imageUrl, Double width,
      Double height, String createdAt, List<GetTagDetailsResponse> tags) {
    this.name = name;
    this.description = description;
    this.author = author;
    this.imageUrl = imageUrl;
    this.width = width;
    this.height = height;
    this.createdAt = createdAt;
    this.tags = tags;
  }

  public static GetPhotographyDetailsResponse from(Photography photography) {
    List<GetTagDetailsResponse> tagsResponse = photography.getTags().stream()
        .map(GetTagDetailsResponse::from)
        .toList();
    return new GetPhotographyDetailsResponse(photography.getName(),
        photography.getDescription(),
        photography.getAuthor(),
        photography.getImageUrl(),
        photography.getWidth().size(),
        photography.getHeight().size(),
        Utility.formatLocalDateTimeToString(photography.getCreatedAt()),
        tagsResponse);
  }

}

@Getter
@ToString
class GetTagDetailsResponse {

  private Integer id;
  private String name;

  private GetTagDetailsResponse(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public static GetTagDetailsResponse from(Tag tag) {
    return new GetTagDetailsResponse(tag.getId().id(), tag.getName());
  }

}