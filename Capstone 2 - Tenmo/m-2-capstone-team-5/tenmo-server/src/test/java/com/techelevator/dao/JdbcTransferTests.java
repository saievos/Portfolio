package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


public class JdbcTransferTests extends BaseDaoTests {

    private JdbcTransferDao sut;
    private static final BigDecimal AMOUNT_1 = new BigDecimal(100.50);
    private static final BigDecimal AMOUNT_2 = new BigDecimal(0.50);
    private static final BigDecimal AMOUNT_3 = new BigDecimal(-1);


    private static final Transfer TRANSFER_1 = new Transfer(3001, 2, 2,
            2001, 2002, AMOUNT_1);
    private static final Transfer TRANSFER_2 = new Transfer(3007, 2, 2,
            2069, 2420, null);


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void update_transfer_happy_test() {
        sut.updateTransfer(TRANSFER_1);
    }

    @Test
    public void update_account_from_test() {
        Account before = new Account(2010, 2017, AMOUNT_1);
        Transfer transfer = new Transfer(3010, 2, 2, 2010,
                2011, AMOUNT_2);
        sut.updateAccountFrom(AMOUNT_2, before.getAccountId());
    }

    @Test
    public void update_account_to_test() {
        Account before = new Account(2010, 2017, AMOUNT_1);
        Transfer transfer = new Transfer(3010, 2, 2, 2010,
                2011, AMOUNT_2);
        sut.updateAccountFrom(AMOUNT_2, before.getAccountId());
    }

    @Test(expected = DaoException.class)
    public void update_transfer_with_null_value_test() {
        sut.updateTransfer(TRANSFER_2);
    }

}
