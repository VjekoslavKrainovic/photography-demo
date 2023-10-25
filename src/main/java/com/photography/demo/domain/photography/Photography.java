package com.photography.demo.domain.photography;

import com.photography.demo.application.exception.MaximumNumberTagsReachedException;
import com.photography.demo.util.Utility;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Photography {

  private static final int MAXIMUM_TAG_NUMBER = 100;
  private final PhotographyId id;
  private String name;
  private String description;
  private String author;
  private String imageUrl;
  private Size width;
  private Size height;
  private LocalDateTime createdAt;
  private final List<Tag> tags;

  public Photography(PhotographyId id, String name, String description, String author, String imageUrl, Size width,
      Size height, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.author = author;
    this.imageUrl = imageUrl;
    this.width = width;
    this.height = height;
    this.createdAt = createdAt;
    this.tags = new ArrayList<>();
  }

  public boolean hasId() {
    return this.id != null;
  }

  public void update(Photography photographyFromDatabase) {
    this.imageUrl = photographyFromDatabase.getImageUrl();
    this.createdAt = photographyFromDatabase.getCreatedAt();
  }

  public void created() {
    this.createdAt = Utility.currentLocalDateTime();
  }

  public void addTag(Tag tag) {
    if (this.tags.size() > MAXIMUM_TAG_NUMBER) {
      throw new MaximumNumberTagsReachedException();
    }
    this.tags.add(tag);
  }

  public PhotographyId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getAuthor() {
    return author;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public Size getWidth() {
    return width;
  }

  public Size getHeight() {
    return height;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public List<Tag> getTags() {
    return tags;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Photography that = (Photography) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Photography{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", author='" + author + '\'' +
        ", imageUrl='" + imageUrl + '\'' +
        ", width=" + width +
        ", height=" + height +
        ", createdAt=" + createdAt +
        ", tags=" + tags +
        '}';
  }
}
