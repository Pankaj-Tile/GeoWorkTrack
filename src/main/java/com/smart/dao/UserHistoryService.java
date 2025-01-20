package com.smart.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryService {
    @Autowired 
    private UserHistoryRepo userHistoryRepo;
    
}
