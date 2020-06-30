package com.logistics.checklist.repositories;

import java.util.List;
import java.util.Optional;

import com.logistics.checklist.models.ECustomerPacakge;
import com.logistics.checklist.models.ListAuditor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListAuditorRepository extends JpaRepository<ListAuditor, Long> {
    Optional<ListAuditor> findByAuditorId(Long id);
    List<ListAuditor> findByCustomerIdAndStatus(Long id, ECustomerPacakge status);
}