package com.logistics.checklist.services;

import java.util.List;
import java.util.Optional;

import com.logistics.checklist.models.CustomerPackage;
import com.logistics.checklist.models.ECustomerPacakge;
import com.logistics.checklist.models.ListAuditor;
import com.logistics.checklist.models.User;
import com.logistics.checklist.repositories.CustomerPackageRepository;
import com.logistics.checklist.repositories.ListAuditorRepository;
import com.logistics.checklist.repositories.UserRepository;
import com.logistics.checklist.response.ResponseMessage;
import com.logistics.checklist.response.auditorsReponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerPackageRepository customerPackageRepository;

    @Autowired
    ListAuditorRepository listAuditorRepository;

    // user select package
    public ResponseEntity<?> selectServicePackage(final CustomerPackage customerPackage){
        try{
            // validate user
            final User validateUser = userRepository.findById(customerPackage.getUser().getId()).orElse(null);


            if(validateUser == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // validate if user already have an existing active package
            if(customerPackageRepository.existsByUserIdAndStatus(customerPackage.getUser().getId(), ECustomerPacakge.valueOf("Active"))){
                return new ResponseEntity<>( new ResponseMessage(true, "You have an active plan running already."), HttpStatus.OK);
            }

            // get available staff to add to list
            if(validateUser.getUserRole().getId() == 8L){
                // find and attach dummy staff
                List<User> auditors= userRepository.findByUserRoleId(9L);

                auditors.forEach(data->{
                    final ListAuditor staffId = listAuditorRepository.findByAuditorId(data.getId()).orElse(null);

                    if(staffId == null){
                        // attach to customer
                        final ListAuditor newListAuditor = new ListAuditor();
                        newListAuditor.setCustomer(validateUser);
                        newListAuditor.setAuditor(data);
                        newListAuditor.setStatus(ECustomerPacakge.Active);
                        listAuditorRepository.save(newListAuditor);
                        return;
                    }
                });
            }

            if(validateUser.getUserRole().getId() == 7L){
                // find and attach real staff
                final List<User> auditors= userRepository.findByUserRoleId(9L);
                auditors.forEach(data->{
                    final ListAuditor staffId = listAuditorRepository.findByAuditorId(data.getId()).orElse(null);

                    if(staffId == null){
                        // attach to customer
                        final ListAuditor newListAuditor = new ListAuditor();
                        newListAuditor.setCustomer(validateUser);
                        newListAuditor.setAuditor(data);
                        newListAuditor.setStatus(ECustomerPacakge.Active);
                        listAuditorRepository.save(newListAuditor);
                        return;
                    }
                });
            }
            
            // create new  customer package
            customerPackage.setStatus(ECustomerPacakge.valueOf("Active"));
            customerPackageRepository.save(customerPackage);
            return new ResponseEntity<>( new ResponseMessage(false, "Package selected successfully."),HttpStatus.CREATED);
        }catch(final Exception ex){
            return ResponseEntity.ok(new ResponseMessage(true, ex.getMessage()));
        }
    }

    // view list auditors
    public ResponseEntity<?> fetchListAuditors(final Long id) {
        auditorsReponse response = new auditorsReponse();
        List<ListAuditor> auditorRecord = listAuditorRepository.findByCustomerIdAndStatus(id, ECustomerPacakge.Active);

        if(!auditorRecord.isEmpty()){

            auditorRecord.forEach(auditor->{
    
                response.setAuditor_id(auditor.getAuditor().getId());
                response.setFullname(auditor.getAuditor().getFullname());
                response.setPicture(auditor.getAuditor().getPicture());
                response.setUsername(auditor.getAuditor().getUsername());
                response.setRoleName(auditor.getAuditor().getUserRole().getRoleName());
    
                return;
            });
        }
        
        return ResponseEntity.ok(response);

    }

    
}
