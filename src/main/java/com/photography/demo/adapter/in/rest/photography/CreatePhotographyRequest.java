package com.photography.demo.adapter.in.rest.photography;

import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.Size;
import com.photography.demo.domain.photography.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreatePhotographyRequest {

  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotBlank
  private String author;
  @NotBlank
  private String imageUrl;
  @NotNull
  private Double width;
  @NotNull
  private Double height;
  @NotEmpty
  private List<String> tags = new ArrayList<>();

  public Photography from() {
    Size width = Size.of(this.width);
    Size height = Size.of(this.height);

    Photography photography = new Photography(null,
        name,
        description,
        author,
        imageUrl,
        width,
        height,
        null);

    tags.stream()
        .map(this::mapTag)
        .forEach(photography::addTag);

    return photography;
  }

  private Tag mapTag(String tagName) {
    return new Tag(null, tagName);
  }

}