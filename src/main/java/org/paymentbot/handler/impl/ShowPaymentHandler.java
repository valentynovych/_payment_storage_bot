package org.paymentbot.handler.impl;

import org.paymentbot.constant.Constants;
import org.paymentbot.handler.UserRequestHandler;
import org.paymentbot.helper.KeyboardHelper;
import org.paymentbot.model.PaymentStorage;
import org.paymentbot.model.UserRequest;
import org.paymentbot.service.PaymentStorageService;
import org.paymentbot.service.TelegramService;
import org.paymentbot.service.UserBalanceStorageService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class        ShowPaymentHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final PaymentStorageService paymentStorageService;
    private final UserBalanceStorageService userBalanceStorageService;


    public ShowPaymentHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, PaymentStorageService paymentStorageService, UserBalanceStorageService userBalanceStorageService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.paymentStorageService = paymentStorageService;
        this.userBalanceStorageService = userBalanceStorageService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate(), Constants.BTN_VIEW_PAYMENT);
    }

    @Override
    public void handle(UserRequest userRequest) {
        String message = generatePaymentMessage(userRequest);
        telegramService.sendMessage(userRequest.getChatId(),
                "Твої останні оплати ⤵️ \n" + message, keyboardHelper.buildMenuPayment());
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    private String generatePaymentMessage(UserRequest userRequest){
        var chatId = userRequest.getChatId();
        List<PaymentStorage> paymentStoragesList = paymentStorageService.getPaymentStorageByChatId(chatId);
        var balanceStorage = userBalanceStorageService.findById(chatId);
        var balance = balanceStorage.get().getUserBalance();

        String message = "\n";
        if (paymentStoragesList.isEmpty()){
            message += "У тебе ще немає зареєстрованих оплат\n" +
                    "Відправ мені свою останню оплату в розділі '*Внести оплату*' ";
        }
        for (PaymentStorage payment : paymentStoragesList){
            if (payment == null){
                continue;
            }
                var srr = payment.getUserStorage().getFirstName();
                message += (srr + "  " + payment.getDate() + " : " + payment.getValue() + " грн; \n") +
                        "-----------------------------\n";
        }
        return message + "\nТвій баланс: " + balance + " грн.";
    }
}
