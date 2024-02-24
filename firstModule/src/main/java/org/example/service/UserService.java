package org.example.service;

import org.example.domain.UserEntity;
import org.example.dto.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
     public User add(User user){
         UserEntity userEntity = new UserEntity();
         userEntity.setName(user.getName());
         userEntity.setEmail(user.getEmail());
         userEntity.setMobileNumber(user.getMobileNumber());
       var addUser =  userRepository.save(userEntity);
       return new User(addUser.getName(),addUser.getEmail(),addUser.getMobileNumber());
     }
     public User find(Long id){
        var findUser= userRepository.findById(id);
        User user = new User();
        findUser.ifPresent(t-> {
            user.setName(t.getName());
            user.setEmail(t.getEmail());
            user.setMobileNumber(t.getMobileNumber());
        });
        return user;
     }
}
