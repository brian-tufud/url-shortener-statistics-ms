package com.statistics.api.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.statistics.api.domain.LongURLStatistics;
import com.statistics.api.domain.ShortURLStatistics;
import com.statistics.api.domain.URLDeviceInformation;
import com.statistics.api.dto.LongURLStatisticsDto;
import com.statistics.api.dto.ShortURLStatisticsDto;
import com.statistics.api.dto.ShortURLStatisticsListDto;
import com.statistics.api.dto.URLDeviceInformationDto;

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

    public ShortURLStatisticsListDto convertShortURLStatisticsListToDto(List<ShortURLStatistics> shortURLs) {
        ShortURLStatisticsListDto dto = new ShortURLStatisticsListDto();

        dto.setData(shortURLs.stream().map(shortURL -> {
            return convertShortURLStatisticsToDto(shortURL);
        }).collect(Collectors.toList()));

        return dto;
    }

    public ShortURLStatisticsDto convertShortURLStatisticsToDto(ShortURLStatistics shortURL) {
        ShortURLStatisticsDto dto = modelMapper.map(shortURL, ShortURLStatisticsDto.class);

        if (shortURL.getDevices() != null && !shortURL.getDevices().isEmpty()) {
            dto.setDevices(shortURL.getDevices().stream().map(device -> {
                return convertURLDeviceInformationToDto(device);
            }).collect(Collectors.toList()));
        }

        return dto;
    }

    public URLDeviceInformationDto convertURLDeviceInformationToDto(URLDeviceInformation URLDeviceInformation) {
        return modelMapper.map(URLDeviceInformation, URLDeviceInformationDto.class);
    }

}
