package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.UserAttendanceHistory;

public interface UserHistoryRepo extends JpaRepository<UserAttendanceHistory,Long>{

}
