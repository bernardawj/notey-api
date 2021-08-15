package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    @Query("SELECT p FROM Project p WHERE p.manager.id = :managerId")
    Iterable<Project> findProjectsByManagerId(@Param("managerId") Integer managerId);

    @Query("SELECT p FROM Project p WHERE p.id = :projectId AND p.manager.id = :managerId")
    Optional<Project> findProjectByProjectIdAndManagerId(@Param("projectId") Integer projectId,
                                                         @Param("managerId") Integer managerId);

    @Query("SELECT p FROM Project p LEFT JOIN ProjectUser pu ON p.id = pu.projectId WHERE p.id = :projectId AND (p" +
            ".manager.id = :userId OR pu.userId = :userId)")
    Optional<Project> findProjectByProjectIdAndManagerIdOrUserId(@Param("projectId") Integer projectId,
                                                                 @Param("userId") Integer userId);
}
