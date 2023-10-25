package com.photography.demo.application.service;

import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.search.SearchPhotography;
import org.springframework.data.domain.Page;

public interface PhotographyDashboardService {

  PhotographyId create(Photography photographyToSave);

  void update(Photography photographyToUpdate);

  void delete(PhotographyId photographyIdToDelete);

  Photography getById(PhotographyId photographyId);

  Page<Photography> search(SearchPhotography searchPhotography);
}
