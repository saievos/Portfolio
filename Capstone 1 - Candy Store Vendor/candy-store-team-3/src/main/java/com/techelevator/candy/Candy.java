package com.techelevator.candy;

public abstract class Candy {

    private String name;
    private double price;
    private boolean isWrapped;
    private String type;
    private String ID;

    public Candy(String name, double price, boolean isWrapped) {
        this.name = name;
        this.price = price;
        this.isWrapped = isWrapped;
    }

    public Candy() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isWrapped() {
        return isWrapped;

    }

    public void setWrapped(boolean wrapped) {
        isWrapped = wrapped;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
