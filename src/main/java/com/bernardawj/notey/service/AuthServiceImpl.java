package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.auth.LoginDTO;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.dto.auth.RegisterDTO;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.AuthServiceException;
import com.bernardawj.notey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service(value = "authService")
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) throws AuthServiceException {
        Optional<User> optUser = this.userRepository.findUserByEmailAndPassword(loginDTO.getEmail(),
                loginDTO.getPassword());
        User user = optUser.orElseThrow(() -> new AuthServiceException("AuthService.USER_NOT_FOUND"));

        return new UserDTO(user.getId(), user.getEmail(), null, user.getFirstName(), user.getLastName());
    }

    @Override
    public UserDTO register(RegisterDTO registerDTO) throws AuthServiceException {
        // Check if user exists within the database
        Optional<User> optUser = this.userRepository.findUserByEmail(registerDTO.getEmail());
        if (optUser.isPresent())
            throw new AuthServiceException("AuthService.USER_EXISTS");

        // Store it into database
        User user = new User(registerDTO.getEmail(), new BCryptPasswordEncoder().encode(registerDTO.getPassword()),
                registerDTO.getFirstName(), registerDTO.getLastName());
        this.userRepository.save(user);

        // Return DTO
        return new UserDTO(user.getId(), user.getEmail(), null, user.getFirstName(), user.getLastName());
    }
}
