package org.paymentbot.service;

import org.paymentbot.model.UserStorage;
import org.paymentbot.repository.UserStorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStorageService {
    public final UserStorageRepository userStorageRepository;

    public UserStorageService(UserStorageRepository userStorageRepository) {
        this.userStorageRepository = userStorageRepository;
    }

    public List<Long> findAllChatIdUser(){
        return userStorageRepository.findAllChatIdUser();
    }


    public void saveUser(UserStorage userStorage){
        userStorageRepository.save(userStorage);
    }
}
