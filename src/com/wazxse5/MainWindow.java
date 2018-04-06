package com.wazxse5;

import com.wazxse5.controller.MainController;
import com.wazxse5.settings.MainSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * Główna klasa programu.
 */
public class MainWindow extends Application {
    private MainSettings mainSettings;

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
        mainSettings = new MainSettings(primaryStage, controller);
        mainSettings.load();
        mainSettings.apply();

        // Ustawienie i wyświetlenie okna programu
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(630.0);
        primaryStage.setMinHeight(470.0);
        primaryStage.setTitle("Pesel Validator");
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("icon.png")));
        primaryStage.show();
    }

    @Override
    public void stop() {
        mainSettings.save();
    }
}
