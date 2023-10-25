package com.photography.demo.application.validator;

import com.photography.demo.application.exception.TagNotExistException;
import com.photography.demo.application.port.TagRepository;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.photography.TagId;
import org.springframework.stereotype.Component;

@Component
public class TagValidator {

  private final TagRepository tagRepository;

  public TagValidator(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public void validateTagId(TagId tagId, PhotographyId photographyId) {
    boolean isTagExist = tagRepository.isExistById(tagId, photographyId);

    if (!isTagExist) {
      throw new TagNotExistException(tagId);
    }
  }

}
