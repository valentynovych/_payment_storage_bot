package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.paymentbot.enums.ConversationState;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.model.UserRequest;
import org.paymentbot.model.UserSession;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserSessionService;


@Component
public class CancelHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public CancelHandler(TelegramService telegramService,
                         KeyboardHelper keyboardHelper,
                         UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate(), Constants.BTN_GO_BACK);
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMainMenu();
        telegramService.sendMessage(userRequest.getChatId(),
                "Обирай з меню нижче ⤵️", replyKeyboard);

        UserSession userSession = userRequest.getUserSession();
        userSession.setState(ConversationState.CONVERSATION_STARTED);
        userSessionService.saveSession(userSession.getChatId(), userSession);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
