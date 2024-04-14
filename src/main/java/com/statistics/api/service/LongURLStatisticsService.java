package com.statistics.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.statistics.api.domain.LongURLStatistics;
import com.statistics.api.dto.LongURLStatisticsDto;
import com.statistics.api.request.URLPairRequest;
import com.statistics.api.repository.LongURLStatisticsRepository;
import com.statistics.api.utils.UtilsService;

@Service
public class LongURLStatisticsService {

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private ShortURLStatisticsService shortURLStatisticsService;

    @Autowired
    private LongURLStatisticsRepository longURLStatisticsRepository;

    public LongURLStatisticsDto getLongURLStatistics(String longURL) {
        LongURLStatistics data = longURLStatisticsRepository.findByLongURL(longURL);

        return utilsService.convertLongURLStatisticsToDto(data);
    }

    public void saveLongURLStatistics(URLPairRequest body) {
        LongURLStatistics longURLStatistics = new LongURLStatistics();

        longURLStatistics.setLongURL(body.getLongURL());
        longURLStatistics.getShortURLs().add(shortURLStatisticsService.createShortURLStatistics(body.getShortURL()));

        longURLStatisticsRepository.save(longURLStatistics);
    }

    public void deleteLongURLStatistics(URLPairRequest body) {
        LongURLStatistics LongURLStatistics = longURLStatisticsRepository.findByLongURL(body.getLongURL());

        longURLStatisticsRepository.delete(LongURLStatistics);
    }

}
