package com.example.spring_security_demo.repo;

import com.example.spring_security_demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
}
