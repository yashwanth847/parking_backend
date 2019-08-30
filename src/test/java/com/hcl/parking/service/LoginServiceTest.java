package com.hcl.parking.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.parking.dto.LoginDto;
import com.hcl.parking.dto.LoginResponseDto;
import com.hcl.parking.entity.Registration;
import com.hcl.parking.entity.Role;
import com.hcl.parking.repository.RoleRepository;
import com.hcl.parking.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

	@Mock UserRepository userRepository;
	@Mock RoleRepository roleRepository;
	@InjectMocks LoginServiceImpl loginService;
	
	public Registration getRegistration() {
		Registration registration = new Registration();
		registration.setMobileNumber("97392313");
		registration.setOverAllExperience(34.5F);
		registration.setHclExperience(5F);
		registration.setPassword("password");
		registration.setRegistrationId(1);
		registration.setRoleId(2);
		registration.setUserName("guru");
		return registration;
	}
	
	public LoginResponseDto getLoginResponseDto() {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setRegId(1);
		loginResponseDto.setRoleName("V");
		loginResponseDto.setMessage("Login success..");
		return loginResponseDto;
	}
	public LoginDto getLoginDto() {
		LoginDto loginDto = new LoginDto();
		loginDto.setMobileNo("97392313");
		loginDto.setPassword("password");
		return loginDto;	
	}
	public Role getRoleDetails() {
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("E");
		return role;
	}
	@Test
	public void testLoginUser()
	{
		Registration registration = getRegistration();
		LoginDto loginDto = getLoginDto();
		LoginResponseDto loginResponseDto = getLoginResponseDto(); 
		Role role = getRoleDetails();
		Mockito.when(userRepository.findByMobileNumber(Mockito.anyString())).thenReturn(Optional.of(registration));
		Mockito.when(roleRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(role));
		LoginResponseDto response = loginService.loginUser(loginDto);
		assertEquals(loginResponseDto.getMessage(), response.getMessage());
	}
}
