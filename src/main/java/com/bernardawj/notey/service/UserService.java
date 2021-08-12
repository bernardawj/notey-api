package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.UserDTO;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;

public interface UserService {

    UserDTO getUserDetails(Integer id) throws UserServiceException;

    void updateProjectAcceptance(Integer projectId, Integer userId, Boolean accept) throws UserServiceException;
}
