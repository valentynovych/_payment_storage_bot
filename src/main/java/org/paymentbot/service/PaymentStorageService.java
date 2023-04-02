package org.paymentbot.service;

import org.paymentbot.model.PaymentStorage;
import org.paymentbot.model.UserPaymentsDTO;
import org.paymentbot.repository.PaymentStorageRepository;
import org.paymentbot.model.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentStorageService {
    @Autowired
    private PaymentStorageRepository paymentStorageRepository;

    public List<PaymentStorage> getPaymentStorageByUser(UserStorage userStorage){
        return paymentStorageRepository.findAllByUserStorage(userStorage);
    }

    public List<PaymentStorage> getPaymentStorageByChatId(Long chatId){
        return paymentStorageRepository.findAllByUserStorage_ChatId(chatId);
    }

    public List<UserPaymentsDTO> findAllPaymentsAllUser(){
        return paymentStorageRepository.findAllPaymentsAllUser();
    }


}
