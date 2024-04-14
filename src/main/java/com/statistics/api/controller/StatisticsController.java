package com.statistics.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.statistics.api.dto.LongURLStatisticsDto;
import com.statistics.api.dto.ShortURLStatisticsDto;
import com.statistics.api.request.URLPairRequest;
import com.statistics.api.service.ShortURLStatisticsService;
import com.statistics.api.service.LongURLStatisticsService;
import com.statistics.api.utils.UtilsService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final ShortURLStatisticsService shortURLStatisticsService;
    private final LongURLStatisticsService longURLStatisticsService;
    private final UtilsService utilsService;

    public StatisticsController(
        ShortURLStatisticsService shortURLStatisticsService,
        LongURLStatisticsService longURLStatisticsService,
        UtilsService utilsService) {
            super();
            this.shortURLStatisticsService = shortURLStatisticsService;
            this.longURLStatisticsService = longURLStatisticsService;
            this.utilsService = utilsService;
    }

    @GetMapping("/{short_url}") 
    public ResponseEntity<ShortURLStatisticsDto> getShortUrlStatistics(HttpServletRequest request,
        @PathVariable(value = "short_url") String shortURL) throws Exception {
        
        ShortURLStatisticsDto shortURLStatistics = shortURLStatisticsService.getShortURLStatistics(shortURL);

        HttpHeaders responseHeaders = utilsService.getResponseHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(shortURLStatistics);
    }

    @GetMapping("/long_url") 
    public ResponseEntity<LongURLStatisticsDto> getLongURLStatistics(HttpServletRequest request,
        @RequestParam(value = "long_url") String longURL) throws Exception {

        LongURLStatisticsDto longURLStatistics = longURLStatisticsService.getLongURLStatistics(longURL);

        HttpHeaders responseHeaders = utilsService.getResponseHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(longURLStatistics);
    }

    @PostMapping("/long_url")
    public ResponseEntity<Void> saveLongURLStatistics(HttpServletRequest request,
        @RequestBody URLPairRequest body) throws Exception {

        longURLStatisticsService.saveLongURLStatistics(body);

        HttpHeaders responseHeaders = utilsService.getResponseHeaders();
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @DeleteMapping("/long_url")
    public ResponseEntity<Void> deleteLongURLStatistics(HttpServletRequest request,
        @RequestBody URLPairRequest body) throws Exception {

        longURLStatisticsService.deleteLongURLStatistics(body);

        HttpHeaders responseHeaders = utilsService.getResponseHeaders();
        return ResponseEntity.ok().headers(responseHeaders).build();
    }
    
}
