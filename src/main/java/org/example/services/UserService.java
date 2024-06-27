package org.example.services;

import org.springframework.stereotype.Service;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsAccount(String userName, String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);
        return user != null;
    }
    public boolean existsUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public void userRegister(User user){
        userRepository.save(user);
    }

    public User findUserByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

}
