package com.wazxse5.fxml;

import com.wazxse5.Pesel;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class MainController {

    @FXML private Pane mainPane;
    @FXML private TextField peselField;
    @FXML private CheckBox validateCheckBox;
    @FXML private Label dateLabel;
    @FXML private Label sexLabel;


    @FXML
    public void validatePesel() {

        Pesel pesel = new Pesel(peselField.getText().trim());
        pesel.validate();


        if (pesel.isCorrect()) {
            validateCheckBox.setSelected(true);
            validateCheckBox.setText("TAK, poprawny");


            dateLabel.setText(pesel.getBirthDate().toString());

            if (pesel.getSex() == Pesel.Sex.MALE)   sexLabel.setText("Mężczyzna");
            if (pesel.getSex() == Pesel.Sex.FEMALE) sexLabel.setText("Kobieta");

            mainPane.setStyle("-fx-background-color: #C4FFC4");
        }
        else {
            validateCheckBox.setSelected(false);
            validateCheckBox.setText("NIE, niepoprawny");
            dateLabel.setText("");
            sexLabel.setText("");
            mainPane.setStyle("-fx-background-color: #FFC4C4");
        }
    }

    @FXML
    public void clear() {
        mainPane.setStyle("-fx-background-color: ghostwhite");
        peselField.setText("");
        validateCheckBox.setSelected(false);
        validateCheckBox.setText("");
        dateLabel.setText("");
        sexLabel.setText("");
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clear();
        if (event.getCode() == KeyCode.ENTER) validatePesel();
    }

    @FXML
    public void keyPressedClearButton(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) clear();
    }

    @FXML
    public void keepCheckBoxState() {
        if (validateCheckBox.isSelected()) validateCheckBox.setSelected(false);
        else validateCheckBox.setSelected(true);
    }
}
