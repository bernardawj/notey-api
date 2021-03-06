package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.auth.AuthDTO;
import com.bernardawj.notey.dto.auth.LoginDTO;
import com.bernardawj.notey.dto.auth.RegisterDTO;
import com.bernardawj.notey.dto.auth.TokenDTO;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.AuthServiceException;
import com.bernardawj.notey.repository.UserRepository;
import com.bernardawj.notey.security.JwtHelper;
import com.bernardawj.notey.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service(value = "authService")
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtHelper jwtHelper;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           UserDetailsServiceImpl userDetailsService, JwtHelper jwtHelper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public AuthDTO generateAuthenticationToken(LoginDTO loginDTO) throws AuthServiceException {
        // Authenticate user
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                loginDTO.getPassword()));

        // Generate token
        final String jwt = jwtHelper.generateToken(userDetails);
        final Date date = jwtHelper.extractExpiration(jwt);

        // Get user information and return DTO
        UserDTO user = this.getUserInformation(userDetails.getUsername());
        TokenDTO token = new TokenDTO(jwt, date);
        return new AuthDTO(user, token);
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
    
    private UserDTO getUserInformation(String email) throws AuthServiceException {
        Optional<User> optUser = this.userRepository.findUserByEmail(email);
        User user = optUser.orElseThrow(() -> new AuthServiceException("AuthService.USER_NOT_FOUND"));

        return new UserDTO(user.getId(), null, null, user.getFirstName(), user.getLastName());
    }
}
