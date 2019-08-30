package com.hcl.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.parking.entity.RequestSlot;

@Repository
public interface RequestSlotRepository extends JpaRepository<RequestSlot, Integer>{

}
