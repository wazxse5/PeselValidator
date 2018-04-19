package com.wazxse5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NumberInfoController {
    private String numberName;

    @FXML private Label title;
    @FXML private Label content;


    public void setNumber(String numberName) {
        this.numberName = numberName;
        this.setTitle();
        this.setContent();
    }

    private void setTitle() {
        title.setText(numberName);
    }

    private void setContent() {
        String contentFilePath = this.getClass().getResource("/numberInfo/" + numberName + ".txt").getPath();
        String contentText = readNumberInfoFromFile(contentFilePath);
        content.setText(contentText);
    }

    private String readNumberInfoFromFile(String path) {
        StringBuilder contentTextBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) break;

                contentTextBuilder.append(line);
                contentTextBuilder.append("\n");
            }
        } catch (IOException e) {
            return "Nie można wczytać informacji";
        }
        return contentTextBuilder.toString();
    }
}
