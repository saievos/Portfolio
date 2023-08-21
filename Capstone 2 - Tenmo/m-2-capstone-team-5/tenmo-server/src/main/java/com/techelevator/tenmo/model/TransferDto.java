package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDto {

    private int accountTo;
    private BigDecimal amount;


    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }




}
