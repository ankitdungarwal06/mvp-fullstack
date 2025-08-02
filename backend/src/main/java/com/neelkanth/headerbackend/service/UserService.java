package com.neelkanth.headerbackend.service;

import com.neelkanth.headerbackend.entity.User;
import com.neelkanth.headerbackend.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        User response = userRepository.save(user);
        return response;
    }

    public List<User> fetchUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User fetchUserById(Long id){
        User response = userRepository.findById(id).orElse(new User());
        if(response != null){
            Hibernate.initialize(response.getImportantDates());
        }
        return response;
    }

    public User deleteUser(Long id){
        User user = fetchUserById(id);
        userRepository.delete(user);
        return user;
    }
}
