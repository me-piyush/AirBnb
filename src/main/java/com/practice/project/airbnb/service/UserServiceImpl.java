package com.practice.project.airbnb.service;

import com.practice.project.airbnb.entity.User;
import com.practice.project.airbnb.exception.ResourceNotFoundException;
import com.practice.project.airbnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public User getUserById(Long id) {
       return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id :"+id));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }
}
