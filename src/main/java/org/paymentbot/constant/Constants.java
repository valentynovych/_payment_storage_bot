package org.paymentbot.constant;

public class Constants {

    public static final String BTN_GO_BACK = "❌ Скасувати";
    public static final String BTN_ENTER_MAIL = "Зареєструвати свій E-Mail";
    public static final String BTN_IN_REGISTER = "Вже зареєстрований";
    public static final String BTN_VIEW_PAYMENT = "Переглянути оплати";
    public static final String BTN_ENTER_PAYMENT = "Внести оплату";
    public static final String COMMAND_START = "/start";
    public static final String COMMAND_CHECK = "/check";
    public static final String COMMAND_SEND_ALL = "/send";

    public static final String REGULAR_MESSAGE = " Привіт \uD83D\uDC4B\n" +
            "Прийшов час знову платити за користування YouTube Premium\n" +
            "Посилання на реквізити моєї карти. (*Перехід у ваш Приват24 з реквізитами моєї карти)\n" +
            "\nhttps://www.privat24.ua/rd/transfer_to_card?hash=rd/transfer_to_card/%7B%22from%22:%22%22,%22to%22:%225169%203600%200699%204747%22,%22ccy%22:%22UAH%22,%22amt%22:%22100%22%7D\n" +
            "\nПісля оплати, будь ласка запиши свою оплату, щоб рахувався правильно твій баланс\n" +
            "\n/start ► \"Вже зареєстрований\" ► \"Внести оплату\"";

}
