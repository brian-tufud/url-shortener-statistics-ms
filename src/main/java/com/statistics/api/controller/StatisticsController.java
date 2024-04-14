package com.statistics.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.statistics.api.dto.LongURLStatisticsDto;
import com.statistics.api.dto.ShortURLStatisticsDto;
import com.statistics.api.service.StatisticsService;
import com.statistics.api.utils.UtilsService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final UtilsService utilsService;

    public StatisticsController(StatisticsService statisticsService, UtilsService utilsService) {
        super();
        this.statisticsService = statisticsService;
        this.utilsService = utilsService;
    }

    @GetMapping("/{short_url}") 
    public ResponseEntity<ShortURLStatisticsDto> getShortUrlStatistics(HttpServletRequest request,
        @PathVariable(value = "short_url") String shortURL) throws Exception {
        
        ShortURLStatisticsDto shortURLStatistics = statisticsService.getShortURLStatistics(shortURL);

        HttpHeaders responseHeaders = utilsService.getResponseHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(shortURLStatistics);
    }

    @GetMapping("/long_url") 
    public ResponseEntity<LongURLStatisticsDto> getLongURLStatistics(HttpServletRequest request,
        @PathVariable(value = "long_url") String shortURL) throws Exception {

        LongURLStatisticsDto longURLStatistics = statisticsService.getLongURLStatistics(shortURL);

        HttpHeaders responseHeaders = utilsService.getResponseHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(longURLStatistics);
    }
    
}
