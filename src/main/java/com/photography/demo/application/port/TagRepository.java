package com.photography.demo.application.port;

import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.photography.TagId;

public interface TagRepository {

  boolean isExistById(TagId tagId, PhotographyId photographyId);

}
