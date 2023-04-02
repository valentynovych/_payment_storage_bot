package org.paymentbot.service;

import com.google.common.collect.ImmutableList;
import org.paymentbot.model.UserBalanceStorage;
import org.paymentbot.model.UserStorage;
import org.paymentbot.repository.UserBalanceStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<UserBalanceStorage> balanceStorages = findALl();
        if (balanceStorages.isEmpty()) {
            saveAll(addMissingBalance(listChatId));
        } else if (listChatId.size() > balanceStorages.size()) {
            List<Long> listEqualsChatId = new ArrayList<>();
                for (UserBalanceStorage userBalance : balanceStorages) {
                    listEqualsChatId.add(userBalance.getUserStorage().getChatId());
                }

            listChatId.removeAll(listEqualsChatId);
            saveAll(addMissingBalance(listChatId));
            }
        }

    private List<UserBalanceStorage> addMissingBalance(List<Long> chaiIdList){

        List<UserBalanceStorage> userEmptyList = new ArrayList<>();
        for (Long chatId : chaiIdList) {
            userEmptyList.add(createNewBalance(chatId));
        }
        return userEmptyList;
    }

    public void addUserBalance(Long chatId){

        var newBalance = createNewBalance(chatId);
        userBalanceStorageRepository.save(newBalance);

    }

    private UserBalanceStorage createNewBalance(Long chatId){

        UserBalanceStorage newBalance = new UserBalanceStorage();
        UserStorage userStorage = new UserStorage();
        userStorage.setChatId(chatId);
        newBalance.setUserStorage(userStorage);
        newBalance.setUserBalance(0.0);
        return newBalance;
    }
}
