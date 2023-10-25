package com.photography.demo.adapter.in.rest.photography;

import com.photography.demo.adapter.in.rest.photography.filter.SearchPhotographyMapper;
import com.photography.demo.application.service.PhotographyDashboardService;
import com.photography.demo.domain.photography.Photography;
import com.photography.demo.domain.photography.PhotographyId;
import com.photography.demo.domain.search.SearchPhotography;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/photographies")
public class PhotographyController {

  private final PhotographyDashboardService photographyDashboardService;
  private final SearchPhotographyMapper searchPhotographyMapper;

  public PhotographyController(PhotographyDashboardService photographyDashboardService,
      SearchPhotographyMapper searchPhotographyMapper) {
    this.photographyDashboardService = photographyDashboardService;
    this.searchPhotographyMapper = searchPhotographyMapper;
  }

  @GetMapping("/{photography-id}")
  public GetPhotographyDetailsResponse getById(@PathVariable("photography-id") Integer photographyIdRequest) {
    log.debug("START: getById()");
    log.debug("INPUT: {}", photographyIdRequest);

    PhotographyId photographyId = PhotographyId.of(photographyIdRequest);
    Photography photography = photographyDashboardService.getById(photographyId);
    GetPhotographyDetailsResponse photographyResponse = GetPhotographyDetailsResponse.from(photography);

    log.debug("OUTPUT: {}", photographyResponse);
    log.info("END: getById()");
    return photographyResponse;
  }

  @PostMapping
  public CreatedEntityIdResponse create(@Valid @RequestBody CreatePhotographyRequest createPhotographyRequest) {
    log.debug("START: create()");
    log.debug("INPUT: {}", createPhotographyRequest);

    Photography photographyToCreate = createPhotographyRequest.from();
    PhotographyId createdPhotographyId = photographyDashboardService.create(photographyToCreate);
    CreatedEntityIdResponse createdPhotographyIdResponse = CreatedEntityIdResponse.from(createdPhotographyId.id());

    log.debug("OUTPUT {}", createdPhotographyIdResponse);
    log.debug("END: create()");
    return createdPhotographyIdResponse;
  }

  @PutMapping("/{photography-id}")
  public void update(@PathVariable("photography-id") Integer photographyIdRequest,
      @Valid @RequestBody UpdatePhotographyRequest updatePhotographyRequest) {
    log.debug("START: update()");
    log.debug("INPUT: {}, {}", photographyIdRequest, updatePhotographyRequest);

    Photography photographyToUpdate = updatePhotographyRequest.from(photographyIdRequest);
    photographyDashboardService.update(photographyToUpdate);

    log.debug("END: update()");
  }

  @DeleteMapping("/{photography-id}")
  public void delete(@PathVariable("photography-id") Integer photographyIdRequest) {
    log.debug("START: delete()");
    log.debug("INPUT: {}", photographyIdRequest);

    PhotographyId photographyId = PhotographyId.of(photographyIdRequest);
    photographyDashboardService.delete(photographyId);

    log.debug("END: delete()");
  }

  @GetMapping
  public Page<GetPhotographyDetailsResponse> search(@RequestParam(required = false) String filter, Pageable pageable) {

    log.debug("START: search()");
    log.debug("INPUT: {}, {}", filter, pageable);

    SearchPhotography searchPhotography = searchPhotographyMapper.map(filter, pageable);

    Page<Photography> photographies = photographyDashboardService.search(searchPhotography);

    List<GetPhotographyDetailsResponse> photograpiesResponse = photographies.stream()
        .map(GetPhotographyDetailsResponse::from)
        .toList();

    log.debug("OUTPUT {}", photograpiesResponse);
    log.debug("END: search()");
    return new PageImpl<>(photograpiesResponse, photographies.getPageable(), photographies.getTotalElements());
  }

}
