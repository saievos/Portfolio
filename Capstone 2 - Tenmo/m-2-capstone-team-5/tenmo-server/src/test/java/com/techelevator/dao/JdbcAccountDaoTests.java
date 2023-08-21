package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests {

    private static final BigDecimal threeThousand = new BigDecimal("3000.00");

    protected static final Account ACCOUNT_1 = new Account(2052, 1052, threeThousand);
    protected static final Account ACCOUNT_2 = new Account(2053, 1052, threeThousand);
    protected static final Account ACCOUNT_3 = new Account(2054, 1052, threeThousand);

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAccount_By_User_Id() {
        sut.getAccountByUserId(ACCOUNT_1.getUserId());
    }
}
