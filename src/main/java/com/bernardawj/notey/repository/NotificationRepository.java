package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.id = :notificationId AND n.user.id = :toUserId")
    Optional<Notification> findByNotificationIdAndUserId(@Param("notificationId") Integer notificationId, @Param(
            "toUserId") Integer toUserId);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId ORDER BY n.createdAt DESC")
    Iterable<Notification> findAllByUserId(@Param("userId") Integer userId);
}
