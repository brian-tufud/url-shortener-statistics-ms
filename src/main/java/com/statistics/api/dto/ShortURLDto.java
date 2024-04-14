package com.statistics.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.statistics.api.domain.URLDeviceInformation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ShortURLDto {

    private Long id;
    
    @JsonProperty("short_url")
    private String shortURL;
    
    @JsonProperty("last_accessed")
    private LocalDateTime lastAccessed;
    
    @JsonProperty("times_used")
    private int timesUsed;
    
    @JsonProperty("long_url")
    private LongURLDto longURL;
    
    private List<URLDeviceInformationDto> devices;
}
