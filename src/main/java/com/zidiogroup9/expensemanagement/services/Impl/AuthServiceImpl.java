package com.zidiogroup9.expensemanagement.services.Impl;

import com.zidiogroup9.expensemanagement.dtos.*;
import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.entities.enums.Role;
import com.zidiogroup9.expensemanagement.exceptions.ResourceNotFoundException;
import com.zidiogroup9.expensemanagement.exceptions.RuntimeConflictException;
import com.zidiogroup9.expensemanagement.repositories.UserRepository;
import com.zidiogroup9.expensemanagement.security.JwtService;
import com.zidiogroup9.expensemanagement.security.UserService;
import com.zidiogroup9.expensemanagement.services.AuthService;
import com.zidiogroup9.expensemanagement.services.EmailSenderService;
import com.zidiogroup9.expensemanagement.services.ForgetPasswordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

	@Override
	public UserDto signUp(SignUpDto signUpDto) {
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
		if (user.isPresent()) {
			throw new RuntimeConflictException("Cannot signup, User already exists with email " + signUpDto.getEmail());
		}
		User mappedUser = modelMapper.map(signUpDto, User.class);
		mappedUser.setProfile(null);
		mappedUser.setRoles(Set.of(Role.EMPLOYEE));
		mappedUser.setDepartment(null);
		mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
		User saveduser = userRepository.save(mappedUser);
		return modelMapper.map(saveduser, UserDto.class);
	}

	@Override
	public String[] login(LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		User user = (User) authentication.getPrincipal();
		String accessToken = jwtService.createAccessToken(user);
		String refreshToken = jwtService.createRefreshToken(user);
		return new String[] { accessToken, refreshToken };
	}

    @Override
    public String[] refreshToken(String refreshToken) {
        String userId=jwtService.generateUserIdFromToken(refreshToken);
        User user=userService.getUserById(userId);
        String accessToken = jwtService.createAccessToken(user);
        return new String[]{accessToken,refreshToken};
    }

    @Override
    public UserDto getProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(user,UserDto.class);
    }
}
