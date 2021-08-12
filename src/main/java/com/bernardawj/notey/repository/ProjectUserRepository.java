package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.utility.ProjectUserCompositeKey;
import org.springframework.data.repository.CrudRepository;

public interface ProjectUserRepository extends CrudRepository<ProjectUser, ProjectUserCompositeKey> {
}
