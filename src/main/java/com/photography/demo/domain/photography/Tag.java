package com.photography.demo.domain.photography;

import java.util.Objects;

public class Tag {

  private final TagId id;
  private String name;

  public Tag(TagId id, String name) {
    this.id = id;
    this.name = name;
  }

  public boolean hasId(){
    return this.id != null;
  }

  public TagId getId() {
    return id;
  }

  public String getName() {
    return name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tag tag = (Tag) o;
    return Objects.equals(id, tag.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Tag{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
