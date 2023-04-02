package org.paymentbot.handler.impl;

import org.paymentbot.model.*;
import org.paymentbot.repository.PaymentStorageRepository;
import org.paymentbot.service.UserBalanceStorageService;
import org.springframework.stereotype.Component;
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
    private final UserBalanceStorageService userBalanceStorageService;

    public PaymentEnteredHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService, PaymentStorageRepository paymentStorageRepository, UserBalanceStorageService userBalanceStorageService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
        this.paymentStorageRepository = paymentStorageRepository;
        this.userBalanceStorageService = userBalanceStorageService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && ConversationState.WAITING_ENTER_PAYMENT.equals(userRequest.getUserSession().getState());
    }

    @Override
    public void handle(UserRequest userRequest) {
        var chatId = userRequest.getChatId();

        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMenuAfterPayment();
        Double payment = Double.valueOf(userRequest.getUpdate().getMessage().getText());

        saveUserPayment(chatId, payment);
        var balance = updateUserBalance(chatId, payment);

        telegramService.sendMessage(chatId, "Дякую !!!\n" +
                "Твоя оплата у розмірі " + payment + " грн, збережена\n" +
                "Твій поточний баланс: " + balance + " грн.\n" +
                "Ти заслуговуєш на + в карму, і на ще один місяць корисування YouTube Premium", replyKeyboardMarkup);

        UserSession session = userRequest.getUserSession();
        session.setState(ConversationState.STAY_IN_MENU);
        userSessionService.saveSession(chatId, session);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }


    public void saveUserPayment(Long chatId, Double payment){

        var dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
        var datePayment = LocalDateTime.now();
        String formatDatePayment = datePayment.format(dtf);

        PaymentStorage paymentStorage = new PaymentStorage();
        UserStorage userStorage = new UserStorage();

        userStorage.setChatId(chatId);
        paymentStorage.setValue(payment);
        paymentStorage.setUserStorage(userStorage);
        paymentStorage.setDate(formatDatePayment);
        paymentStorageRepository.save(paymentStorage);
    }

    public Double updateUserBalance(Long chatId, Double payment){

        var userBalanceStorage = userBalanceStorageService.findById(chatId);
        if (userBalanceStorage.isPresent()) {
            UserBalanceStorage balanceStorage = userBalanceStorage.get();
            Double balance = balanceStorage.getUserBalance() + payment;
            balanceStorage.setUserBalance(balance);
            userBalanceStorageService.updateBalance(chatId, balance);
            return balance;
        }
        return 0.0;
    }
}
