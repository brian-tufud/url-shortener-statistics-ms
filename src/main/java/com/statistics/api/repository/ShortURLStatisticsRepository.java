package com.statistics.api.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.statistics.api.domain.ShortURLStatistics;

@Repository
public interface ShortURLStatisticsRepository extends JpaRepository<ShortURLStatistics, Long> {

    public ShortURLStatistics findByShortURL(String shortURL);

    public List<ShortURLStatistics> findAllByLongURLLongURL(String longURL);

}
