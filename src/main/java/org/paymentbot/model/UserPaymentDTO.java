package org.paymentbot.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentDTO {

    private Long userChatId;
    private String userFirstName;
    private Double valueUserPayment;
    @Id
    private String dateUserPayment;

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public Long getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(Long userChatId) {
        this.userChatId = userChatId;
    }

    public Double getValueUserPayment() {
        return valueUserPayment;
    }

    public void setValueUserPayment(Double valueUserPayment) {
        this.valueUserPayment = valueUserPayment;
    }

    public String getDateUserPayment() {
        return dateUserPayment;
    }

    public void setDateUserPayment(String dateUserPayment) {
        this.dateUserPayment = dateUserPayment;
    }
}