package com.smart.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.UserHistory;
@Service
public class UserHistoryService {
    @Autowired
    private UserHistoryRepository userHistoryRepository;

    /**
     * Retrieves all entries for the given userId.
     * 
     * @param userId the ID of the user
     * @return a list of UserHistory entries for the specified userId
     */
    public List<UserHistory> getAllEntriesForUserId(Integer userId) {
        return userHistoryRepository.findByUserId(userId);
    }
}
