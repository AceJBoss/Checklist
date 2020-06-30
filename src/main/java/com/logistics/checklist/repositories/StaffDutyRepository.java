package com.logistics.checklist.repositories;

import java.util.List;
import java.util.Optional;

import com.logistics.checklist.models.StaffDuty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDutyRepository extends JpaRepository<StaffDuty, Long>{
    List<StaffDuty> findByUserId(Long userId);
    Optional<StaffDuty> findByUserIdAndServicesId(Long userId, Long serviceId);

}