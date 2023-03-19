package org.paymentbot.handler.impl;

import org.paymentbot.model.*;
import org.paymentbot.repository.PaymentStorageRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.paymentbot.enums.ConversationState;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserSessionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class PaymentEnteredHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;
    private final PaymentStorageRepository paymentStorageRepository;

    public PaymentEnteredHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService, PaymentStorageRepository paymentStorageRepository) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
        this.paymentStorageRepository = paymentStorageRepository;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && ConversationState.WAITING_ENTER_PAYMENT.equals(userRequest.getUserSession().getState());
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMenuAfterPayment();
        String payment = userRequest.getUpdate().getMessage().getText();
        saveUserPayment(userRequest.getUpdate().getMessage(), payment);
        telegramService.sendMessage(userRequest.getChatId(), "Дякую !!!\n" +
                "Твоя оплата у розмірі " + payment + " грн, збережена\n" +
                "Ти заслуговуєш на + в карму, і на ще один місяць корисування YouTube Premium", replyKeyboardMarkup);

        UserSession session = userRequest.getUserSession();
        session.setState(ConversationState.STAY_IN_MENU);
        userSessionService.saveSession(userRequest.getChatId(), session);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }


    public void saveUserPayment(Message message, String payment){

        var chatId = message.getChatId();
        var dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
        var datePayment = LocalDateTime.now();
        String formatDatePayment = datePayment.format(dtf);

        PaymentStorage paymentStorage = new PaymentStorage();
        UserStorage userStorage = new UserStorage();

        userStorage.setChatId(chatId);
        paymentStorage.setValue(Double.valueOf(payment));
        paymentStorage.setUserStorage(userStorage);
        paymentStorage.setDate(formatDatePayment);
        paymentStorageRepository.save(paymentStorage);
    }
}
