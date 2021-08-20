package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.Task;
import com.bernardawj.notey.enums.TaskType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.name = :taskName")
    Optional<Task> findByProjectIdAndTaskName(@Param("projectId") Integer projectId,
                                              @Param("taskName") String taskName);

    @Query("SELECT t FROM Task t WHERE t.id = :taskId AND t.user.id = :userId")
    Optional<Task> findByTaskIdAndUserId(@Param("taskId") Integer taskId, @Param("userId") Integer userId);

    @Query("SELECT t FROM Task t WHERE t.id = :taskId AND t.project.manager.id = :managerId")
    Optional<Task> findByTaskIdAndManagerId(@Param("taskId") Integer taskId, @Param("managerId") Integer managerId);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Task> findAllByUserIdAndPagination(@Param("userId") Integer userId, @Param("filter") String filter,
                                            Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.user.id = :projectId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, " +
            "'%')) AND t.type = :type")
    Page<Task> findAllByUserIdAndPaginationAndType(@Param("projectId") Integer userId,
                                                   @Param("filter") String filter, @Param("type") TaskType type,
                                                   Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.user.id = :projectId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, " +
            "'%')) AND t.isCompleted = :completed")
    Page<Task> findAllByUserIdAndPaginationAndCompleted(@Param("projectId") Integer userId,
                                                        @Param("filter") String filter,
                                                        @Param("completed") Boolean completed,
                                                        Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.user.id = :projectId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, " +
            "'%')) AND t.type = :type AND t.isCompleted = :completed")
    Page<Task> findAllByUserIdAndPaginationAndTypeOrCompleted(@Param("projectId") Integer userId,
                                                              @Param("filter") String filter,
                                                              @Param("type") TaskType type,
                                                              @Param("completed") Boolean completed,
                                                              Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, " +
            "'%'))")
    Page<Task> findAllByProjectIdAndPagination(@Param("projectId") Integer projectId, @Param("filter") String filter,
                                               Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, " +
            "'%')) AND t.type = :type")
    Page<Task> findAllByProjectIdAndPaginationAndType(@Param("projectId") Integer projectId,
                                                      @Param("filter") String filter, @Param("type") TaskType type,
                                                      Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, " +
            "'%')) AND t.isCompleted = :completed")
    Page<Task> findAllByProjectIdAndPaginationAndCompleted(@Param("projectId") Integer projectId,
                                                           @Param("filter") String filter,
                                                           @Param("completed") Boolean completed,
                                                           Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, " +
            "'%')) AND t.type = :type AND t.isCompleted = :completed")
    Page<Task> findAllByProjectIdAndPaginationAndTypeOrCompleted(@Param("projectId") Integer projectId,
                                                                 @Param("filter") String filter,
                                                                 @Param("type") TaskType type,
                                                                 @Param("completed") Boolean completed,
                                                                 Pageable pageable);
}
