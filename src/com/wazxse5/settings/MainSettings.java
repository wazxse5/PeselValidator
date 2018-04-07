package com.wazxse5.settings;

import com.wazxse5.controller.MainController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Klasa odpowiada za ładowanie, zapisywanie i zarządzanie wszystkimi opcjami i ustawieniami jakie można zapisać w programie
 */
public class MainSettings {
    private Stage primaryStage;
    private MainController mainController;

    private Properties settings;
    private File settingsFile;

    private DoubleProperty stageX;
    private DoubleProperty stageY;
    private DoubleProperty stageWidth;
    private DoubleProperty stageHeight;
    private BooleanProperty stageMaximized;


    public MainSettings(Stage primaryStage, MainController mainController) {
        this.primaryStage = primaryStage;
        this.mainController = mainController;

        // Utworzenie property do odczytywania i tam gdzie się da do zapisywania wartości ustawień
        stageX = new SimpleDoubleProperty();
        stageY = new SimpleDoubleProperty();
        stageWidth = new SimpleDoubleProperty();
        stageHeight = new SimpleDoubleProperty();
        stageMaximized = new SimpleBooleanProperty();


        // Zbindowanie do odpowienich property w primaryStage
        stageX.bind(primaryStage.xProperty());
        stageY.bind(primaryStage.yProperty());
        stageWidth.bind(primaryStage.widthProperty());
        stageHeight.bind(primaryStage.heightProperty());
        stageMaximized.bind(primaryStage.maximizedProperty());


        // Utworzenie ustawień i załadowanie domyślnych wartości
        settings = new Properties();
        settings.put("stageX", "400.0");
        settings.put("stageY", "200.0");
        settings.put("stageWidth", "630.0");
        settings.put("stageHeight", "470.0");
        settings.put("stageMaximized", "false");
        settings.put("numberText", "");
        settings.put("selectedNumberName", "PESEL");
    }

    public void load() {
        // Przejście do katalogu użytkownika
        String userDir = System.getProperty("user.home");
        // Utworzenie osobnego katalogu jeśli go nie ma
        File settingsDir = new File(userDir, ".peselValidator");
        if (!settingsDir.exists()) settingsDir.mkdir();
        // Próba otwarcia pliku ustawień
        settingsFile = new File(settingsDir, "settings.piwo");
        if (settingsFile.exists()) {
            try {
                FileInputStream input = new FileInputStream(settingsFile);
                settings.load(input);
            } catch (java.io.IOException ignored) {
            }
        }

    }

    public void apply() {
        // Załadowanie ustawień do primaryStage i mainController
        primaryStage.setX(Double.parseDouble(settings.getProperty("stageX")));
        primaryStage.setY(Double.parseDouble(settings.getProperty("stageY")));
        primaryStage.setWidth(Double.parseDouble(settings.getProperty("stageWidth")));
        primaryStage.setHeight(Double.parseDouble(settings.getProperty("stageHeight")));
        primaryStage.setMaximized(Boolean.parseBoolean(settings.getProperty("stageMaximized")));

        mainController.setNumberText(settings.getProperty("numberText"));
        mainController.setSelectedNumberName(settings.getProperty("selectedNumberName"));
    }

    public void save() {
        // Włożenie odpowiednich wartości do Properties settings
        settings.put("stageX", Double.toString(stageX.get()));
        settings.put("stageY", Double.toString(stageY.get()));
        settings.put("stageWidth", Double.toString(stageWidth.get()));
        settings.put("stageHeight", Double.toString(stageHeight.get()));
        settings.put("stageMaximized", Boolean.toString(stageMaximized.get()));
        settings.put("numberText", mainController.getNumberText());
        settings.put("selectedNumberName", mainController.getSelectedNumberName());

        // Próba zapisu
        try {
            FileOutputStream output = new FileOutputStream(settingsFile);
            settings.store(output, "Setting");
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.err.println("Nie udało się zapisać ustawień do pliku");
        }
    }
}
