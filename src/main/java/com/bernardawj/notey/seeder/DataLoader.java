package com.bernardawj.notey.seeder;

import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Checking if the database needs to be seeded...");

        if (userRepository.countUsers() == 0) {
            System.out.println("Seeding the database");

            var passwordEncoder = new BCryptPasswordEncoder();
            userRepository.save(new User("ben@email.com", passwordEncoder.encode("ben12345"), "Ben", "Tan"));
            userRepository.save(new User("emma@email.com", passwordEncoder.encode("emma12345"), "Emma", "Kang"));
            userRepository.save(new User("daniel@email.com", passwordEncoder.encode("daniel12345"), "Daniel", "Wong"));
            userRepository.save(new User("carly@email.com", passwordEncoder.encode("carly12345"), "Carly", "Lee"));
        }

        System.out.println("Database has been seeded.");
    }
}
