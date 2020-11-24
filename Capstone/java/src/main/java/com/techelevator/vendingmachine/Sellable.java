package com.techelevator.vendingmachine;

import java.math.BigDecimal;

public interface Sellable {

    String getName();

    BigDecimal getPrice();

    String getSlot();

    int getInventory();

    void buyOne();

    String getSound();
}