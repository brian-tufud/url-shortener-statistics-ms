package com.statistics.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class URLDeviceInformationDto {

    private Long id;
    
    @JsonProperty("device_class")
    private String deviceClass;
    
    @JsonProperty("device_name")
    private String deviceName;
    
    @JsonProperty("device_brand")
    private String deviceBrand;
    
    @JsonProperty("os_name")
    private String osName;
    
    @JsonProperty("os_version")
    private String osVersion;
    
    @JsonProperty("agent_class")
    private String agentClass;
    
    @JsonProperty("agent_version")
    private String agentVersion;
    
}
