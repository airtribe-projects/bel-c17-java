package org.airtribe.LearnerManagementSystemBelC17.controller.v1;

import org.airtribe.LearnerManagementSystemBelC17.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC17.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CohortV1Controller {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @GetMapping("/v1/cohorts")
  public Page<Cohort> getAllCohorts(
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
      @RequestParam(value = "sortBy", defaultValue = "cohortId") String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
  ) {
    if (pageNumber < 0) {
      pageNumber = 0;
    }

    if (pageSize > 2000) {
      pageSize = 10;
    }

    if (!sortDir.equals("asc") && !sortDir.equals("desc")) {
      sortDir = "asc";
    }

    if (!sortBy.equals("cohortId") && !sortBy.equals("cohortName")) {
      sortBy = "cohortId";
    }

    return _learnerManagementService.fetchPaginatedAndSortedCohorts(pageSize, pageNumber, sortBy, sortDir);

  }
}
