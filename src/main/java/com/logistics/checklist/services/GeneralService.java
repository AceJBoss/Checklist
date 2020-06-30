package com.logistics.checklist.services;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.logistics.checklist.repositories.ServicePackageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.logistics.checklist.models.ServicePackage;

@Service
public class GeneralService {
    private final Path root = Paths.get("uploads");
	
	@Autowired
	ServicePackageRepository servicePackageRepository;
	
	// fetch all packages
	public List<ServicePackage> getAllPackages(){
		return servicePackageRepository.findAll();
	}

    // get file details
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
			return resource;
			} else {
			throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}
}