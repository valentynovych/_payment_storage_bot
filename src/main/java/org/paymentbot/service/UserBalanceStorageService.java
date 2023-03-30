package org.paymentbot.service;

import com.google.common.collect.ImmutableList;
import org.paymentbot.model.UserBalanceStorage;
import org.paymentbot.model.UserStorage;
import org.paymentbot.repository.UserBalanceStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserBalanceStorageService {
    @Autowired
    private UserBalanceStorageRepository userBalanceStorageRepository;
    public Optional<UserBalanceStorage> findById(Long chatId){
        return userBalanceStorageRepository.findById(chatId);
    }
    public void saveBalance(UserBalanceStorage userBalanceStorage){
        userBalanceStorageRepository.save(userBalanceStorage);
    }

    public void saveAll(Iterable<UserBalanceStorage> userBalanceStorageList){
        userBalanceStorageRepository.saveAll(userBalanceStorageList);
    }

    public List<UserBalanceStorage> findALl() {
        Iterable<UserBalanceStorage> userBalanceStorage = userBalanceStorageRepository.findAll();
        return ImmutableList.copyOf(userBalanceStorage);
    }

    public void updateBalance(Long chatId, Double balance){
        userBalanceStorageRepository.updateBalance(chatId, balance);
    }

    public void checkIsEmptyBalance(List<Long> listChatId) {
        List<UserBalanceStorage> optionalList = findALl();
        if (optionalList.isEmpty()) {
            saveAll(addMissingBalance(listChatId));
        } else {

            if (listChatId.size() > optionalList.size()) {
                for (UserBalanceStorage userBalance : optionalList) {
                    List<Long> listEqualsChatId = new ArrayList<>();
                    listEqualsChatId.add(userBalance.getUserChatId().getChatId());
                    listChatId.removeAll(listEqualsChatId);
                    saveAll(addMissingBalance(listChatId));
                }
            }
        }
    }

    private List<UserBalanceStorage> addMissingBalance(List<Long> chaiId){
        List<UserBalanceStorage> userEmptyList = new ArrayList<UserBalanceStorage>();
        for (Long chatId : chaiId) {
            UserBalanceStorage newBalance = new UserBalanceStorage();
            UserStorage userStorage = new UserStorage();
            userStorage.setChatId(chatId);
            newBalance.setUserChatId(userStorage);
            newBalance.setUserBalance(0.0);
            userEmptyList.add(newBalance);
        }
        return userEmptyList;
    }
}
