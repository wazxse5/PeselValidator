package com.wazxse5;

import com.wazxse5.fxml.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Główna klasa programu.
 */
public class Main extends Application {
    private Settings programSettings;

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("fxml/MainScreen.fxml"));
        Scene scene = new Scene(loader.load());
        MainController controller = loader.getController();

        programSettings = new Settings();
        programSettings.setMainController(controller);
        programSettings.setPrimaryStage(primaryStage);
        programSettings.apply();

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
