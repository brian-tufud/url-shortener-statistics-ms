package com.statistics.api.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.statistics.api.domain.LongURLStatistics;
import com.statistics.api.domain.ShortURLStatistics;
import com.statistics.api.dto.LongURLStatisticsDto;
import com.statistics.api.dto.ShortURLStatisticsDto;
import com.statistics.api.repository.LongURLStatisticsRepository;
import com.statistics.api.repository.ShortURLStatisticsRepository;
import com.statistics.api.response.IpStackResponse;
import com.statistics.api.utils.Constants;
import com.statistics.api.utils.UtilsService;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@Service
public class StatisticsService {

    @Autowired
    private UtilsService utilsService;
    
    @Autowired
    private IpStackService ipStackService;

    @Autowired
    private ShortURLStatisticsRepository shortURLRepository;

    @Autowired
    private LongURLStatisticsRepository longURLRepository;

    public ShortURLStatisticsDto getShortURLStatistics(String shortURL) {
        ShortURLStatistics data = shortURLRepository.findByShortUrl(shortURL);

        return utilsService.convertShortURLStatisticsToDto(data);
    }

    public LongURLStatisticsDto getLongURLStatistics(String longURL) {
        LongURLStatistics data = longURLRepository.findByLongURL(longURL);

        return utilsService.convertLongURLStatisticsToDto(data);
    }

    public void getUserAgentData(HttpServletRequest request) {
        String userAgentString = request.getHeader("User-Agent");

        UserAgentAnalyzer uaa = UserAgentAnalyzer
                    .newBuilder()
                    .hideMatcherLoadStats()
                    .withoutCache()
                    .build();

        UserAgent agent = uaa.parse(userAgentString);

        String deviceClass = agent.getValue("DeviceClass");
        String deviceName = agent.getValue("DeviceName");
        String deviceBrand = agent.getValue("DeviceBrand");
        String operatingSystemName = agent.getValue("OperatingSystemName");
        String operatingSystemVersion = agent.getValue("OperatingSystemVersion");
        String agentName = agent.getValue("AgentName");
        String agentVersion = agent.getValue("AgentVersion");

        return;
        
    }

    public IpStackResponse getIpData(HttpServletRequest request) {
        String ipAddress = getClientIpAddress(request);

        return getIpDetails(ipAddress);
    }

    private IpStackResponse getIpDetails(String ipAddress) {
        return ipStackService.getIpDetails(ipAddress);
    }

    private String getClientIpAddress(HttpServletRequest request) {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }

        for (String header: Constants.IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    private HashMap<String, String> getMetadata(HttpServletRequest request, String shortURL) {
        HashMap<String, String> metadata = new HashMap<String, String>();

        metadata.put("short_url", shortURL);
        metadata.put("client_ip", getClientIpAddress(request));
        metadata.put("user_agent", request.getHeader("User-Agent"));

        return metadata;
    }

}
