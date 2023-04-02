package org.paymentbot.model;


public class UserPaymentsDTO {

    private String firstName;
    private Long chatId;
    private Double value;
    private String  date;

    public UserPaymentsDTO(String firstName, Long chatId, Double value, String date) {
        this.firstName = firstName;
        this.chatId = chatId;
        this.value = value;
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Double getValue() {
        if (value == null){
            return 0.0;
        }
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDate() {
        if (date == null){
            return "--";
        }
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
