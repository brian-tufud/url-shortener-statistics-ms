package com.statistics.api.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.statistics.api.domain.ClientLocationInformation;

@Repository
public interface ClientLocationInformationRepository extends JpaRepository<ClientLocationInformation, Long> {

}
