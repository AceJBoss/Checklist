package com.logistics.checklist.repositories;

import com.logistics.checklist.models.CustomerPackage;
import com.logistics.checklist.models.ECustomerPacakge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPackageRepository extends JpaRepository<CustomerPackage , Long>{
    Boolean existsByUserIdAndStatus(Long user_id, ECustomerPacakge status);
}