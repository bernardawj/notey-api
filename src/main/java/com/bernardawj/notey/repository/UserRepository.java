package com.bernardawj.notey.repository;

import com.bernardawj.notey.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id IN (:fromUserId, :toUserId)")
    List<User> findUsersByFromUserIdAndToUserId(@Param("fromUserId") Integer fromUserId,
                                                @Param("toUserId") Integer toUserId);
}
