package com.proyecto.app.Services;

import org.springframework.stereotype.Service;

import com.proyecto.app.Repository.UserRepository;
@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    


}
