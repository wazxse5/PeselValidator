package com.wazxse5;

import com.wazxse5.fxml.MainController;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Klasa odpowiada za ładowanie, zapisywanie i zarządzanie wszystkimi opcjami i ustawieniami jakie można zapisać w programie
 */
public class Settings {
    private Stage primaryStage;
    private MainController mainController;

    private Properties settingsProperties;
    private File settingsFile;


    public Settings() {
        settingsProperties = new Properties();
        loadDefault();
    }

    public void loadDefault() {
        String userDir = System.getProperty("user.home");
        File settingsDir = new File(userDir, ".peselValidator");
        if (!settingsDir.exists()) settingsDir.mkdir();
        settingsFile = new File(settingsDir, "settings.piwo");
        if (settingsFile.exists()) load(settingsFile);
        else {
            settingsProperties.put("positionX", "400.0");
            settingsProperties.put("positionY", "200.0");
            settingsProperties.put("sizeX", "616.0");
            settingsProperties.put("sizeY", "464.0");
            settingsProperties.put("selectedNumberIndex", "0");
            settingsProperties.put("numberText", "");
            settingsProperties.put("quickValidationEnabled", "true");
        }
    }

    public void load(File settingsFile) {
        try {
            FileInputStream input = new FileInputStream(settingsFile);
            settingsProperties.load(input);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void apply() {
        primaryStage.setX(Double.parseDouble(settingsProperties.getProperty("positionX")));
        primaryStage.setY(Double.parseDouble(settingsProperties.getProperty("positionY")));
        primaryStage.setWidth(Double.parseDouble(settingsProperties.getProperty("sizeX")));
        primaryStage.setHeight(Double.parseDouble(settingsProperties.getProperty("sizeY")));
        mainController.setQuickValidationEnabled(Boolean.parseBoolean(settingsProperties.getProperty("quickValidationEnabled")));
        mainController.setSelectedNumberIndex(Integer.parseInt(settingsProperties.getProperty("selectedNumberIndex")));
        mainController.setNumberText(settingsProperties.getProperty("numberText"));
    }

    public void save() {
        settingsProperties.put("positionX", String.valueOf(primaryStage.getX()));
        settingsProperties.put("positionY", String.valueOf(primaryStage.getY()));
        settingsProperties.put("sizeX", String.valueOf(primaryStage.getWidth()));
        settingsProperties.put("sizeY", String.valueOf(primaryStage.getHeight()));
        settingsProperties.put("selectedNumberIndex", String.valueOf(mainController.getSelectedNumberIndex()));
        settingsProperties.put("numberText", mainController.getNumberText());
        settingsProperties.put("quickValidationEnabled", String.valueOf(mainController.isQuickValidationEnabled()));

        try {
            FileOutputStream output = new FileOutputStream(settingsFile);
            settingsProperties.store(output, "Setting");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
