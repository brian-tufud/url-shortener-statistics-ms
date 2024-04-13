package com.statistics.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.statistics.api.response.IpStackResponse;
import com.statistics.api.utils.Constants;

import org.json.JSONObject;

@Service
public class IpStackService {

    public IpStackResponse getIpDetails(String ipAddress) {
        try {
            String url =  Constants.IP_STACK_URL + ipAddress + "?access_key=" + Constants.IP_STACK_ACCESS_KEY;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            IpStackResponse ipStackResponse = new IpStackResponse();
            ipStackResponse.setContinentCode(jsonResponse.getString("continent_code"));
            ipStackResponse.setContinentName(jsonResponse.getString("continent_name"));
            ipStackResponse.setCountryCode(jsonResponse.getString("country_code"));
            ipStackResponse.setCountryName(jsonResponse.getString("country_name"));
            ipStackResponse.setRegionCode(jsonResponse.getString("region_code"));
            ipStackResponse.setRegionName(jsonResponse.getString("region_name"));
            ipStackResponse.setCityName(jsonResponse.getString("city"));
            ipStackResponse.setZipCode(jsonResponse.getString("zip"));

            return ipStackResponse;

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Manejo de error, devuelve null en caso de error
        }
    }


}
