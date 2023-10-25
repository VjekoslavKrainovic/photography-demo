package com.photography.demo.application.port;

import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.search.SearchPhotography;
import org.springframework.data.domain.Page;

public interface PhotographyRepository {

  boolean isExistById(PhotographyId photographyId);

  Photography getById(PhotographyId photographyId);

  void delete(PhotographyId photographyId);

  PhotographyId save(Photography photography);

  Page<Photography> search(SearchPhotography searchPhotography);
}
