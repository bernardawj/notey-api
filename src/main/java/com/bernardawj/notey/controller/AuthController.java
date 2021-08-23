package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.auth.AuthDTO;
import com.bernardawj.notey.dto.auth.LoginDTO;
import com.bernardawj.notey.dto.auth.RegisterDTO;
import com.bernardawj.notey.dto.auth.TokenDTO;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.exception.AuthServiceException;
import com.bernardawj.notey.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginDTO loginDTO) throws AuthServiceException {
        AuthDTO authDTO = this.authService.generateAuthenticationToken(loginDTO);
        return new ResponseEntity<>(authDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterDTO registerDTO) throws AuthServiceException {
        UserDTO userDTO = this.authService.register(registerDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
