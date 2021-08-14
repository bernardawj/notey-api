package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.auth.LoginDTO;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.dto.auth.RegisterDTO;
import com.bernardawj.notey.exception.AuthServiceException;

public interface AuthService {

    UserDTO login(LoginDTO loginDTO) throws AuthServiceException;

    UserDTO register(RegisterDTO registerDTO) throws AuthServiceException;
}
