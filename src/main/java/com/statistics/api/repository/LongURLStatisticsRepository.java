package com.statistics.api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.statistics.api.domain.LongURLStatistics;

@Repository
public interface LongURLStatisticsRepository extends JpaRepository<LongURLStatistics, Long> {

    public LongURLStatistics findByLongURL(String LongURL);

}
