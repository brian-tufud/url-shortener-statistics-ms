package com.statistics.api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.statistics.api.domain.LongURL;

@Repository
public interface LongURLRepository extends JpaRepository<LongURL, Long> {

    public LongURL findByLongURL(String LongURL);

}
