package com.techelevator;

import com.techelevator.vendingmachine.SalesReport;
import com.techelevator.vendingmachine.Sellable;
import com.techelevator.vendingmachine.VMLog;
import com.techelevator.vendingmachine.VendingMachine;
import com.techelevator.view.*;

import java.math.BigDecimal;

public class Application {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_QUIT = "Quit";
	private static final String MAIN_MENU_SALES_REPORT = Menu.HIDDEN_OPTION;
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_QUIT, MAIN_MENU_SALES_REPORT};

	private static final String MENU_OPTION_PURCHASE_1 = "Feed Money";
	private static final String MENU_OPTION_PURCHASE_2 = "Select Product";
	private static final String MENU_OPTION_PURCHASE_3 = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {MENU_OPTION_PURCHASE_1, MENU_OPTION_PURCHASE_2, MENU_OPTION_PURCHASE_3};


	private VendingMachine vendo = new VendingMachine();

	private final BasicUI ui;

	public Application(BasicUI ui) {
		this.ui = ui;
	}

	public static void main(String[] args) {
		BasicUI cli = new MenuDrivenCLI();
		Application application = new Application(cli);
		application.run();
	}

	public void run() {

		vendo.loadInventory();
		boolean finished = false;
		while (!finished) {
			String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);

			if (selection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				ui.output(vendo.getItemInfo());
			} else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {
				handlePurchaseMenu();
			} else if (selection.equals(MAIN_MENU_OPTION_QUIT)) {
				finished = true;
			} else if (selection.equals(MAIN_MENU_SALES_REPORT)) {
				//need a ui.output
				SalesReport.createReport(vendo.getProducts());
			}


		}
	}

	private void handlePurchaseMenu() {

		boolean finished = false;
		while (!finished) {

			String selection = ui.promptForSelection(PURCHASE_MENU_OPTIONS);
			if (selection.equals(MENU_OPTION_PURCHASE_1)) {
				handleDeposit();
			} else if (selection.equals(MENU_OPTION_PURCHASE_2)) {
				if (vendo.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
					ui.output("Please deposit money first.");
				} else {
					handlePurchase();
				}

			} else if (selection.equals(MENU_OPTION_PURCHASE_3)) {
				ui.output(vendo.getChange());
				finished = true;
			}
		}

	}

	public void handleDeposit() {
		String userInputString = ui.promptForString("Please enter a whole number amount to deposit: ");
		BigDecimal userInput = new BigDecimal(userInputString);
		vendo.deposit(userInput);
		VMLog.logFeedMoney(vendo.getBalance(), userInput);
		ui.output("Your balance is $" + vendo.getBalance());
	}

	public void handlePurchase() {
		String slot = ui.promptForString("Which item would you like to purchase? ");
		Sellable purchasedItem = vendo.purchaseItemInSlot(slot);
		VMLog.logItems(purchasedItem.getName(), purchasedItem.getSlot(), purchasedItem.getPrice(), vendo.getBalance());
		if (purchasedItem != null) {
				ui.output(purchasedItem.getSound() + "\n" + "you have $" + vendo.getBalance() + " left.");
		} else {
			ui.output("Insert more money.");
		}
	}
}

