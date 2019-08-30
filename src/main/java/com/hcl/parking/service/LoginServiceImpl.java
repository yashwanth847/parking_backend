package com.hcl.parking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.parking.dto.LoginDto;
import com.hcl.parking.dto.LoginResponseDto;
import com.hcl.parking.entity.Registration;
import com.hcl.parking.entity.Role;
import com.hcl.parking.exception.UserNotFoundException;
import com.hcl.parking.repository.RoleRepository;
import com.hcl.parking.repository.UserRepository;
import com.hcl.parking.util.PasswordUtil;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	public LoginResponseDto loginUser(LoginDto loginDto) {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		PasswordUtil password = new PasswordUtil();
		logger.info("inside the loginUser method..");
		java.util.Optional<Registration> registration = userRepository.findByMobileNumber(loginDto.getMobileNo());
		java.util.Optional<Role> role = roleRepository.findById(registration.get().getRoleId());
		if (!registration.isPresent())
			throw new UserNotFoundException("Invalid credentials");
		if (!role.isPresent())
			throw new UserNotFoundException("Role id not available");

		if (registration.get().getMobileNumber().equalsIgnoreCase(loginDto.getMobileNo())
				&& registration.get().getPassword().equals(password.encodePassword(loginDto.getPassword()))) {

			loginResponseDto.setMessage("Login success..");
			loginResponseDto.setRegId(registration.get().getRegistrationId());
			loginResponseDto.setRoleName(role.get().getRoleName());
			loginResponseDto.setStatusCode(200);
		}

		return loginResponseDto;
	}
}
