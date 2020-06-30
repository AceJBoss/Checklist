package com.logistics.checklist.repositories;

import java.util.Optional;

import com.logistics.checklist.models.ServicePackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePackageRepository extends JpaRepository<ServicePackage, Long>{
    Boolean existsByPackageName(String packageName);
    Optional<ServicePackage> findByPackageName(String packageName);
}