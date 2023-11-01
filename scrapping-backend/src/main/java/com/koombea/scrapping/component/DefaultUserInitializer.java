package com.koombea.scrapping.component;

import com.koombea.scrapping.model.AppUser;
import com.koombea.scrapping.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DefaultUserInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the default user already exists
        if (userRepository.findByUsername("defaultUser") == null) {
            // Create and save the default user
            AppUser defaultUser = new AppUser();
            defaultUser.setUsername("defaultUser");
            defaultUser.setPassword("defaultUser");
            userRepository.save(defaultUser);
        }
    }
}
