package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    @Query("SELECT p FROM Project p WHERE p.manager.id = :managerId")
    Iterable<Project> findProjectsByManagerId(@Param("managerId") Integer managerId);

    @Query("SELECT p FROM Project p WHERE LOWER(p.name) = LOWER(:name) ")
    Optional<Project> findProjectByName(@Param("name") String name);

    @Query("SELECT p FROM Project p WHERE p.id = :projectId AND p.manager.id = :managerId")
    Optional<Project> findProjectByProjectIdAndManagerId(@Param("projectId") Integer projectId,
                                                         @Param("managerId") Integer managerId);

    @Query("SELECT p FROM Project p WHERE p.manager.id = :userId ORDER BY p.accessedAt DESC")
    Iterable<Project> findRecentlyAccessedProjects(@Param("userId") Integer userId);
}
