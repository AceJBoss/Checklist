package com.logistics.checklist.controllers;

import java.util.List;

import com.logistics.checklist.models.CustomerPackage;
import com.logistics.checklist.models.ListAuditor;
import com.logistics.checklist.response.ResponseMessage;
import com.logistics.checklist.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    // select package
    @PostMapping("/user/select/package")
    @PreAuthorize("hasRole(7) or hasRole(8)")
    public ResponseEntity<?> selectServicePackage(@RequestBody CustomerPackage customerPackage){
        try{
            return customerService.selectServicePackage(customerPackage);
        }catch(Exception ex){
            return new ResponseEntity<>(new ResponseMessage(true, ex.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/user/list/auditors/{user_id}")
    @PreAuthorize("hasRole(7) or hasRole(8)")
    public ResponseEntity<?> getListAuditors(@PathVariable("user_id") Long user_id){
        try{
            return customerService.fetchListAuditors(user_id);
        }catch(Exception ex){
            return new ResponseEntity<>(new ResponseMessage(true, ex.getMessage()), HttpStatus.OK);
        }
    }
}
