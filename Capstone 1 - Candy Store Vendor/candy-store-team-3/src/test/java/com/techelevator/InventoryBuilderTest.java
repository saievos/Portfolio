package com.techelevator;

import com.techelevator.inventory.InventoryBuilder;
import com.techelevator.candy.Candy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class InventoryBuilderTest {

    InventoryBuilder inventoryBuilder;
    @Before
    public void setup() {
        inventoryBuilder = new InventoryBuilder("C:\\Users\\Student\\source\\repos\\pairs\\candy-store-team-3\\src\\test\\resources\\InventoryTest.csv");
    }

    @Test
    public void getInventory_Happy_Test() {
        Map<String, Candy> test = inventoryBuilder.getInventory();

        String expected = "C1";
        String actual = test.get("C1").getID();
        Assert.assertEquals(expected, actual);
    }

}
