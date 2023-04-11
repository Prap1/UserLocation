package com.example.demo.controller;

import com.example.demo.entity.UserLocation;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.UserLocationRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user_location")
public class UserController {
    @Autowired
    private UserLocationRepository userRepo;
   @Autowired
   private UserService service;

    @PostMapping("/create_data")
    @PreAuthorize("hasRole('ADMIN')")
    public UserLocation createUserLocation(@RequestBody UserLocation userLocation) {
        return userRepo.save(userLocation);
    }

    @PatchMapping("/update_data")
    @PreAuthorize("hasRole('ADMIN')")
    public UserLocation updateUserLocation(@PathVariable Long id, @RequestBody UserLocation updatedUserLocation) {
        UserLocation userLocation = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User location not found with id: " + id));
        userLocation.setName(updatedUserLocation.getName());
        userLocation.setLatitude(updatedUserLocation.getLatitude());
        userLocation.setLongitude(updatedUserLocation.getLongitude());
        return userRepo.save(userLocation);
    }
    @GetMapping("/get_users/{n}")
    @PreAuthorize("hasRole('ROLE_READER')")
    public List<UserLocation> getNearestUsers(@PathVariable int n) {
        return service.getNearestUsers(n);
    }

    @DeleteMapping("/delete_data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteTable() {
        userRepo.deleteTable();
        return new ResponseEntity<>("Table deleted successfully", HttpStatus.OK);
    }
}

