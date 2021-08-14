package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.UserDTO;
import com.bernardawj.notey.dto.user.RegisterDTO;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.AuthServiceException;
import com.bernardawj.notey.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootTest
public class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void invalidEmailExistsOnRegisterShouldThrowException() {
        // Mock DTO
        RegisterDTO registerDTO = new RegisterDTO("dummy@test.com", "password", "Dummy", "User");

        // Mock the behavior of repository
        Mockito.when(this.userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.of(new User()));

        // Check if method indeed throws an exception
        AuthServiceException ex = Assertions.assertThrows(AuthServiceException.class,
                () -> this.authService.register(registerDTO));

        // Check if exception message thrown is the same
        Assertions.assertEquals("AuthService.USER_EXISTS", ex.getMessage());
    }

    @Test
    public void validOnRegisterShouldReturnDTO() throws AuthServiceException {
        // Mock DTO
        RegisterDTO registerDTO = new RegisterDTO("dummy@test.com", "password", "Dummy", "User");

        // Mock entity
        User user = new User(registerDTO.getEmail(), new BCryptPasswordEncoder().encode(registerDTO.getPassword()),
                registerDTO.getFirstName(), registerDTO.getLastName());

        // Mock the behavior of repository
        Mockito.when(this.userRepository.findUserByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Assert the returned DTO
        UserDTO userDTO = this.authService.register(registerDTO);
        Assertions.assertEquals(userDTO.getEmail(), user.getEmail());
        Assertions.assertNull(userDTO.getPassword());
        Assertions.assertEquals(userDTO.getFirstName(), user.getFirstName());
        Assertions.assertEquals(userDTO.getLastName(), user.getLastName());
    }
}
