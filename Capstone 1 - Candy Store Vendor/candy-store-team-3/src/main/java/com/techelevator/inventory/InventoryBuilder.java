
package com.techelevator.inventory;

import com.techelevator.candy.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class InventoryBuilder {
    // Hmmm.... looks like a great place to create some form or method called readFile....
    //might migrate stuff from this class to a new if it gets bloated

    private String filePath;

    public InventoryBuilder(String inventoryFilePath){
        //inventoryFilePath gets called in run()
        this.filePath = inventoryFilePath;
    }

    public InventoryBuilder() {
    }

    public Map<String, Candy> getInventory() {

        Map<String,Candy> inventory = new HashMap<>();
        File inventoryCSV = new File(filePath);

        try(Scanner inventoryScanner = new Scanner(inventoryCSV)){
            while(inventoryScanner.hasNextLine()){
                String currentLineFromInventoryScanner = inventoryScanner.nextLine();
                String[] currentLineToArray = currentLineFromInventoryScanner.split("\\|");
                String candyType = currentLineToArray[0];
                String candyID = currentLineToArray[1];
                String candyName = currentLineToArray[2];
                double candyPrice = Double.parseDouble(currentLineToArray[3]);
                String isWrapped = currentLineToArray[4];


                Candy currentCandy = null;

                if (candyType.equalsIgnoreCase("CH")) {
                    currentCandy = new CandyChocolates();
                } else if (candyType.equalsIgnoreCase("SR")) {
                    currentCandy = new CandySours();
                } else if (candyType.equalsIgnoreCase("HC")) {
                    currentCandy = new CandyHardCandy();
                } else if (candyType.equalsIgnoreCase("LI")) {
                    currentCandy = new CandyLicorice();
                } else {
                    return null;
                }

                currentCandy.setType(currentLineToArray[0]);
                currentCandy.setID(currentLineToArray[1]);
                currentCandy.setName(currentLineToArray[2]);
                currentCandy.setPrice(Double.parseDouble(currentLineToArray[3]));
                if (currentLineToArray[4].equals("T")) {
                    isWrapped = "true";
                } else {
                    isWrapped = "false";
                }
                currentCandy.setWrapped(Boolean.parseBoolean(isWrapped));
                inventory.put(candyID, currentCandy);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return inventory;
    }
}
