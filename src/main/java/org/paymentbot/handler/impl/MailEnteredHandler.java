package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.paymentbot.model.UserStorage;
import org.paymentbot.repository.UserStorageRepository;
import org.paymentbot.service.UserStorageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.paymentbot.enums.ConversationState;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.model.UserRequest;
import org.paymentbot.model.UserSession;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserSessionService;

import java.util.Optional;

@Component
public class MailEnteredHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;
    private final UserStorageRepository userStorageRepository;

    public MailEnteredHandler(TelegramService telegramService,
                              KeyboardHelper keyboardHelper,
                              UserSessionService userSessionService,
                              UserStorageRepository userStorageRepository) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
        this.userStorageRepository = userStorageRepository;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && (ConversationState.WAITING_FOR_MAIL.equals(userRequest.getUserSession().getState())
                || isTextMessage(userRequest.getUpdate(), Constants.BTN_IN_REGISTER));
    }

    @Override
    public void handle(UserRequest userRequest) {

        if (!isTextMessage(userRequest.getUpdate(), Constants.BTN_IN_REGISTER)){

        String enteredMail = userRequest.getUpdate().getMessage().getText();
        registerMailUser(userRequest.getUpdate().getMessage(), enteredMail);
        }

        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMenuPayment();
        telegramService.sendMessage(userRequest.getChatId(),
                "Обери дію з меню нижче ⤵️",
                replyKeyboardMarkup);

        UserSession session = userRequest.getUserSession();
        session.setState(ConversationState.STAY_IN_MENU);
        userSessionService.saveSession(userRequest.getChatId(), session);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    public void registerMailUser(Message message, String mailUser) {

        Optional<UserStorage> userStorageOptional = userStorageRepository.findById(message.getChatId());

            var chatId = message.getChatId();
            var chat = message.getChat();

            if(userStorageOptional.isPresent()){

                userStorageOptional.get().setMail(mailUser);
                userStorageRepository.save(userStorageOptional.get());
            } else {
                UserStorage userStorage = new UserStorage();
                userStorage.setChatId(chatId);
                userStorage.setMail(mailUser);
                userStorage.setUsername(chat.getUserName());
                userStorage.setFirstName(chat.getFirstName());
                userStorage.setLastName(chat.getLastName());
                userStorageRepository.save(userStorage);
            }
        }
    }



