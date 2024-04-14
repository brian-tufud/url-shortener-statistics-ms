package com.statistics.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.statistics.api.request.SqsURLAccessedEventRequest;
import com.statistics.api.utils.EventTypes;
import com.statistics.api.utils.UtilsService;

import org.json.JSONObject;

@Service
public class EventService {

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private ShortURLStatisticsService shortURLStatisticsService;

    public void processEvent(JSONObject event) {
        String eventType = event.getString("event_type");

        switch (eventType) {
            case EventTypes.URL_ACCESSED:
                processURLAccessedEvent(event);
                break;
            default:
                throw new IllegalArgumentException("Invalid event type");
        }
    }

    private void processURLAccessedEvent(JSONObject event) {
        SqsURLAccessedEventRequest body = utilsService.convertJSONObjectToSqsURLAccessedEventRequest(event);

        shortURLStatisticsService.updateShortURLStatistics(body);
    }

}
