package org.example.service;

import org.example.domain.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomBean {
    private final UserRepository userRepository;

    public CustomBean(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void changeState(UserEntity userEntity){
        userEntity.setName(userEntity.getName()+"change");
        userRepository.save(userEntity);
    }
}
