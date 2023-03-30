package org.paymentbot.model;

import javax.persistence.*;

@Entity(name = "userBalanceTable")
public class UserBalanceStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long balanceId;
    @OneToOne
    @JoinColumn(name = "chatId")
    private UserStorage userChatId;
    private Double userBalance;

    public UserStorage getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(UserStorage userChatId) {
        this.userChatId = userChatId;
    }

    public Double getUserBalance() {
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
