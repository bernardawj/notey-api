package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.LoginDTO;
import com.bernardawj.notey.dto.UserDTO;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.AuthServiceException;
import com.bernardawj.notey.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service(value = "authService")
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepositoryImpl userRepository;

    @Autowired
    public AuthServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) throws AuthServiceException {
        Optional<User> optUser = this.userRepository.findByEmailAndPassword(loginDTO.getEmail(),
                loginDTO.getPassword());
        User user = optUser.orElseThrow(() -> new AuthServiceException("AuthService.USER_NOT_FOUND"));

        return new UserDTO(user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName());
    }
}
