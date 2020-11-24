package com.techelevator.vendingmachine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class VMLog {

    public static void logActivity(String activityText) {
        try (FileOutputStream stream = new FileOutputStream("audit/file.log", true);
             PrintWriter writer = new PrintWriter(stream)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            writer.println((dtf.format(now)) + activityText);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public static void logFeedMoney(BigDecimal balance, BigDecimal amount) {
            logActivity(" FEED MONEY: $" + amount + " $" + balance);
    }

    public static void logItems(String name, String slot, BigDecimal price, BigDecimal balance) {
        try (FileOutputStream stream = new FileOutputStream("audit/file.log", true);
             PrintWriter writer = new PrintWriter(stream)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            writer.println((dtf.format(now)) + " " + name + " " + slot + " $" + price + " $" + balance);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void logChange(BigDecimal change, BigDecimal balance) {
        try (FileOutputStream stream = new FileOutputStream("audit/file.log", true);
             PrintWriter writer = new PrintWriter(stream)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            writer.println((dtf.format(now)) + " " + "GIVE CHANGE: $" + change + " $" + balance);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
