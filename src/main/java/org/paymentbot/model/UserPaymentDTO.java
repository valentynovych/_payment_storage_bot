package org.paymentbot.model;


import java.util.List;


public class UserPaymentDTO{

    private String userFirstName;
    private List<PaymentStorage> paymentStorageList;

    public UserPaymentDTO(String userFirstName, List<PaymentStorage> paymentStorageList){
        this.userFirstName = userFirstName;
        this.paymentStorageList = paymentStorageList;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public List<PaymentStorage> getPaymentStorageList() {
        return paymentStorageList;
    }

    public void setPaymentStorageList(List<PaymentStorage> paymentStorageList) {
        this.paymentStorageList = paymentStorageList;
    }

    @Override
    public String toString() {
        return "UserPaymentDTO{" +
                "userFirstName='" + userFirstName + '\'' +
                ", paymentStorageList=" + paymentStorageList +
                '}';
    }
}