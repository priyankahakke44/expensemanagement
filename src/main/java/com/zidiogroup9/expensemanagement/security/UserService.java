package com.zidiogroup9.expensemanagement.security;

import com.zidiogroup9.expensemanagement.entities.User;
import com.zidiogroup9.expensemanagement.exceptions.ResourceNotFoundException;
import com.zidiogroup9.expensemanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                ()->new ResourceNotFoundException("User not found with Email: "+username)
        );
    }
    public User getUserById(String userId){
        return userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User not found with id: "+userId)
        );
    }
}
