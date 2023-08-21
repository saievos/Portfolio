package com.techelevator;

import com.techelevator.cashier.SalesManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SalesManagerTest {

    SalesManager salesManager;

    @Before
    public void setup() {
        salesManager = new SalesManager();
    }

    @Test
    public void addMoney_25_returns_25() {
        double expected = 25;
        double actual = salesManager.addMoney(25);
        Assert.assertEquals(expected, actual, 0.000009);
    }

    @Test
    public void addMoney_150_returns_0() {
        double expected = 0;
        double actual = salesManager.addMoney(150);
        Assert.assertEquals(expected, actual, 0.000009);
    }


    @Test
    public void addMoney_returns_currentBalance_when_request_over_1000() {
        double expected = 950;
        double test1 = salesManager.addMoney(100);
        double test2 = salesManager.addMoney(100);
        double test3 = salesManager.addMoney(100);
        double test4 = salesManager.addMoney(100);
        double test5 = salesManager.addMoney(100);
        double test6 = salesManager.addMoney(100);
        double test7 = salesManager.addMoney(100);
        double test8 = salesManager.addMoney(100);
        double test9 = salesManager.addMoney(100);
        double test10 = salesManager.addMoney(50);
        double actual = salesManager.addMoney(51);
        Assert.assertEquals(expected, actual, 0.0000009);
    }

    @Test
    public void isEnough_returns_false_when_totalQ_less_than_RequestedQ() {
        boolean actual = salesManager.isEnoughInStock(5, 4);
        Assert.assertFalse(actual);
    }


    @Test
    public void buyItem_returns_875_after_125_purchase() {
        salesManager.addMoney(10);
        double actual = salesManager.buyItem(1.25, 1);
        Assert.assertEquals(8.75, actual, 0.000009);
    }

    @Test
    public void buyItem_returns_insufficientFunds() {
        salesManager.addMoney(1);
        double expected = 1;
        double actual = salesManager.buyItem(1.25, 2);
        Assert.assertEquals(expected, actual, 0.000009);
    }

    @Test
    public void changeMaker_Happy_Test(){
        String actual = salesManager.changeMaker(66.40);
        String expected = "3 x twenty dollar bills 0 x ten dollar bills 1 x five dollar bills 1 x one dollar bills 1 x quarters 1 x dimes 1 x nickels";
        Assert.assertEquals(expected, actual);
    }


}
