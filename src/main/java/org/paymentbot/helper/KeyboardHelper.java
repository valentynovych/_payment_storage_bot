package org.paymentbot.helper;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static org.paymentbot.constant.Constants.*;

/**
 * Helper class, allows to build keyboards for users
 */
@Component
public class KeyboardHelper {

    public ReplyKeyboardMarkup buildMailMenu() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(BTN_IN_REGISTER);
        row1.add(BTN_GO_BACK);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public ReplyKeyboardMarkup buildMainMenu() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(BTN_ENTER_MAIL);
        keyboardRow.add(BTN_IN_REGISTER);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(keyboardRow))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public ReplyKeyboardMarkup buildMenuPayment() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(BTN_ENTER_PAYMENT);
        row1.add(BTN_VIEW_PAYMENT);
        row2.add(BTN_GO_BACK);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1, row2))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public ReplyKeyboardMarkup buildMenuAfterPayment() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(BTN_VIEW_PAYMENT);
        row1.add(BTN_GO_BACK);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
}
