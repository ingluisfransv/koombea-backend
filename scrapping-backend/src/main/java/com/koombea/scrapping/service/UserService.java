package com.koombea.scrapping.service;

import com.koombea.scrapping.model.AppUser;
import com.koombea.scrapping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public AppUser registerUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    public AppUser login(AppUser appUser){
        AppUser existingAppUser = userRepository.findByUsername(appUser.getUsername());
        if (existingAppUser != null && existingAppUser.getPassword().equals(appUser.getPassword())) {
            return existingAppUser;
        } else {
            return null; // Authentication failed
        }
    }
}
