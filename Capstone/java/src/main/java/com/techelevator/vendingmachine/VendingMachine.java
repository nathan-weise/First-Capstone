package com.techelevator.vendingmachine;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {

    private List<Sellable> products = new ArrayList<>();
    private static final int DEFAULT_INVENTORY = 5;
    private BigDecimal balance;

    public VendingMachine() {
        balance = BigDecimal.ZERO;
    }

    public List<Sellable> getProducts() {
        return products;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal deposit(BigDecimal amountToDeposit) {
        balance = amountToDeposit.add(getBalance());
        //VMLog.logFeedMoney(this.getBalance(), amountToDeposit);
        return getBalance();
    }

    private Sellable findProductInSlot(String slot) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getSlot().equals(slot)) {
                return products.get(i);
            }
        }
        return null;
    }

    public Sellable purchaseItemInSlot(String slot) {
        Sellable item = findProductInSlot(slot);
        if (item.getInventory() >= 1 && balance.compareTo(item.getPrice()) >= 0) {
            item.buyOne();
            balance = balance.subtract(item.getPrice());
            //VMLog.logItems(item.getName(), item.getSlot(), item.getPrice(), this.getBalance());
            return item;
        } else {
            return null;
        }
    }


    public void loadInventory() {

        List<String> vendingMachineList = null;
        String[] vendingMachineArray = new String[0];


        Path filePath = Paths.get("inventory.txt");
        try (Scanner fileScanner = new Scanner(filePath)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                vendingMachineArray = line.split("\\|");
                String slot = vendingMachineArray[0];
                String name = vendingMachineArray[1];
                BigDecimal price = new BigDecimal(vendingMachineArray[2]);
                String type = vendingMachineArray[3];

                VendingMachineItem product = null;

                if (type.equals("Chip")) {
                    product = new Chips();
                }

                if (type.equals("Candy")) {
                    product = new Candy();
                }

                if (type.equals("Drink")) {
                    product = new Drink();
                }

                if (type.equals("Gum")) { //make as else if
                    product = new Gum();
                }
                product.setPrice(price);
                product.setName(name);
                product.setSlot(slot);
                product.setInventory(DEFAULT_INVENTORY);
                products.add(product);

            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }

    }

    public String getItemInfo() {
        String result = "";
        for (Sellable product : products) {
            if (product.getInventory() == 0) {
                result += product.getSlot() + " SOLD OUT! \n" ;
            } else {
                result += product.getSlot() + " " + product.getName() + " $" + product.getPrice() + "\n";
            }
        } return result;
    }


    public String getChange() {
        BigDecimal changeDue = balance;

        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickle = new BigDecimal("0.05");

        int numberOfQuarters = 0;
        int numberOfDimes = 0;
        int numberOfNickles = 0;

        while (balance.compareTo(BigDecimal.ZERO) != 0) {

            if (balance.compareTo(quarter) >= 0) {
                balance = balance.subtract(quarter);
                numberOfQuarters++;
            } else if (balance.compareTo(dime) >= 0) {
                balance = balance.subtract(dime);
                numberOfDimes++;
            } else if (balance.compareTo(nickle) >= 0) {
                balance = balance.subtract(nickle);
                numberOfNickles++;
            }
        }
        VMLog.logChange(changeDue, balance);
        return "Number of quarters = " + numberOfQuarters + ", Number of dimes = " + numberOfDimes
                + ", Number of nickles = " + numberOfNickles + ". Remaining balance is $" + getBalance() + ".";
    }


}











