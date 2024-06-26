package com.statistics.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LongURLStatisticsDto {

    private Long id;

    @JsonProperty("long_url")
    private String longURL;

    @JsonProperty("short_urls")
    private List<ShortURLStatisticsDto> shortURLs;
}
