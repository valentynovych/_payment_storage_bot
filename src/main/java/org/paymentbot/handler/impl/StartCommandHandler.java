package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.paymentbot.model.UserStorage;
import org.paymentbot.repository.UserStorageRepository;
import org.paymentbot.service.UserBalanceStorageService;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.model.UserRequest;
import org.paymentbot.service.TelegramService;

import java.util.Optional;

@Component
public class StartCommandHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserStorageRepository userStorageRepository;
    private final UserBalanceStorageService userBalanceStorageService;

    public StartCommandHandler(TelegramService telegramService,
                               KeyboardHelper keyboardHelper,
                               UserStorageRepository userStorageRepository, UserBalanceStorageService userBalanceStorageService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userStorageRepository = userStorageRepository;
        this.userBalanceStorageService = userBalanceStorageService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), Constants.COMMAND_START);
    }

    @Override
    public void handle(UserRequest request) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMainMenu();
        telegramService.sendMessage(request.getChatId(),
                "\uD83D\uDC4BПривіт, " + request.getUpdate().getMessage().getChat().getFirstName() +
                        "\nВітаю тебе в боті\n" +
                        "За допомогою цього чат-бота ти можеш реєструвати свої оплати за користування" +
                        "сервісом YouTune Premium\n" +
                        "\nТицяй на меню нижче ⤵️",
                replyKeyboard);

        registerUser(request.getUpdate().getMessage());
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    public void registerUser(Message message) {

        var chatId = message.getChatId();
        var chat = message.getChat();

        Optional<UserStorage> userStorageOptional = userStorageRepository.findById(chatId);
        UserStorage userStorage;

        if(userStorageOptional.isPresent()){
                userStorage = userStorageOptional.get();
                userStorage.setFirstName(chat.getFirstName());
                userStorage.setLastName(chat.getLastName());
                userStorage.setUsername(chat.getUserName());
                userStorageRepository.save(userStorage);

        } else {
                userStorage = new UserStorage();
                userStorage.setChatId(chatId);
                userStorage.setUsername(chat.getUserName());
                userStorage.setFirstName(chat.getFirstName());
                userStorage.setLastName(chat.getLastName());

                userStorageRepository.save(userStorage);
                userBalanceStorageService.addUserBalance(chatId);

                }
    }
}
