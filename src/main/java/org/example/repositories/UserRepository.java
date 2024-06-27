package org.example.repositories;

import org.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,Integer>{

    User save(User user);

    Boolean existsByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

    User findByUserName(String userName);

}
