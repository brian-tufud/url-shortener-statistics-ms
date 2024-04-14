package com.statistics.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientLocationInformationDto {

    private Long id;

    @JsonProperty("continent_code")
    private String continentCode;

    @JsonProperty("continent_name")
    private String continentName;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("region_code")
    private String regionCode;

    @JsonProperty("region_name")
    private String regionName;

    @JsonProperty("city")
    private String cityName;

    @JsonProperty("zip")
    private String zipCode;

    @JsonProperty("accessed_at")
    private String accessedAt;

    @JsonProperty("client_device_information")
    private ClientDeviceInformationDto clientDeviceInformation;
}
