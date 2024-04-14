package com.statistics.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.statistics.api.dto.CrudStatusDto;
import com.statistics.api.dto.CrudStatusDto.CrudStatus;
import com.statistics.api.request.URLPairRequest;
import com.statistics.api.service.ShortURLStatisticsService;
import com.statistics.api.service.LongURLStatisticsService;
import com.statistics.api.utils.UtilsService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/internal/statistics")
public class InternalStatisticsController {

    private final ShortURLStatisticsService shortURLStatisticsService;
    private final LongURLStatisticsService longURLStatisticsService;
    private final UtilsService utilsService;

    public InternalStatisticsController(
        ShortURLStatisticsService shortURLStatisticsService,
        LongURLStatisticsService longURLStatisticsService,
        UtilsService utilsService) {
            super();
            this.shortURLStatisticsService = shortURLStatisticsService;
            this.longURLStatisticsService = longURLStatisticsService;
            this.utilsService = utilsService;
    }

    @DeleteMapping("/short_url/{short_url}")
    public ResponseEntity<CrudStatusDto> deleteLongURLStatistics(HttpServletRequest request,
        @PathVariable(value = "short_url") String shortURL) throws Exception {

        shortURLStatisticsService.deleteShortURLStatistics(shortURL);

        CrudStatusDto response = CrudStatusDto.builder().status(CrudStatus.DELETED).build();

        HttpHeaders responseHeaders = utilsService.getResponseHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }

    @PostMapping("/long_url")
    public ResponseEntity<CrudStatusDto> saveLongURLStatistics(HttpServletRequest request,
        @RequestBody URLPairRequest body) throws Exception {

        longURLStatisticsService.saveLongURLStatistics(body);

        CrudStatusDto response = CrudStatusDto.builder().status(CrudStatus.CREATED).build();

        HttpHeaders responseHeaders = utilsService.getResponseHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }

}
