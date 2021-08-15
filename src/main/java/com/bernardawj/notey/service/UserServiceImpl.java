package com.bernardawj.notey.service;

import com.bernardawj.notey.dto.project.ProjectDTO;
import com.bernardawj.notey.dto.user.UserDTO;
import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.entity.User;
import com.bernardawj.notey.exception.UserServiceException;
import com.bernardawj.notey.repository.ProjectUserRepository;
import com.bernardawj.notey.repository.UserRepository;
import com.bernardawj.notey.utility.ProjectUserCompositeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProjectUserRepository projectUserRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProjectUserRepository projectUserRepository) {
        this.userRepository = userRepository;
        this.projectUserRepository = projectUserRepository;
    }

    @Override
    public UserDTO getUserDetails(Integer id) throws UserServiceException {
        // Check if user exists within the database
        Optional<User> optUser = this.userRepository.findById(id);
        User user = optUser.orElseThrow(() -> new UserServiceException("UserService.USER_NOT_FOUND"));

        // Populate assigned projects
        List<ProjectDTO> assignedProjects = new ArrayList<>();
        user.getProjectUsers().forEach(projectUser -> {
            UserDTO manager = new UserDTO(projectUser.getProject().getManager().getId(),
                    projectUser.getProject().getManager().getEmail(), null,
                    projectUser.getProject().getManager().getFirstName(),
                    projectUser.getProject().getManager().getLastName());
            assignedProjects.add(new ProjectDTO(projectUser.getProject().getId(), projectUser.getProject().getName(),
                    projectUser.getProject().getDescription(), projectUser.getProject().getStartAt(),
                    projectUser.getProject().getEndAt(), manager));
        });

        return new UserDTO(user.getId(), user.getEmail(), null, user.getFirstName(), user.getLastName(),
                assignedProjects);
    }

    @Override
    public void updateProjectAcceptance(Integer projectId, Integer userId, Boolean accept) throws UserServiceException {
        // Check if user and project exists within the database
        ProjectUserCompositeKey compositeKey = new ProjectUserCompositeKey(projectId, userId);
        Optional<ProjectUser> optProjectUser =
                this.projectUserRepository.findById(compositeKey);
        ProjectUser projectUser = optProjectUser.orElseThrow(() -> new UserServiceException("UserService" +
                ".NO_PROJECT_FOR_ACCEPTANCE"));

        // Delete project user if declined
        if (!accept) {
            this.projectUserRepository.deleteById(compositeKey);
            return;
        }

        // Perform acceptance on project and update the database
        projectUser.setHasAccepted(true);
        this.projectUserRepository.save(projectUser);
    }
}
