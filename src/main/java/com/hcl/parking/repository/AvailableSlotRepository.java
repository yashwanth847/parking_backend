package com.hcl.parking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.parking.entity.AvailableSlot;

@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, Integer>{

//	@Query("select New com.hcl.parking.dto.SlotsResponseDto (p.availableDate, p.bookedEmpId,p.slotId,p.status,p.vipId,) from AvailableSlot p" + 
//			" where " + 
//			" p.availableDate=:currentDate" + 
//			" and " + 
//			" p.availableSlotId=p.availableSlotId group by p.availableSlotId")
//	public List<SlotsResponseDto> getAllSLots(@Param("currentDate") LocalDate currentDate);
	

}
