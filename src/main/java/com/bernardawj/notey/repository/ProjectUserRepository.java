package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.ProjectUser;
import com.bernardawj.notey.utility.ProjectUserCompositeKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectUserRepository extends CrudRepository<ProjectUser, ProjectUserCompositeKey> {

    @Query("SELECT pu FROM ProjectUser pu WHERE pu.projectId = :projectId AND pu.userId = :userId")
    Optional<ProjectUser> findByProjectIdAndUserId(@Param("projectId") Integer projectId,
                                                   @Param("userId") Integer userId);
}
