package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository(value = "userRepository")
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users;

    public UserRepositoryImpl() {
        users = new ArrayList<>();
        users.add(new User(1, "ben@email.com", "ben123", "Ben", "Tan"));
        users.add(new User(2, "carly@email.com", "carly123", "Ben", "Tan"));
        users.add(new User(3, "daniel@email.com", "daniel123", "Ben", "Tan"));
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(Integer id) {
        return users.stream().filter(user -> user.getId() == id.intValue()).findFirst();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return users.stream().filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password)).findFirst();
    }
}
