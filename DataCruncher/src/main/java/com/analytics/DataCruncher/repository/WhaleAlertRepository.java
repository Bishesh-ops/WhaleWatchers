package com.analytics.DataCruncher.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analytics.DataCruncher.model.WhaleAlert;

@Repository
public interface WhaleAlertRepository extends JpaRepository<WhaleAlert, UUID>{
    
}
