package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.model.UserPaymentDTO;
import org.paymentbot.model.UserRequest;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserPaymentDTOService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class CheckPaymentUsersHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final UserPaymentDTOService userPaymentDTOService;
    @Value("${bot.owner}")
    private Long botOwner;

    public CheckPaymentUsersHandler(TelegramService telegramService, UserPaymentDTOService userPaymentDTOService) {
        this.telegramService = telegramService;
        this.userPaymentDTOService = userPaymentDTOService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), Constants.COMMAND_CHECK);
    }

    @Override
    public void handle(UserRequest userRequest) {

        if (userRequest.getChatId().equals(botOwner)) {
            List<UserPaymentDTO> userPaymentDTOList = userPaymentDTOService.findAllUserPaymentByChatId();
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
                if (!message.contains(user.getUserFirstName())) {
                    message += String.format("=========================" +
                            "\nUser: %s\n", user.getUserFirstName());
                }
                message += String.format("  %s грн, Дата: %s\n",
                        user.getValueUserPayment(), user.getDateUserPayment().substring(0,10));
            }
            return message;
        }
    }
