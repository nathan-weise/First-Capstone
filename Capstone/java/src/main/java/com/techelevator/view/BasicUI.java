package com.techelevator.view;

public interface BasicUI {

    void output(String content);

    void pauseOutput();

    String promptForSelection(String[] options);

    String promptForString(String prompt);
}
