package com.techelevator;

import com.techelevator.inventory.InventoryBuilder;
import com.techelevator.inventory.InventoryDisplay;
import com.techelevator.candy.Candy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class InventoryDisplayTest {

    InventoryBuilder inventoryBuilder;
    InventoryDisplay inventoryDisplay;
    Map<String, Candy> test = new HashMap<>();

    @Before
    public void setup() {
        inventoryDisplay = new InventoryDisplay();
        inventoryBuilder = new InventoryBuilder("C:\\Users\\Student\\source\\repos\\pairs\\candy-store-team-3\\src\\test\\resources\\InventoryTest.csv");
        test = inventoryBuilder.getInventory();
    }

    @Test
    public void rearrangeInventory_Happy_Test() {
        List<String> actual = inventoryDisplay.rearrangeInventory(test);
        List<String> expected = new ArrayList<>();
        expected.add("C1");
        expected.add("Snuckers Bar");
        expected.add("Y");
        expected.add("100");
        expected.add("1.35");
        Assert.assertEquals(expected, actual);
        System.out.println(actual);
    }
}
