package com.statistics.api.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.statistics.api.domain.ShortURL;

@Repository
public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {

    public ShortURL findByShortUrl(String shortURL);

    public List<ShortURL> findAllByLongURLLongURL(String longURL);

}
