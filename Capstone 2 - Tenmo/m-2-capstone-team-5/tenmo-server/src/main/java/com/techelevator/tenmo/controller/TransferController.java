package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, UserDao userDao, AccountDao accountDao){
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.accountDao = accountDao;

    }

    @RequestMapping (path = "/accounts/transfers", method = RequestMethod.POST)
    public int transferAmount(@RequestBody TransferDto transferDto, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName()); //current user
        Transfer transfer = new Transfer();
        Account account = accountDao.getAccountByUserId(user.getId());
        BigDecimal amount = transferDto.getAmount(); //amount
        int transferId = 0; //counter
        if (account.getBalance().compareTo(amount) > 0) { //if amount > balance --> no thanks
            transfer.setAccountFrom(account.getAccountId()); //sets current user = account_from
            transfer.setAmount(amount);
            transfer.setAccountTo(transferDto.getAccountTo());
            transferId = transferDao.transfer(account, transfer);
            //transfer method: needs object & balance
        }
        return transferId;
    }
    @RequestMapping(path = "/accounts/transfers", method = RequestMethod.GET)
    public List<Integer> getTransfers(Principal principal){
        List <Integer> transferList;
        User user = userDao.getUserByUsername(principal.getName());
        Account account = accountDao.getAccountByUserId(user.getId());
        transferList = transferDao.getTransfers(account.getAccountId());
        return transferList;
    }

    @RequestMapping(path = "/accounts/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        Transfer finalResult = transferDao.getTransferById(id);
        return finalResult;
    }


}
