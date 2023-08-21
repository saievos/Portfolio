package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    int transfer(Account account, Transfer transfer);


    List<Integer> getTransfers(int id);

    Transfer getTransferById(int transferId);
}
