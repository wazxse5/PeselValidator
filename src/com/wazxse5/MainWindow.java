package com.wazxse5;

import com.wazxse5.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Główna klasa programu.
 */
public class MainWindow extends Application {
    private Settings programSettings;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Załadowanie pliku fxml, czyli widoku okna
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("fxml/MainScreen.fxml"));
        Scene scene = new Scene(loader.load());
        MainController controller = loader.getController();

        // Załadowanie ustawień programu
        programSettings = new Settings(primaryStage, controller);
        programSettings.load();
        programSettings.apply();

        // Ustawienie i wyświetlenie okna programu
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(616.0);
        primaryStage.setMinHeight(464.0);
        primaryStage.setTitle("Pesel Validator");
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("icon.png")));
        primaryStage.show();
    }

    @Override
    public void stop() {
        programSettings.save();
    }
}
