package com.wazxse5;

import com.wazxse5.controller.AboutController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class AboutWindow {

    public AboutWindow(Window owner) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AboutScreen.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage aboutStage = new Stage();

            AboutController aboutController = loader.getController();
            aboutController.setAboutStage(aboutStage);

            aboutStage.setTitle("O programie");
            aboutStage.setResizable(false);
            aboutStage.initOwner(owner);
            aboutStage.initModality(Modality.WINDOW_MODAL);

            aboutStage.setScene(scene);
            aboutStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
