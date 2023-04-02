package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.model.*;
import org.paymentbot.service.PaymentStorageService;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserBalanceStorageService;
import org.paymentbot.service.UserStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CheckPaymentUsersHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final PaymentStorageService paymentStorageService;
    private final UserStorageService userStorageService;
    private final UserBalanceStorageService userBalanceStorageService;
    private final KeyboardHelper keyboardHelper;

    @Value("${bot.owner}")
    private Long botOwner;

    public CheckPaymentUsersHandler(TelegramService telegramService,
                                    PaymentStorageService paymentStorageService,
                                    UserStorageService userStorageService,
                                    UserBalanceStorageService userBalanceStorageService, KeyboardHelper keyboardHelper) {
        this.telegramService = telegramService;
        this.paymentStorageService = paymentStorageService;
        this.userStorageService = userStorageService;
        this.userBalanceStorageService = userBalanceStorageService;

        this.keyboardHelper = keyboardHelper;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), Constants.COMMAND_CHECK);
    }

    @Override
    public void handle(UserRequest userRequest) {

        if (userRequest.getChatId().equals(botOwner)) {

            List<Long> listChatId= userStorageService.findAllChatIdUser();
            userBalanceStorageService.checkIsEmptyBalance(listChatId);

            List<UserPaymentsDTO> userPaymentDTOList = paymentStorageService.findAllPaymentsAllUser();
            userPaymentDTOList.stream().map(UserPaymentsDTO::getFirstName);

            var message = generateCheckPaymentMessage(userPaymentDTOList);

            telegramService.sendMessage(userRequest.getChatId(), message);

        } else {

            telegramService.sendMessage(userRequest.getChatId(), "Sorry !!!\nУ вас немає доступу до цієї команди", keyboardHelper.buildMailMenu());
        }
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    private String generateCheckPaymentMessage(List<UserPaymentsDTO> userPaymentDTOList) {

        String message = "";
        for (UserPaymentsDTO user : userPaymentDTOList) {
            if (user == null) {
                continue;
            }

            if (!message.contains(user.getFirstName())){
                message += "=====================\nUser: " + user.getFirstName() + "\n";
            }
                message += String.format("Payment: %s Date: %s\n", user.getValue(), user.getDate());

            }
        return message;
        }

        }

