package org.paymentbot.handler.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.paymentbot.enums.ConversationState;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.model.UserRequest;
import org.paymentbot.model.UserSession;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserSessionService;

import static org.paymentbot.constant.Constants.BTN_ENTER_MAIL;

@Component
public class WaitEnteredMailHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public WaitEnteredMailHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate(), BTN_ENTER_MAIL);
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMailMenu();
        telegramService.sendMessage(userRequest.getChatId(),"Введи свій E-Mail (аккаунт з YouTube Premium)⤵️", replyKeyboardMarkup);

        UserSession userSession = userRequest.getUserSession();
        userSession.setState(ConversationState.WAITING_FOR_MAIL);
        userSessionService.saveSession(userSession.getChatId(), userSession);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

}
