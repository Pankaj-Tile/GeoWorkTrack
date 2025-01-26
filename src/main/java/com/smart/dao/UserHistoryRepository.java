package com.smart.dao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.UserHistory;

public interface UserHistoryRepository extends  JpaRepository <UserHistory, Integer>{
      @Query("SELECT u FROM UserHistory u WHERE u.userId = :userId AND u.exitTime IS NULL")
    Optional<UserHistory> findActiveEntry(@Param("userId") Integer userId);

    @Query("SELECT u FROM UserHistory u WHERE u.userId = :userId")
    List<UserHistory> findByUserId(@Param("userId") Integer userId);
}
