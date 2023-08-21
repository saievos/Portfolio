package com.techelevator.cashier;

import java.util.List;
import java.util.Scanner;

import com.techelevator.inventory.InventoryDisplay;
import com.techelevator.inventory.InventoryBuilder;

public class Menu {
    InventoryDisplay inventoryDisplay = new InventoryDisplay();
    SalesManager salesManager = new SalesManager();
    InventoryBuilder inventoryBuilder = new InventoryBuilder();
    List<String> userInventory = inventoryDisplay.rearrangeInventory(inventoryBuilder.getInventory());

    Scanner input = new Scanner(System.in);
    String userInput = input.nextLine();

    public void greeting() {
        System.out.println("Application is running");
        mainMenu:
        while (true) {
            System.out.println("(1) Show Inventory");
            if (userInput.equals("1")) {
                List<String> userInventory = inventoryDisplay.rearrangeInventory(inventoryBuilder.getInventory());

                //make sure to display list in natural order --> treeSet
                System.out.print(userInventory);
                continue mainMenu;
            }
            System.out.println("(2) Make Sale");

            System.out.println("(3) Quit");
            if (userInput.equals("3")) {
                System.out.println("Thank you, have a good day. Get out!");
                return;
            }
            userInput = input.nextLine();

            System.out.println();

            subMenu:
            while (true) {
                System.out.println("(1) Take Money");
                if (userInput.equals("1")) {
                    System.out.println("Please enter your amount in whole numbers up to $100: ");
                    userInput = input.nextLine();
                    salesManager.addMoney(Integer.parseInt(userInput));
                    System.out.println(salesManager.getCurrentBalance());
                } else {
                    System.out.println("Invalid input. Please try again.");
                }

                System.out.println("(2) Select Products");
                if (userInput.equals("2")) {

                    List<String> productDisplay = userInventory;
                    System.out.print(productDisplay);

                    System.out.println("Please enter the candy ID you want to select for purchase: ");
                    userInput = input.nextLine();

                    if (userInventory.contains(userInput)) {
                        System.out.println("How many candies do you want to purchase?");
                        userInput = input.nextLine();
                        int customerNumberCandiesSelected = Integer.parseInt(userInput);
                        int quantityRemaining = Integer.parseInt(productDisplay.get(3));
                        double priceOfCandy = Double.parseDouble(String.valueOf(productDisplay.indexOf(4)));

                        if (customerNumberCandiesSelected <= quantityRemaining) {
                            salesManager.buyItem(priceOfCandy, customerNumberCandiesSelected);
                            int updatedQuantity = quantityRemaining - customerNumberCandiesSelected;
                            String updatedQuantityStr = String.valueOf(updatedQuantity);
                            productDisplay.add(3, updatedQuantityStr);
                            if (quantityRemaining == 0) {
                                productDisplay.add(3, "Out of Stock");
                            }
                        } else {
                            System.out.println("Sorry, insufficient stock for amount requested.");
                            continue;
                        }
                    } else {
                        System.out.println("The product selected does not exist, please try again.");
                        continue;
                    }

                    System.out.println("(3) Complete Sale");
                    if (userInput.equals("3")) {
                        //show receipt - build this method


                        salesManager.changeMaker(salesManager.getCurrentBalance());
                        //fix inside printLine and fix changeMaker method in SalesManager
                        System.out.println();
                    }
                    System.out.println("(4) Return to Main Menu");
                    if (userInput.equals("4")) {
                        continue mainMenu;
                    }
                    System.out.println("$" + salesManager.getCurrentBalance());

                    userInput = input.nextLine();


                    input.close();
                }

            }

        }
    }
}
