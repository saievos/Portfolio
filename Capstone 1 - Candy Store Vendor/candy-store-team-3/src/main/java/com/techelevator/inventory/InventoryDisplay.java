package com.techelevator.inventory;

import com.techelevator.candy.Candy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryDisplay {

    //we want to take inventory and rearrange to look like sample in readME
    // ID Name Wrapper Qty Price

    //2 methods
    //use tab to align in sout

    //first arranges
    //second toString() method


    private int quantityStart = 100;
    public List<String> inventoryList;


    public List<String> rearrangeInventory(Map<String, Candy> inventory) {

        inventoryList = new ArrayList<>();
        try {
            for (Map.Entry<String, Candy> nextEntry : inventory.entrySet()) {
                String key = nextEntry.getKey();
                Candy value = nextEntry.getValue();

                inventoryList.add(key);
                inventoryList.add(value.getName());
                String wrapper;
                if (value.isWrapped()) {
                    wrapper = "Y";
                } else {
                    wrapper = "N";
                }
                inventoryList.add(wrapper);
                inventoryList.add(String.valueOf(quantityStart));
                inventoryList.add(String.valueOf(value.getPrice()));
            }
        } catch(NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return inventoryList;
    }


    //this needs to have a method and method should not be inside Menu
    //call back inventory screen --> menu thing
    //if ID bad, sout and return subMenu
    //if product sold out, sout, and return subMenu
    //if not enough product remains for customer request, sout, and return subMenu
    //if not enough cash, sout, then return subMenu
    //if all good, put in cart, cost from balance taken immediately
    //back to subMenu
    //removing items from cart is out of scope for this release

    public double itemsPurchasedFromCart() {


    return 0;
    }




}
