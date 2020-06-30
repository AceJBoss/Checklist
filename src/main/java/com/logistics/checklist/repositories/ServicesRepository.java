package com.logistics.checklist.repositories;

import java.util.Optional;

import com.logistics.checklist.models.Services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long>{
    Boolean existsByServiceName(String serviceName);
    Optional<Services> findByServiceName(String serviceName);
}
