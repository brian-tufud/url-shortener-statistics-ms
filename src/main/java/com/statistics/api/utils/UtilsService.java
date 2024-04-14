package com.statistics.api.utils;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.statistics.api.domain.LongURL;
import com.statistics.api.domain.ShortURL;
import com.statistics.api.domain.URLDeviceInformation;
import com.statistics.api.dto.LongURLDto;
import com.statistics.api.dto.ShortURLDto;
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

    public LongURLDto convertLongURLToDto(LongURL longURL) {
        LongURLDto dto = modelMapper.map(longURL, LongURLDto.class);

        if (longURL.getShortURLs() != null && !longURL.getShortURLs().isEmpty()) {
            dto.setShortURLs(longURL.getShortURLs().stream().map(shortURL -> {
                return convertShortURLToDto(shortURL);
            }).collect(Collectors.toList()));
        }

        return dto;
    }

    public ShortURLDto convertShortURLToDto(ShortURL shortURL) {
        ShortURLDto dto = modelMapper.map(shortURL, ShortURLDto.class);

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
