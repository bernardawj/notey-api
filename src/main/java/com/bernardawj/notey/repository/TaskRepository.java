package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {


    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.name = :taskName")
    Optional<Task> findByProjectIdAndTaskName(@Param("projectId") Integer projectId, @Param("taskName") String taskName);

    @Query("SELECT t FROM Task t WHERE t.id = :taskId AND t.user.id = :userId")
    Optional<Task> findByTaskIdAndUserId(@Param("taskId") Integer taskId, @Param("userId") Integer userId);
}
