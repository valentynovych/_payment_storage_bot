package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.paymentbot.enums.ConversationState;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.model.UserRequest;
import org.paymentbot.model.UserSession;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


@Component
public class WaitEnteredPaymentHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public WaitEnteredPaymentHandler(TelegramService telegramService,
                                     KeyboardHelper keyboardHelper,
                                     UserSessionService userSessionService) {

        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate(), Constants.BTN_ENTER_PAYMENT);
    }

    @Override
    public void handle(UserRequest userRequest) {

        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMenuAfterPayment();
        telegramService.sendMessage(userRequest.getChatId(),"Введи нижче твою останню оплату за сервіс⤵️", replyKeyboardMarkup);

        UserSession userSession = userRequest.getUserSession();
        userSession.setState(ConversationState.WAITING_ENTER_PAYMENT);
        userSessionService.saveSession(userSession.getChatId(), userSession);

    }

    @Override
    public boolean isGlobal() {
        return true;
    }

}


