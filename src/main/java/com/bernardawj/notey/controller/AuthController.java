package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.LoginDTO;
import com.bernardawj.notey.dto.UserDTO;
import com.bernardawj.notey.exception.AuthServiceException;
import com.bernardawj.notey.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO) throws AuthServiceException {
        UserDTO userDTO = this.authService.login(loginDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
