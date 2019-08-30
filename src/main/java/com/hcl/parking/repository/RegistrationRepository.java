package com.hcl.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.parking.entity.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer>{

}
