package org.paymentbot.service;

import org.paymentbot.model.PaymentStorage;
import org.paymentbot.repository.PaymentStorageRepository;
import org.paymentbot.model.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
