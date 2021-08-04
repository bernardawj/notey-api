package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Integer id);

    Optional<User> findByEmailAndPassword(String email, String password);
}
