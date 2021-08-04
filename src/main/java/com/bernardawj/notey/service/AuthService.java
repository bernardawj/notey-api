package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.LoginDTO;
import com.bernardawj.notey.dto.UserDTO;
import com.bernardawj.notey.exception.AuthServiceException;

public interface AuthService {

    UserDTO login(LoginDTO loginDTO) throws AuthServiceException;
}
