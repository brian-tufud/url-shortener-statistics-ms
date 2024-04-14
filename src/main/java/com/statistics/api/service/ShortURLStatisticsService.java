package com.statistics.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.statistics.api.domain.LongURLStatistics;
import com.statistics.api.domain.ShortURLStatistics;
import com.statistics.api.dto.ShortURLStatisticsDto;
import com.statistics.api.repository.ShortURLStatisticsRepository;
import com.statistics.api.request.SqsURLAccessedEventRequest;
import com.statistics.api.utils.UtilsService;

@Service
public class ShortURLStatisticsService {

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private ClientStatisticsService clientStatisticsService;

    @Autowired
    private ShortURLStatisticsRepository shortURLRepository;

    public ShortURLStatisticsDto getShortURLStatistics(String shortURL) {
        ShortURLStatistics data = shortURLRepository.findByShortUrl(shortURL);

        return utilsService.convertShortURLStatisticsToDto(data);
    }

    public ShortURLStatistics createShortURLStatistics(String shortURL) {
        ShortURLStatistics shortURLStatistics = new ShortURLStatistics();

        shortURLStatistics.setShortURL(shortURL);
        shortURLStatistics.setTimesUsed(0);

        return shortURLStatistics;
    }

    public void updateShortURLStatistics(SqsURLAccessedEventRequest body) {
        ShortURLStatistics shortURLStatistics = shortURLRepository.findByShortUrl(body.getShortURL());

        shortURLStatistics.setTimesUsed(shortURLStatistics.getTimesUsed() + 1);
        shortURLStatistics.getConnections().add(clientStatisticsService.createClientStatistics(body));

        shortURLRepository.save(shortURLStatistics);
    }

}
