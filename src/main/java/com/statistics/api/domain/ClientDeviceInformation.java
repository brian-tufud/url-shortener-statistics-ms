package com.statistics.api.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client_device_information")
public class ClientDeviceInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_class")
    private String deviceClass;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_brand")
    private String deviceBrand;

    @Column(name = "os_name")
    private String osName;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "agent_class")
    private String agentClass;

    @Column(name = "agent_version")
    private String agentVersion;

    @OneToOne()
    @JoinColumn(name = "client_location_information", referencedColumnName = "id")
    private ClientLocationInformation clientLocationInformation;
}