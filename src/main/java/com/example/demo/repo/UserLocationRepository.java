package com.example.demo.repo;

import com.example.demo.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation,Long> {
    void deleteTable();
}
