package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.model.UserRequest;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SendMessageAllUsersHandler extends UserRequestHandler {
    @Value("${bot.owner}")
    private Long ownerChatId;

    private final TelegramService telegramService;
    private final UserStorageService userStorageService;
    private final KeyboardHelper keyboardHelper;

    public SendMessageAllUsersHandler(TelegramService telegramService,
                                      UserStorageService userStorageService,
                                      KeyboardHelper keyboardHelper) {
        this.telegramService = telegramService;
        this.userStorageService = userStorageService;
        this.keyboardHelper = keyboardHelper;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate())
                && request.getUpdate().getMessage().getText().contains(Constants.COMMAND_SEND_ALL);
    }

    @Override
    public void handle(UserRequest dispatchRequest) {
        if (dispatchRequest.getChatId().equals(ownerChatId)) {
            String message = dispatchRequest.getUpdate().getMessage().getText();
            message = message.substring(6);

            List<Long> chatIdsList = userStorageService.findAllChatIdUser();
            for (Long chatId : chatIdsList){
                telegramService.sendMessage(chatId, message, keyboardHelper.buildMenuPayment());
            }
        } else {
            telegramService.sendMessage(dispatchRequest.getChatId(),
                    "Sorry !\nУ вас немає доступу до цієї команди", keyboardHelper.buildMainMenu());
        }
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
