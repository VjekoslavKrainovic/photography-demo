package com.photography.demo.adapter.in.rest.photography;

import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.photography.Size;
import com.photography.demo.domain.photography.Tag;
import com.photography.demo.domain.photography.TagId;
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
public class UpdatePhotographyRequest {

  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotBlank
  private String author;
  @NotNull
  private Double width;
  @NotNull
  private Double height;
  @NotEmpty
  private List<UpdateTagRequest> tags = new ArrayList<>();

  public Photography from(Integer photographyId) {
    Size width = Size.of(this.width);
    Size height = Size.of(this.height);

    Photography photography = new Photography(PhotographyId.of(photographyId),
        name,
        description,
        author,
        null,
        width,
        height,
        null);

    tags.stream()
        .map(UpdateTagRequest::from)
        .forEach(photography::addTag);

    return photography;
  }
}

@Getter
@Setter
@ToString
class UpdateTagRequest {

  private Integer id;
  @NotBlank
  private String name;

  public Tag from() {
    TagId id = null;
    if(this.id != null){
      id = TagId.of(this.id);
    }
    return new Tag(id, name);
  }
}