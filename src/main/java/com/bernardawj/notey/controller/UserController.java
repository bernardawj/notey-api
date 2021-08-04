package com.bernardawj.notey.controller;

import com.bernardawj.notey.dto.UserDTO;
import com.bernardawj.notey.exception.UserServiceException;
import com.bernardawj.notey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable("id") Integer id) throws UserServiceException {
        UserDTO userDTO = this.userService.getUserDetails(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
