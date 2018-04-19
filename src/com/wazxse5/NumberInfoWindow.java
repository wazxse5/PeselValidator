package com.wazxse5;

import com.wazxse5.controller.NumberInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class NumberInfoWindow {

    public NumberInfoWindow(String numberName, Window owner) {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/NumberInfoScreen.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage numberInfoStage = new Stage();

            NumberInfoController numberInfoController = loader.getController();
            numberInfoController.setNumber(numberName);

            numberInfoStage.setTitle("Informacje o numerze " + numberName);
            numberInfoStage.setMinWidth(400.0);
            numberInfoStage.setMinHeight(200.0);
            numberInfoStage.initOwner(owner);

            numberInfoStage.setScene(scene);
            numberInfoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
