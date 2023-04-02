package org.paymentbot.model;

import javax.persistence.*;

@Entity(name = "userBalanceTable")
public class UserBalanceStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long balanceId;
    @OneToOne
    @JoinColumn(name = "chatId")
    private UserStorage userStorage;
    private Double userBalance;

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public void setUserStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Double getUserBalance() {
        if (userBalance == null){
            return 0.0;
        }
        return userBalance;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }
}
