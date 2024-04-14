package com.statistics.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.statistics.api.domain.LongURLStatistics;
import com.statistics.api.domain.ShortURLStatistics;
import com.statistics.api.dto.ShortURLStatisticsDto;
import com.statistics.api.repository.LongURLStatisticsRepository;
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

    @Autowired
    private LongURLStatisticsRepository longURLStatisticsRepository;

    public ShortURLStatisticsDto getShortURLStatistics(String shortURL) {
        ShortURLStatistics data = findByShortURL(shortURL);

        return utilsService.convertShortURLStatisticsToDto(data);
    }
    
    public ShortURLStatistics createShortURLStatistics(String shortURL, LongURLStatistics longURLStatistics) {
        ShortURLStatistics shortURLStatistics = new ShortURLStatistics();

        shortURLStatistics.setShortURL(shortURL);
        shortURLStatistics.setTimesUsed(0);
        shortURLStatistics.setLongURL(longURLStatistics);

        return shortURLStatistics;
    }

    @Transactional
    public void updateShortURLStatistics(SqsURLAccessedEventRequest body) {
        ShortURLStatistics shortURLStatistics = findByShortURL(body.getShortURL());

        shortURLStatistics.setTimesUsed(shortURLStatistics.getTimesUsed() + 1);
        shortURLStatistics.getConnections().add(clientStatisticsService.createClientStatistics(body, shortURLStatistics));

        shortURLRepository.save(shortURLStatistics);
    }

    @Transactional
    public void deleteShortURLStatistics(String shortURL) {
        ShortURLStatistics shortURLStatistics = findByShortURL(shortURL);

        LongURLStatistics longURLStatistics = shortURLStatistics.getLongURL();

        if (longURLStatistics.getShortURLs().size() == 1) {
            longURLStatisticsRepository.delete(longURLStatistics);
        } else {
            longURLStatistics.getShortURLs().remove(shortURLStatistics);
            longURLStatisticsRepository.save(longURLStatistics);

            shortURLRepository.delete(shortURLStatistics);
        }
    }

    private ShortURLStatistics findByShortURL(String shortURL) {
        return shortURLRepository.findByShortURL(shortURL);
    }

}
