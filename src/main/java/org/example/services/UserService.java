package org.example.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.example.model.User;
import org.example.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public List<User> getUser(){
        return userRepository.findAll();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public boolean existsUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public boolean existsAccount(String userName, String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);
        return user != null;
    }

    public User foundUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User login(String userName, String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);
        return user;
    }

    public User register(User user) {
        return userRepository.save(user);
    }


}
