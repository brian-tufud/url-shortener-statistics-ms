package com.statistics.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.statistics.api.domain.ClientDeviceInformation;
import com.statistics.api.domain.ClientLocationInformation;
import com.statistics.api.dto.ClientLocationInformationDto;
import com.statistics.api.request.SqsURLAccessedEventRequest;
import com.statistics.api.utils.UtilsService;


import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Service
public class ClientStatisticsService {

    @Autowired
    private UtilsService utilsService;
    
    @Autowired
    private IpStackService ipStackService;

    public ClientLocationInformation createClientStatistics(SqsURLAccessedEventRequest body) {
        ClientLocationInformationDto locationData = getIPDetails(body.getClientIP());

        ClientLocationInformation clientLocationInformation = utilsService.createClientLocationInformationFromDto(locationData);

        setUserDeviceData(clientLocationInformation, body.getUserAgent());

        setAccessedAt(clientLocationInformation, body.getAccessedAt());

        return clientLocationInformation;
    }

    private void setAccessedAt(ClientLocationInformation clientLocationInformation, String accessedAt) {
        clientLocationInformation.setAccessedAt(utilsService.convertMillisToLocalDateTime(accessedAt));
    }

    private void setUserDeviceData(ClientLocationInformation clientInformationLocation, String userAgentString) {
        UserAgent agent = getUserAgent(userAgentString);

        ClientDeviceInformation clientDeviceInformation = new ClientDeviceInformation();

        clientDeviceInformation.setDeviceClass(agent.getValue("DeviceClass"));
        clientDeviceInformation.setDeviceName(agent.getValue("DeviceName"));
        clientDeviceInformation.setDeviceBrand(agent.getValue("DeviceBrand"));
        clientDeviceInformation.setOsName(agent.getValue("OperatingSystemName"));
        clientDeviceInformation.setOsVersion(agent.getValue("OperatingSystemVersion"));
        clientDeviceInformation.setAgentClass(agent.getValue("AgentName"));
        clientDeviceInformation.setAgentVersion(agent.getValue("AgentVersion"));

        clientInformationLocation.setClientDeviceInformation(clientDeviceInformation);
    }

    private UserAgent getUserAgent(String userAgentString) {
        UserAgentAnalyzer uaa = UserAgentAnalyzer
                    .newBuilder()
                    .hideMatcherLoadStats()
                    .withoutCache()
                    .build();

        return uaa.parse(userAgentString);
    }

    private ClientLocationInformationDto getIPDetails(String ipAddress) {
        return ipStackService.getIPDetails(ipAddress);
    }

}
