package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.exception.UserServiceException;

public interface UserService {

    UserDTO getUserDetails(Integer id) throws UserServiceException;
}
