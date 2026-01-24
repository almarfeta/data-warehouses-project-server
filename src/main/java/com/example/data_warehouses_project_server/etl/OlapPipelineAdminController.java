package com.example.data_warehouses_project_server.etl;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(OlapPipelineAdminController.DEFAULT_ENDPOINT_MAPPING)
class OlapPipelineAdminController {

    static final String DEFAULT_ENDPOINT_MAPPING = "/api/admin/etl";

    private final OlapPipelineService olapPipelineService;

    OlapPipelineAdminController(OlapPipelineService olapPipelineService) {
        this.olapPipelineService = olapPipelineService;
    }

    @PostMapping("/run")
    public ResponseEntity<String> startEtlPipeline(
            @RequestParam(name = "populateDimTime", required = false, defaultValue = "false") boolean populateDimTime,
            @RequestParam(name = "populateDimPaymentMethod", required = false, defaultValue = "false") boolean populateDimPaymentMethod,
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate
    ) {
        if (populateDimTime && (startDate == null || endDate == null)) {
            return ResponseEntity.badRequest().body("startDate and endDate must be provided when populateDimTime is true");
        }

        this.olapPipelineService.runPipeline(populateDimTime, populateDimPaymentMethod, startDate, endDate);
        return ResponseEntity.accepted().body("ETL pipeline started");
    }
}
