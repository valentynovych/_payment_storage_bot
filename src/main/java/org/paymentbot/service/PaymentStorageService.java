package org.paymentbot.service;

import org.paymentbot.model.PaymentStorage;
import org.paymentbot.model.UserPaymentDTO;
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

    public UserPaymentDTO getUserPaymentDTO(Long chatId) {
        List<Object[]> result = paymentStorageRepository.findAllPaymentsUserByChatId(chatId);
        if (result.isEmpty()) {
            return null;
        }
        String firstName = (String) result.get(0)[0];
        List<PaymentStorage> paymentStorageList = result.stream()
                .map(obj -> (PaymentStorage) obj[1])
                .collect(Collectors.toList());
        return new UserPaymentDTO(firstName, paymentStorageList);
    }

}
