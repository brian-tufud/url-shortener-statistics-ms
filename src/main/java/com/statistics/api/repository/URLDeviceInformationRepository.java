package com.statistics.api.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.statistics.api.domain.URLDeviceInformation;

@Repository
public interface URLDeviceInformationRepository extends JpaRepository<URLDeviceInformation, Long> {

    public List<URLDeviceInformation> findAllByShortURLShortURL(String shortURL);

}
