package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {

    @Query("SELECT p FROM Project p WHERE p.manager.id = :managerId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :filter," +
            " '%'))")
    Page<Project> findProjectsByManagerId(@Param("managerId") Integer managerId, @Param("filter") String filter,
                                          Pageable pageable);

    @Query("SELECT p FROM Project p INNER JOIN ProjectUser pu ON p.id = pu.projectId WHERE pu.userId = :userId " +
            "AND LOWER(p.name) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Project> findProjectsByUserId(@Param("userId") Integer userId, @Param("filter") String filter,
                                       Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.id = :projectId AND p.manager.id = :managerId")
    Optional<Project> findProjectByProjectIdAndManagerId(@Param("projectId") Integer projectId,
                                                         @Param("managerId") Integer managerId);

    @Query("SELECT p FROM Project p LEFT JOIN ProjectUser pu ON p.id = pu.projectId WHERE p.id = :projectId AND (p" +
            ".manager.id = :userId OR pu.userId = :userId)")
    Optional<Project> findProjectByProjectIdAndManagerIdOrUserId(@Param("projectId") Integer projectId,
                                                                 @Param("userId") Integer userId);
}
