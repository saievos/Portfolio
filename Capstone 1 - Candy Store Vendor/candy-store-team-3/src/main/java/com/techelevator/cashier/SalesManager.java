package com.techelevator.cashier;

public class SalesManager {

    private double currentBalance;
    private int totalQuantityInStock;
    private String longStr;
    int twentyDollar = 20;
    int twentiesCounter = 0;
    int tenDollar = 10;
    int tensCounter = 0;
    int fiveDollar = 5;
    int fivesCounter = 0;
    int oneDollar = 1;
    int onesCounter = 0;
    double quarter = 0.25;
    int quartersCounter = 0;
    double dime = 0.10;
    int dimesCounter = 0;
    double nickel = 0.05;
    int nickelsCounter = 0;



    public boolean isEnoughInStock(int quantityRequested, int totalQuantityInStock){
        if(quantityRequested > totalQuantityInStock){
            System.out.println("Insufficient stock.");
            return false;
        } else {
            return true;
        }
    }

    public double addMoney(int cashToAccount) {
        if (cashToAccount > 100) {
            System.out.println("Cannot add more than $100 at a time");
            return currentBalance;
        } else if (currentBalance + cashToAccount <= 1000) {
            currentBalance += cashToAccount;
        } else {
            System.out.println("Sorry, current balance cannot exceed $1000.");
        }
        return currentBalance;
    }


    public double buyItem(double itemCost, int quantity) {
        if (itemCost * quantity > currentBalance) {
            System.out.println("Insufficient funds.");
        } else {
            currentBalance -= (itemCost * quantity);
            return currentBalance;
        }
        return currentBalance;
    }

    public String changeMaker(double currentBalance) {
    //BigDecimal
        int twentyDollar = 20;
        int twentiesCounter = 0;
        int tenDollar = 10;
        int tensCounter = 0;
        int fiveDollar = 5;
        int fivesCounter = 0;
        int oneDollar = 1;
        int onesCounter = 0;
        double quarter = 0.25;
        int quartersCounter = 0;
        double dime = 0.10;
        int dimesCounter = 0;
        double nickel = 0.05;
        int nickelsCounter = 0;

        while (currentBalance > 0) {
            if (currentBalance >= twentyDollar) {
                twentiesCounter++;
                currentBalance -= twentyDollar;
            }else if(currentBalance >= tenDollar){
                tensCounter++;
                currentBalance -= tenDollar;
            }else if(currentBalance >= fiveDollar){
                fivesCounter++;
                currentBalance -= fiveDollar;
            }else if(currentBalance >= oneDollar){
                onesCounter++;
                currentBalance -= oneDollar;
            }else if(currentBalance >= quarter){
                quartersCounter++;
                currentBalance -= quarter;
            }else if(currentBalance >= dime){
                dimesCounter++;
                currentBalance -= dime;
            }else {
                nickelsCounter++;
                currentBalance -= nickel;
            }
        }
        return twentiesCounter + " x " + "twenty dollar bills " + tensCounter + " x " + "ten dollar bills " + fivesCounter + " x " + "five dollar bills "
                + onesCounter + " x " + "one dollar bills " + quartersCounter + " x " + "quarters " + dimesCounter + " x " + "dimes " + nickelsCounter + " x " + "nickels";
    }

    public String getLongStr() {
        String longStr = twentiesCounter + " x " + "twenty dollar bills " + tensCounter + " x " + "ten dollar bills " + fivesCounter + " x " + "five dollar bills "
                + onesCounter + " x " + "one dollar bills " + quartersCounter + " x " + "quarters " + dimesCounter + " x " + "dimes " + nickelsCounter + " x " + "nickels";
        return longStr;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }


}




