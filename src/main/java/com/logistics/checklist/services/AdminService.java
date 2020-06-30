package com.logistics.checklist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


import com.logistics.checklist.models.User;
import com.logistics.checklist.models.UserRole;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.logistics.checklist.models.ServicePackage;
import com.logistics.checklist.models.Services;
import com.logistics.checklist.models.StaffDuty;
import com.logistics.checklist.repositories.ServicePackageRepository;
import com.logistics.checklist.repositories.ServicesRepository;
import com.logistics.checklist.repositories.StaffDutyRepository;
import com.logistics.checklist.repositories.UserRepository;
import com.logistics.checklist.repositories.UserRoleRepository;
import com.logistics.checklist.response.ResponseMessage;


@Service
public class AdminService {
	private final Path root = Paths.get("uploads");
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private ServicesRepository servicesRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	ServicePackageRepository servicePackageRepository;

	@Autowired
	StaffDutyRepository staffDutyRepository;

	public void init() {
		try {
		Files.createDirectory(root);
		} catch (final IOException e) {
		throw new RuntimeException("Could not initialize folder for upload!");
		}
	}
	
	// register staff
	public ResponseEntity<User> registerStaff(User user){
		UserRole userRole = userRoleRepository.findById(4L).orElse(null);
        if(userRole == null){
            userRole = new UserRole();
        }
        user.setUserRole(userRole);
        final String username = user.getUsername();
       if (userRepository.existsByUsername(username)){

           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       }
  
        final String password = user.getPassword();
        final String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);
	    return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
	}
	
	// fetch staff list
	public List<User> getAllStaff(){
		return userRepository.findByUserRoleId(4L);
	}

	// edit staff record
	public ResponseEntity<Optional<User>> editStaffInfo(final Long id){
		final Optional<User> staff = userRepository.findById(id);
		if(staff==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(staff, HttpStatus.OK);
	}

	// update staff record
	public ResponseEntity<User> updateStaffInfo(final Long id, final User user){
		final User staffRecord = userRepository.findById(id).orElse(null);
        if(staffRecord == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
        final String username = user.getUsername();
       if (userRepository.existsByUsername(username)){
			// get record with the same user name and validate
			final User validateUsername = userRepository.findByUsername(username).orElse(null);
			if(validateUsername.getId() != id){

				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
	   }
	   
	   staffRecord.setFullname(user.getFullname());
	   staffRecord.setEmail(user.getEmail());
	   staffRecord.setPhone(user.getPhone());
	   staffRecord.setUsername(user.getUsername());
	   staffRecord.setAddress(user.getAddress());
	   staffRecord.setFcmToken(user.getFcmToken());
	    return new ResponseEntity<>(userRepository.save(staffRecord), HttpStatus.OK);
	}

	// delete staff record
	public ResponseEntity<?> deleteStaffInfo(final Long id){
		// validate staff id
		final User staffRecord = userRepository.findById(id).orElse(null);

		if(staffRecord == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// create new service
	public ResponseEntity<Services> createService(final Services service){
		if(servicesRepository.existsByServiceName(service.getServiceName())){
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		return ResponseEntity.ok(servicesRepository.save(service));
	}

	// get all services
	public List<Services> getAllServices(){
		return servicesRepository.findAll();
	}

	// edit service info
	public ResponseEntity<Optional<Services>> editServicesInfo(final Long id){
		final Optional<Services> serviceData = servicesRepository.findById(id);

		if(serviceData == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(serviceData, HttpStatus.OK);
	}


	// update service info
	public ResponseEntity<Services> updateServiceInfo(final Long id, final Services service){
		final Services serviceData = servicesRepository.findById(id).orElse(null);

		if(serviceData == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(servicesRepository.existsByServiceName(service.getServiceName())){
			final String serviceName = service.getServiceName();
			// validate service name with existing record
			final Services validateServiceName = servicesRepository.findByServiceName(serviceName).orElse(null);
			if(validateServiceName.getId() != id){

				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

		serviceData.setServiceName(service.getServiceName());
		serviceData.setServiceDesc(service.getServiceDesc());

		return new ResponseEntity<>(servicesRepository.save(serviceData),HttpStatus.OK);
	}

	// delete service
	public ResponseEntity<?> deleteService(final Long id){
		// validate service id
		final Services serviceData = servicesRepository.findById(id).orElse(null);

		if(serviceData == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		servicesRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// create new package
	public ResponseEntity<ServicePackage> createPackage(final ServicePackage packageData){
		
		if(servicePackageRepository.existsByPackageName(packageData.getPackageName())){
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		return ResponseEntity.ok(servicePackageRepository.save(packageData));
	}


	// edit package details
	public ResponseEntity<Optional<ServicePackage>> editPackageInfo(final Long id){
		final Optional<ServicePackage> packageData = servicePackageRepository.findById(id);

		if(packageData == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(packageData, HttpStatus.OK);
	}

	// update package details
	public ResponseEntity<ServicePackage> updatePackageInfo(final Long id, final ServicePackage packageData){
		final ServicePackage servicePackageData = servicePackageRepository.findById(id).orElse(null);

		if(servicePackageData == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(servicePackageRepository.existsByPackageName(servicePackageData.getPackageName())){
			final String packageName = packageData.getPackageName();
			
			// validate package name with existing record
			final ServicePackage validatePackagename = servicePackageRepository.findByPackageName(packageName).orElse(null);
			if(validatePackagename.getId() != id){

				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

		servicePackageData.setPackageName(packageData.getPackageName());
		servicePackageData.setPackageDesc(packageData.getPackageDesc());
		servicePackageData.setPrice(packageData.getPrice());

		return new ResponseEntity<>(servicePackageRepository.save(servicePackageData),HttpStatus.OK);
	}

	// delete service package
	public ResponseEntity<?> deleteServicePackage(final Long id){
		// validate service package id
		final ServicePackage packageData = servicePackageRepository.findById(id).orElse(null);

		if(packageData == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		servicePackageRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// create staff duty
	public ResponseEntity<ResponseMessage> attachStaffToService(final StaffDuty duty){
		// validate staff id
		final User staff = userRepository.findById(duty.getUser().getId()).orElse(null);

		if(staff == null){
			return new ResponseEntity<>(new ResponseMessage(true, "Staff not found."),HttpStatus.OK);
		}

		// validate service
		final Services service = servicesRepository.findById(duty.getServices().getId()).orElse(null);

		if(service == null){
			return new ResponseEntity<>(new ResponseMessage(true, "Invalid service supplied."),HttpStatus.OK);
		}

		// validate if staff has already been assigned to service
		if(staffDutyRepository.findByUserIdAndServicesId(duty.getUser().getId(), duty.getServices().getId()).isPresent()){
			return new ResponseEntity<>(new ResponseMessage(true, "Duplicate record not allowed."),HttpStatus.OK);
		}

		// save record
		staffDutyRepository.save(duty);
		return new ResponseEntity<>(new ResponseMessage(false, "Staff attached to duty successfully"),HttpStatus.OK);
	}

	// get staff duty
	public List<StaffDuty> getStaffDuty(final Long userId){
		return staffDutyRepository.findByUserId(userId);
	}

	// update staff duty
	public ResponseEntity<ResponseMessage> updateStaffDuty(final Long id, final StaffDuty duty){
		// validate id
		final StaffDuty staffDuty = staffDutyRepository.findById(id).orElse(null);

		if(staffDuty == null){
			return new ResponseEntity<>(new ResponseMessage(true, "Record not found."), HttpStatus.OK);
		}

		// validate staff id
		final User staff = userRepository.findById(duty.getUser().getId()).orElse(null);

		if(staff == null){
			return new ResponseEntity<>(new ResponseMessage(true, "Staff not found."),HttpStatus.OK);
		}

		// validate service
		final Services service = servicesRepository.findById(duty.getServices().getId()).orElse(null);

		if(service == null){
			return new ResponseEntity<>(new ResponseMessage(true, "Invalid service supplied."),HttpStatus.OK);
		}

		// validate if a different staff has already been assigned to service
		final Optional<StaffDuty> validateStaffDuty = staffDutyRepository.findByUserIdAndServicesId(duty.getUser().getId(), duty.getServices().getId());
		if(validateStaffDuty.isPresent() && validateStaffDuty.get().getId() != id){
			return new ResponseEntity<>(new ResponseMessage(true, "Duplicate record not allowed."),HttpStatus.OK);
		}

		// update record
		staffDuty.setUser(duty.getUser());
		staffDuty.setServices(duty.getServices());

		staffDutyRepository.save(staffDuty);
		return new ResponseEntity<>(new ResponseMessage(false, "Staff duty updated successfully."),HttpStatus.OK);
	}

	// delete staff duty
	public ResponseEntity<ResponseMessage> deleteStaffDuty(final Long id){
		// validate id
		final StaffDuty staffDuty = staffDutyRepository.findById(id).orElse(null);

		if(staffDuty == null){
			return new ResponseEntity<>(new ResponseMessage(true, "Record not found."), HttpStatus.OK);
		}

		staffDutyRepository.deleteById(id);
		return new ResponseEntity<>(new ResponseMessage(false, "Record deleted."), HttpStatus.OK);
	}

	// update staff profile picture
	public ResponseEntity<ResponseMessage> updateStaffProfilePicture(final Long id, MultipartFile staffImage){
		try {
			User staffData = userRepository.findById(id).orElse(null);

			if(staffData == null){
				return ResponseEntity.ok(new ResponseMessage(true, "Staff not found."));
			}

			if(staffData.getPicture() != null){
				// delete previous picture
				FileSystemUtils.deleteRecursively(root.resolve(staffData.getPicture()));
			}

			final Random rand = new Random();

			final String filename = rand.nextInt()+"_"+staffImage.getOriginalFilename();
			Files.copy(staffImage.getInputStream(), this.root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

			staffData.setPicture(filename);
			userRepository.save(staffData);

			return ResponseEntity.ok(new ResponseMessage(false, "Staff picture updated successfully."));


		} catch (final Exception e) {
			return new ResponseEntity<>(new ResponseMessage(true, "Could not update staff picture"), HttpStatus.CONFLICT);
		}
	}

}
