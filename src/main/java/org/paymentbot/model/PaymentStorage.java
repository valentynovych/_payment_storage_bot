package org.paymentbot.model;

import javax.persistence.*;


@Entity(name = "usersPaymentTable")
public class PaymentStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPayment;
    @ManyToOne
    @JoinColumn(name = "chatId")
    private UserStorage userStorage;
    private Double value;
    private String  date;

    public Long getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(Long idPayment) {
        this.idPayment = idPayment;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public void setUserStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
