package com.photography.demo.adapter.in.rest.photography.filter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.photography.demo.application.exception.PhotographyNotExistException;
import com.photography.demo.application.exception.TagNotExistException;
import com.photography.demo.application.port.PhotographyRepository;
import com.photography.demo.application.port.TagRepository;
import com.photography.demo.application.service.PhotographyDashboardService;
import com.photography.demo.application.service.PhotographyDashboardServiceImpl;
import com.photography.demo.application.validator.PhotographyDashboardValidator;
import com.photography.demo.application.validator.PhotographyValidator;
import com.photography.demo.application.validator.TagValidator;
import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.photography.Size;
import com.photography.demo.domain.photography.Tag;
import com.photography.demo.domain.photography.TagId;
import com.photography.demo.util.Utility;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PhotographyDashboardServiceImplTest {


  private PhotographyDashboardService photographyDashboardService;
  @Mock
  private PhotographyRepository photographyRepositoryMock;
  @Mock
  private TagRepository tagRepositoryMock;


  @BeforeEach
  void setUp() {
    PhotographyValidator photographyValidator = new PhotographyValidator(photographyRepositoryMock);
    TagValidator tagValidator = new TagValidator(tagRepositoryMock);
    PhotographyDashboardValidator photographyDashboardValidator = new PhotographyDashboardValidator(tagValidator,
        photographyValidator);
    photographyDashboardService = new PhotographyDashboardServiceImpl(photographyDashboardValidator,
        photographyRepositoryMock);
  }

  @Test
  void given_Photography_Then_Save() {
    // prepare
    Photography photography = new Photography(null, null, null, null, null, null, null, null);

    when(photographyRepositoryMock.save(photography)).thenReturn(PhotographyId.of(1));

    // execute
    PhotographyId createdPhotographyId = photographyDashboardService.create(photography);

    // verify
    assertThat(photography.getCreatedAt()).isNotNull();
    assertThat(createdPhotographyId.id()).isEqualTo(1);
  }

  @Test
  void given_Valid_Photography_Then_Update() {
    // prepare
    PhotographyId photographyId = PhotographyId.of(1);
    Photography photographyToUpdate = new Photography(photographyId, "name", "description",
        "author", null, Size.of(12.0), Size.of(13.0), null);
    photographyToUpdate.addTag(new Tag(null, "tag1"));
    photographyToUpdate.addTag(new Tag(TagId.of(1), "tag2"));

    LocalDateTime localDateTime = Utility.currentLocalDateTime();
    Photography photographyFromDatabase = new Photography(photographyId, "nameFromDB", "descriptionFromDB",
        "authorFromDB", "imageUrlFromDB", Size.of(10.0), Size.of(10.0), localDateTime);
    photographyFromDatabase.addTag(new Tag(TagId.of(1), "tagFromDb"));

    when(photographyRepositoryMock.getById(PhotographyId.of(1))).thenReturn(photographyFromDatabase);
    when(photographyRepositoryMock.isExistById(photographyId)).thenReturn(true);

    when(tagRepositoryMock.isExistById(TagId.of(1), photographyId)).thenReturn(true);

    // execute
    photographyDashboardService.update(photographyToUpdate);

    // verify
    assertThat(photographyToUpdate.getId()).isEqualTo(photographyId);
    assertThat(photographyToUpdate.getName()).isEqualTo("name");
    assertThat(photographyToUpdate.getDescription()).isEqualTo("description");
    assertThat(photographyToUpdate.getAuthor()).isEqualTo("author");
    assertThat(photographyToUpdate.getImageUrl()).isEqualTo("imageUrlFromDB");
    assertThat(photographyToUpdate.getHeight()).isEqualTo(Size.of(13.0));
    assertThat(photographyToUpdate.getWidth()).isEqualTo(Size.of(12.0));
    assertThat(photographyToUpdate.getCreatedAt()).isEqualTo(localDateTime);

    verify(photographyRepositoryMock).save(photographyToUpdate);
  }

  @Test
  void given_Invalid_PhotographyId_Then_ThrowException() {
    // prepare
    PhotographyId photographyId = PhotographyId.of(1);
    Photography photographyToUpdate = new Photography(photographyId, "name", "description",
        "author", null, Size.of(12.0), Size.of(13.0), null);
    photographyToUpdate.addTag(new Tag(null, "tag1"));
    photographyToUpdate.addTag(new Tag(TagId.of(1), "tag2"));

    when(photographyRepositoryMock.isExistById(photographyId)).thenReturn(false);

    // execute && verfiy
    assertThatThrownBy(() -> photographyDashboardService.update(photographyToUpdate))
        .isInstanceOf(PhotographyNotExistException.class)
        .hasMessageContaining("Photography with id: 1 is not existing.");

    verify(photographyRepositoryMock, times(0)).save(photographyToUpdate);
  }

  @Test
  void given_Invalid_TagId_Then_ThrowException() {
    // prepare
    PhotographyId photographyId = PhotographyId.of(1);
    Photography photographyToUpdate = new Photography(photographyId, "name", "description",
        "author", null, Size.of(12.0), Size.of(13.0), null);
    photographyToUpdate.addTag(new Tag(null, "tag1"));
    photographyToUpdate.addTag(new Tag(TagId.of(1), "tag2"));

    when(photographyRepositoryMock.isExistById(photographyId)).thenReturn(true);
    when(tagRepositoryMock.isExistById(TagId.of(1), photographyId)).thenReturn(false);

    // execute && verfiy
    assertThatThrownBy(() -> photographyDashboardService.update(photographyToUpdate))
        .isInstanceOf(TagNotExistException.class)
        .hasMessageContaining("Tag with id: 1 is not existing.");

    verify(photographyRepositoryMock, times(0)).save(photographyToUpdate);
  }

  @Test
  void given_Existing_Photography_Then_delete() {
    // prepare
    PhotographyId photographyId = PhotographyId.of(1);

    when(photographyRepositoryMock.isExistById(photographyId)).thenReturn(true);

    // execute
    photographyDashboardService.delete(photographyId);

    // verify
    verify(photographyRepositoryMock).delete(photographyId);
  }

  @Test
  void given_Non_Existing_Photography_Then_Throw_Exception() {
    // prepare
    PhotographyId photographyId = PhotographyId.of(2);

    when(photographyRepositoryMock.isExistById(photographyId)).thenReturn(false);

    // execute && verify
    assertThatThrownBy(() -> photographyDashboardService.delete(photographyId))
        .isInstanceOf(PhotographyNotExistException.class)
        .hasMessageContaining("Photography with id: 2 is not existing.");
  }

  @Test
  void given_Existing_PhotographyId_Then_Return_Photography() {
    // prepare
    PhotographyId photographyId = PhotographyId.of(2);

    when(photographyRepositoryMock.isExistById(photographyId)).thenReturn(true);
    Photography photographyMock = new Photography(null, null, null, null, null, null, null, null);
    when(photographyRepositoryMock.getById(photographyId)).thenReturn(photographyMock);

    // execute
    Photography photography = photographyDashboardService.getById(photographyId);

    // verify
    assertThat(photography).isEqualTo(photographyMock);
  }

  @Test
  void given_Non_Existing_PhotographyId_Then_Throw_Exception() {
    // prepare
    PhotographyId photographyId = PhotographyId.of(2);

    when(photographyRepositoryMock.isExistById(photographyId)).thenReturn(false);

    // execute && verify
    assertThatThrownBy(() -> photographyDashboardService.getById(photographyId))
        .isInstanceOf(PhotographyNotExistException.class)
        .hasMessageContaining("Photography with id: 2 is not existing.");
  }

}