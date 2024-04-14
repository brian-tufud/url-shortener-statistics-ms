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
public class SqsURLAccessedEventRequest {

    @JsonProperty("short_url")
    private String shortURL;

    @JsonProperty("client_ip")
    private String clientIP;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("accessed_at")
    private String accessedAt;
    
}
