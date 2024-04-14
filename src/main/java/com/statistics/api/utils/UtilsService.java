package com.statistics.api.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.statistics.api.domain.LongURLStatistics;
import com.statistics.api.domain.ShortURLStatistics;
import com.statistics.api.domain.ClientDeviceInformation;
import com.statistics.api.domain.ClientLocationInformation;
import com.statistics.api.dto.LongURLStatisticsDto;
import com.statistics.api.dto.ShortURLStatisticsDto;
import com.statistics.api.request.SqsURLAccessedEventRequest;
import com.statistics.api.request.URLPairRequest;
import com.statistics.api.dto.ClientDeviceInformationDto;
import com.statistics.api.dto.ClientLocationInformationDto;

@Service
public class UtilsService {

    private final ModelMapper modelMapper = new ModelMapper();

    public HttpHeaders getResponseHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        responseHeaders.set("Access-Control-Allow-Headers",
                "Content-Type, X-AUTH-TOKEN ,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization, Accept,Content-Disposition");
        responseHeaders.set("Access-Control-Expose-Headers",
                "content-type,  X-AUTH-TOKEN, X-AUTH-ROLES, Authorization,Content-Disposition");

        return responseHeaders;
    }

    public LongURLStatisticsDto convertLongURLStatisticsToDto(LongURLStatistics longURL) {
        LongURLStatisticsDto dto = modelMapper.map(longURL, LongURLStatisticsDto.class);

        if (longURL.getShortURLs() != null && !longURL.getShortURLs().isEmpty()) {
            dto.setShortURLs(longURL.getShortURLs().stream().map(shortURL -> {
                return convertShortURLStatisticsToDto(shortURL);
            }).collect(Collectors.toList()));
        }

        return dto;
    }

    public ShortURLStatisticsDto convertShortURLStatisticsToDto(ShortURLStatistics shortURL) {
        ShortURLStatisticsDto dto = modelMapper.map(shortURL, ShortURLStatisticsDto.class);

        if (shortURL.getConnections() != null && !shortURL.getConnections().isEmpty()) {
            dto.setConnections(shortURL.getConnections().stream().map(device -> {
                return convertURLLocationInformationToDto(device);
            }).collect(Collectors.toList()));
        }

        return dto;
    }

    private ClientLocationInformationDto convertURLLocationInformationToDto(ClientLocationInformation URLLocationInformation) {
        ClientLocationInformationDto dto = modelMapper.map(URLLocationInformation, ClientLocationInformationDto.class);

        dto.setClientDeviceInformation(convertClientDeviceInformationToDto(URLLocationInformation.getClientDeviceInformation()));

        return dto;
    }

    private ClientDeviceInformationDto convertClientDeviceInformationToDto(ClientDeviceInformation URLDeviceInformation) {
        return modelMapper.map(URLDeviceInformation, ClientDeviceInformationDto.class);
    }

    public ClientLocationInformation createClientLocationInformationFromDto(ClientLocationInformationDto URLLocationInformation) {
        return modelMapper.map(URLLocationInformation, ClientLocationInformation.class);
    }

    public LocalDateTime convertMillisToLocalDateTime(String millis) {
        long timestamp = Long.parseLong(millis);

        Instant instant = Instant.ofEpochMilli(timestamp);

        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public URLPairRequest convertJSONObjectToSqsURLEventRequest(JSONObject body) {
        URLPairRequest request = new URLPairRequest();

        request.setLongURL(body.getString("long_url") != null ? body.getString("long_url") : null);
        request.setShortURL(body.getString("short_url") != null ? body.getString("short_url") : null);

        return request;
    }

    public SqsURLAccessedEventRequest convertJSONObjectToSqsURLAccessedEventRequest(JSONObject body) {
        SqsURLAccessedEventRequest request = new SqsURLAccessedEventRequest();

        request.setShortURL(body.getString("short_url") != null ? body.getString("short_url") : null);
        request.setClientIP(body.getString("client_ip") != null ? body.getString("client_ip") : null);
        request.setUserAgent(body.getString("user_agent") != null ? body.getString("user_agent") : null);
        request.setAccessedAt(body.getString("accessed_at") != null ? body.getString("accessed_at") : null);

        return request;
    }

}
