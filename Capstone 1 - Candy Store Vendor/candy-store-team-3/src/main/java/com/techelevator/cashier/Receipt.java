package com.techelevator.cashier;

import com.techelevator.inventory.InventoryDisplay;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    SalesManager salesManager = new SalesManager();
    InventoryDisplay inventoryDisplay = new InventoryDisplay();

    public List<String> getReceipt(){
        //receipt show # of each product purchased, product type, product name,
        //cost of each item, total cost for quantity of
        //that item purchased, total amount of sale, change to be
        //given, including denominations
        List <String> receipt = new ArrayList<>();
       // String quantityOfItemPurchased = String.valueOf(salesManager.buyItem(inventoryDisplay.inventoryList.indexOf(4), inventoryDisplay.inventoryList.indexOf(3)));


        return receipt;
    }

}
