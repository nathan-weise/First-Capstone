package com.techelevator.vendingmachine;

import java.math.BigDecimal;

public class VendingMachineItem implements Sellable {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal("0.00");

    private String slot;
    private String name;
    private String sound;
    private BigDecimal price;
    private static final int DEFAULT_INVENTORY = 5;
    private int inventory;

    public VendingMachineItem( String name, String sound) {
        this.name = name;
        this.sound = sound;
        this.price =	DEFAULT_PRICE;
        this.inventory = DEFAULT_INVENTORY;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSound() {
        return sound;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
    @Override
    public String getSlot() {
        return slot;
    }
    @Override
    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public void buyOne() {
        this.inventory--;
    }

}
