package org.paymentbot.service;

import org.paymentbot.constant.Constants;
import org.paymentbot.model.UserBalanceStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchedulingService {
    private final TelegramService telegramService;
    private final UserStorageService userStorageService;
    private final UserBalanceStorageService userBalanceStorageService;
    @Value("${regularPaymentUAH}")
    private Double regularPayment;

    public SchedulingService(TelegramService telegramService,
                             UserStorageService userStorageService,
                             UserBalanceStorageService userBalanceStorageService) {

        this.telegramService = telegramService;
        this.userStorageService = userStorageService;
        this.userBalanceStorageService = userBalanceStorageService;
    }

    /**
     * Кожного місяця, 8-го числа о 10:15         \\(cron = "0 15 10 8 * ?")
     * Всім юзерам потрібно відправити повіломлення \\Constants.REGULAR_MESSAGE + subMessage
     * В повідомлені має міститись:
     * - Текст нагадування про необхідний платіж  \\Constants.REGULAR_MESSAGE
     * - Сума заборгованості                      \\actuallyBalance
     */
    @Scheduled(cron = "0 30 10 8 * ?")
    public void sendNotify(){

        List<Long> listChatId = userStorageService.findAllChatIdUser();
        List<UserBalanceStorage> balanceStorageList = userBalanceStorageService.findALl();

        userBalanceStorageService.checkIsEmptyBalance(listChatId);

        for (UserBalanceStorage userBalance : balanceStorageList){

            var chatId = userBalance.getUserStorage().getChatId();
            var balance = userBalance.getUserBalance();
            Double actuallyBalance;
            if (chatId == 414462566L){
                actuallyBalance = balance - regularPayment * 2 ;
            } else {
                actuallyBalance = balance - regularPayment;
            }

            userBalanceStorageService.updateBalance(chatId, actuallyBalance);

            String subMessage = String.format("\n\nЗа поточний місяць плата за сервіс: %s грн,\n" +
                    "Твій поточний баланс: %s грн\n", regularPayment, actuallyBalance);

            telegramService.sendMessage(chatId, Constants.REGULAR_MESSAGE + subMessage);
        }

    }

}
