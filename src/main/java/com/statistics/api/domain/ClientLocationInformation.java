package com.statistics.api.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client_location_information")
public class ClientLocationInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "continent_code")
    private String continentCode;

    @Column(name = "continent_name")
    private String continentName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "city")
    private String cityName;

    @Column(name = "zip")
    private String zipCode;

    @Column(name = "accessed_at")
    private LocalDateTime accessedAt;

    @ManyToOne()
    @JoinColumn(name = "short_url")
    private ShortURLStatistics shortURL;

    @OneToOne(mappedBy = "clientLocationInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClientDeviceInformation clientDeviceInformation;
}