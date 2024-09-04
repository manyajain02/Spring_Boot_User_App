package com.springboot.project.uber.User.App.services;

import com.springboot.project.uber.User.App.entities.User;
import com.springboot.project.uber.User.App.exceptions.ResourceNotFoundException;
import com.springboot.project.uber.User.App.repositories.UserRepository;
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
        return userRepository.findByEmail(username).orElse(null);
    }

    public User getUserById(Long id){
       return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found with id" + id));
    }
}
