package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.model.*;
import org.paymentbot.repository.UserBalanceStorageRepository;
import org.paymentbot.service.PaymentStorageService;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserBalanceStorageService;
import org.paymentbot.service.UserStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CheckPaymentUsersHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final PaymentStorageService paymentStorageService;
    private final UserStorageService userStorageService;
    private final UserBalanceStorageService userBalanceStorageService;

    @Value("${bot.owner}")
    private Long botOwner;

    public CheckPaymentUsersHandler(TelegramService telegramService,
                                    PaymentStorageService paymentStorageService,
                                    UserStorageService userStorageService,
                                    UserBalanceStorageService userBalanceStorageService) {
        this.telegramService = telegramService;
        this.paymentStorageService = paymentStorageService;
        this.userStorageService = userStorageService;
        this.userBalanceStorageService = userBalanceStorageService;

    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), Constants.COMMAND_CHECK);
    }

    @Override
    public void handle(UserRequest userRequest) {

        if (userRequest.getChatId().equals(botOwner)) {

            List<Long> listChatId= userStorageService.findAllChatIdUser();
            List<UserPaymentDTO> userPaymentDTOList= new ArrayList<>();

            userBalanceStorageService.checkIsEmptyBalance(listChatId);

            for (Long chatId : listChatId ) {
                UserPaymentDTO userPaymentDTO = paymentStorageService.getUserPaymentDTO(chatId);
                userPaymentDTOList.add(userPaymentDTO);
            }
            telegramService.sendMessage(userRequest.getChatId(), generateCheckPaymentMessage(userPaymentDTOList));
        } else {
            telegramService.sendMessage(userRequest.getChatId(), "Sorry !\nУ вас немає доступу до цієї команди");
        }
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    private String generateCheckPaymentMessage(List<UserPaymentDTO> userPaymentDTOList) {

        String message = "";
        for (UserPaymentDTO user : userPaymentDTOList) {
            if (user == null) {
                continue;
            }
            message += "User: " + user.getUserFirstName() + "\n";
            List<PaymentStorage> paymentStorageList = user.getPaymentStorageList();
            for (PaymentStorage paymentStorage : paymentStorageList){
                if (paymentStorage == null) {
                    message += "Payment null\n";
            } else {
                    message += String.format("Payment: %s, Date: %s\n",
                            paymentStorage.getValue(), paymentStorage.getDate());
                }
            }
            message += "=====================\n";
        }
            return message;
        }




    }
