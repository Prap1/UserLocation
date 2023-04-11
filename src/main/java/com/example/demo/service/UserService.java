package com.example.demo.service;

import com.example.demo.entity.UserLocation;
import com.example.demo.repo.UserLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserLocationRepository userRepo;

    public List<UserLocation> getNearestUsers(int n) {
        List<UserLocation> allUsers = userRepo.findAll();
        allUsers.sort((u1, u2) -> {
            double dist1 = calculateDistance(u1.getLatitude(), u1.getLongitude(), 0.0, 0.0);
            double dist2 = calculateDistance(u2.getLatitude(), u2.getLongitude(), 0.0, 0.0);
            return Double.compare(dist1, dist2);
        });
        return allUsers.subList(0, Math.min(n, allUsers.size()));
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1Rad) * Math.cos(lat2Rad);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d;
    }
}
