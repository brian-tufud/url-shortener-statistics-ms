package com.statistics.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class URLPairRequest {
    
    @JsonProperty("short_url")
    private String shortURL;

    @JsonProperty("long_url")
    private String longURL;
    
}
