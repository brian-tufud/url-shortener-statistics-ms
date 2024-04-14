package com.statistics.api.dto;

import java.time.LocalDateTime;
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
public class LongURLDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("long_url")
    private String longURL;

    @JsonProperty("last_accessed")
    private LocalDateTime lastAccessed;

    @JsonProperty("short_urls")
    private List<ShortURLDto> shortURLs;
}
