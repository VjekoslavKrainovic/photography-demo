package com.photography.demo.adapter.out.persistance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "photography")
@Getter
@Setter
public class PhotographyDbo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;
  private String author;
  private String imageUrl;
  private Double width;
  private Double height;
  private LocalDateTime createdAt;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "photography")
  private List<TagDbo> tags = new ArrayList<>();
}
