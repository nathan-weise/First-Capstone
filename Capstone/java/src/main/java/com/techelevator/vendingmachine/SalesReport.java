package com.techelevator.vendingmachine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

public class SalesReport {

    public static void createReport(List<Sellable> products) {
        try (FileWriter write = new FileWriter("SaleReport/report", false);
             PrintWriter writer = new PrintWriter(write)) {
            BigDecimal totalSales = new BigDecimal("0");

            for(Sellable product : products) {
                int itemSold = 5 - product.getInventory();
                writer.println(product.getName() + "|" + itemSold);
                totalSales = totalSales.add(BigDecimal.valueOf(itemSold).multiply(product.getPrice()));
            }

            writer.println("\n" + "**TOTAL SALES** $" + totalSales);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
}


}
