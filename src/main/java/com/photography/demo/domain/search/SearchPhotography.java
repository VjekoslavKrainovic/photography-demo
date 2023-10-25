package com.photography.demo.domain.search;

import java.util.List;
import org.springframework.data.domain.Pageable;

public class SearchPhotography {

  private List<SubQuery> subQueries;
  private Pageable pageable;

  public SearchPhotography(List<SubQuery> subQueries, Pageable pageable) {
    this.subQueries = subQueries;
    this.pageable = pageable;
  }

  public List<SubQuery> getSubQueries() {
    return subQueries;
  }

  public Pageable getPageable() {
    return pageable;
  }

  @Override
  public String toString() {
    return "SearchPhotography{" +
        "subQueries=" + subQueries +
        ", pageable=" + pageable +
        '}';
  }
}
