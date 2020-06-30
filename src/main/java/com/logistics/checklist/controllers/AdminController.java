package com.logistics.checklist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;

import com.logistics.checklist.models.ServicePackage;
import com.logistics.checklist.models.Services;
import com.logistics.checklist.models.StaffDuty;
// models
import com.logistics.checklist.models.User;
// import com.logistics.checklist.models.UserRole;
import com.logistics.checklist.services.AdminService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    // create new staff
    @PostMapping("/staff/create")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<User> createStaffAccount(@RequestBody User body){
        // UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // return new ResponseEntity<>(userDetails.getAuthorities(), HttpStatus.OK);
        return adminService.registerStaff(body);
    }

    // list all staff
    @GetMapping("/staff/list")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> fetchStaffList(){
        return new ResponseEntity<>(adminService.getAllStaff(), HttpStatus.OK);
    }

    // edit staff record
    @GetMapping("/staff/edit/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<Optional<User>> editStaffRecord(@PathVariable Long id){
        return adminService.editStaffInfo(id);
    }

    // update staff record
    @PutMapping("/staff/update/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<User> updateStaffRecord(@PathVariable(name = "id") Long id, @RequestBody User user){
        return adminService.updateStaffInfo(id, user);
    }

    // delete staff record
    @DeleteMapping("/staff/delete/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        return adminService.deleteStaffInfo(id);
    }

    // create new service
    @PostMapping("/services/create")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<Services> createNewService(@RequestBody Services service) {
        return adminService.createService(service);
    }

    // list all created services
    @GetMapping("/services")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> fetchServices(){
        return new ResponseEntity<>(adminService.getAllServices(), HttpStatus.OK);
    }

    // edit service info
    @GetMapping("/services/edit/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<Optional<Services>> editServiceRecord(@PathVariable Long id){
        return adminService.editServicesInfo(id);
    }

    // update service info
    @PutMapping("/services/update/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<Services> updateServiceRecord(@PathVariable("id") Long id, @RequestBody Services service){
        return adminService.updateServiceInfo(id, service);
    }

    // delete service
    @DeleteMapping("/services/delete/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> deleteServiceRecord(@PathVariable("id") Long id){
        return adminService.deleteService(id);
    }

    // create new service package
    @PostMapping("/packages/create")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> createNewPackage(@RequestBody ServicePackage packageData){
        return adminService.createPackage(packageData);
    }

    // edit package info
    @GetMapping("/packages/edit/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<Optional<ServicePackage>> editPackageRecord(@PathVariable Long id){
        return adminService.editPackageInfo(id);
    }

    // update package info
    @PutMapping("/packages/update/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<ServicePackage> updatePackageRecord(@PathVariable("id") Long id, @RequestBody ServicePackage packageData){
        return adminService.updatePackageInfo(id, packageData);
    }

    // delete package record
    @DeleteMapping("/packages/delete/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> deletePackageRecord(@PathVariable("id") Long id){
        return adminService.deleteServicePackage(id);
    }

    // create staff duty
    @PostMapping("/staff/duty")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> createStaffDuty(@RequestBody StaffDuty duty){
        return adminService.attachStaffToService(duty);
    }

    // get staff with there duty or get a staff duty
    @GetMapping("/staff/{userId}/duty")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> fetchStaffDuty(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(adminService.getStaffDuty(userId),HttpStatus.OK);
    }
    
    // update staff duty 
    @PutMapping("/staff/duty/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> updateStaffDuty(@PathVariable("id") Long id, @RequestBody StaffDuty duty){
        return adminService.updateStaffDuty(id, duty);
    }


    // delete staff duty
    @DeleteMapping("/staff/duty/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> deleteStaffduty(@PathVariable("id") Long id){
        return adminService.deleteStaffDuty(id);
    }
    

    @PutMapping("/staff/update/picture/{id}")
    @PreAuthorize("hasRole(1)")
    public ResponseEntity<?> updateStaffPicture(@PathVariable("id") Long id, @RequestBody MultipartFile staffImage){
        return adminService.updateStaffProfilePicture(id, staffImage);
    }
}
