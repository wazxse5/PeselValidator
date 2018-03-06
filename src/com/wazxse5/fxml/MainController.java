package com.wazxse5.fxml;

import com.wazxse5.number.Nip;
import com.wazxse5.number.Number;
import com.wazxse5.number.Pesel;
import com.wazxse5.number.Regon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa kontrolera FXML głównego okna programu.
 * Program składa się z jednego okna i jednej sceny.
 * Ta klasa odpowiada za obsługę zdarzeń takich jak naciśnięcia przycisków, prezentację wyników czy wybór numeru do sprawdzenia poprawności.
 */
public class MainController {
    private Number number;
    private String selectedNumberName;

    @FXML private ListView<String> numberListView;              // służy do wyboru numeru do walidacji
    @FXML private Label enterNumberLabel;                       // podpis pola tekstowego do wprowadzania numeru
    @FXML private TextField numberField;                        // pole tekstowe do wprowadzania numeru
    @FXML private CheckBox validateCheckBox;                    // informuje o poprawności numeru po walidacji
    @FXML private TextField infoTextField;                      // wyświetla dodatkowe informacje możliwe do uzyskania z numeru
    @FXML private TextField checksumTextField;                  // wyświetla informacje o obliczonej sumie kontrolnej
    @FXML private CheckMenuItem quickValidationCheckItem;       // ustawia czy ma być włączone szybkie sprawdzanie

    public void initialize() {
        // utworzenie listy z dostępnymi numerami do wyboru
        ObservableList<String> numberNames = FXCollections.observableArrayList("PESEL", "NIP", "REGON");
        numberListView.setItems(numberNames);

        // ustawienie listenera do listy numerów
        numberListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedNumberName = newValue;
            enterNumberLabel.setText("Wprowadź numer " + selectedNumberName + ":");
            if (quickValidationCheckItem.isSelected()) validateNumber();
        });

        // ustawienie zaznaczenia pierwszego numeru na liście
        numberListView.getSelectionModel().selectFirst();

        // dodanie listenera na zmianę wprowadzonego numeru
        numberField.textProperty().addListener((observable) -> {
            if (quickValidationCheckItem.isSelected()) this.validateNumber();
        });

        // ustawianie fokusa na pole wprowadzania numeru
        Platform.runLater(() -> numberField.requestFocus());
    }

    /**
     * Sprawdza poprawność wprowadzonego numeru.
     * Tworzy odpowiedni obiekt numeru, a następnie wywołuje na nim metodę {@link Number#validate()}.
     * Jeśli numer jest poprawny to pobiera i wyświetla odpowiednie informacje.
     */
    @FXML public void validateNumber() {
        // wybór odpowiedniego rodzaju numeru
        switch (selectedNumberName) {
            case "PESEL":
                number = new Pesel(numberField.getText().trim());
                break;
            case "NIP":
                number = new Nip(numberField.getText().trim());
                break;
            case "REGON":
                number = new Regon(numberField.getText().trim());
                break;
        }

        number.validate();

        // ustawienie pól na odpowiednie wartości
        if (number.isCorrect()) {
            validateCheckBox.setSelected(true);
            validateCheckBox.setText("TAK, poprawny");
            infoTextField.setText(number.getAdditionalInfo());
        } else {
            validateCheckBox.setSelected(false);
            validateCheckBox.setText("NIE, niepoprawny");
            infoTextField.setText("");
        }
    }

    /**
     * Wyświetla okienienko informacyjne z paska menu.
     */
    @FXML public void showAboutWindow() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AboutProgramScreen.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage aboutStage = new Stage();

            AboutProgramController aboutController = loader.getController();
            aboutController.setAboutStage(aboutStage);

            aboutStage.setTitle("O programie");
            aboutStage.setResizable(false);
            aboutStage.initOwner(numberField.getScene().getWindow());
            aboutStage.initModality(Modality.APPLICATION_MODAL);

            aboutStage.setScene(scene);
            aboutStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dzięki tej metodzie możliwe jest wywołanie metody {@link #validateNumber()} za pomocą klawisza ENTER.
     * Natomiast jeśli zostanie naciśnięty klawisz ESCAPE to zostanie wywołana metoda {@link #clear()}
     *
     * @param event umożliwia określenie który klawisz został naciśnięty
     */
    @FXML public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clear();
        if (event.getCode() == KeyCode.ENTER) validateNumber();
    }

    /**
     * Ta metoda pozwala zachować aktualny stan {@link #validateCheckBox} w przypdaku gdyby użytkownik w niego kliknął.
     */
    @FXML public void keepCheckBoxState() {
        if (validateCheckBox.isSelected()) validateCheckBox.setSelected(false);
        else validateCheckBox.setSelected(true);
    }

    /**
     * Czyści wszystkie pola tekstowe i CheckBox.
     */
    @FXML public void clear() {
        numberField.setText("");
        validateCheckBox.setSelected(false);
        validateCheckBox.setText("Nie sprawdzono");
        infoTextField.setText("");
        checksumTextField.setText("");
    }

    /**
     * Kończy działanie programu.
     */
    @FXML public void exit() {
        Platform.exit();
    }

    public int getSelectedNumberIndex() {
        return numberListView.getSelectionModel().getSelectedIndex();
    }

    public void setSelectedNumberIndex(int i) {
        numberListView.getSelectionModel().select(i);
    }

    public String getNumberText() {
        return numberField.getText();
    }

    public void setNumberText(String numberText) {
        this.numberField.setText(numberText);
    }

    public boolean isQuickValidationEnabled() {
        return quickValidationCheckItem.isSelected();
    }

    public void setQuickValidationEnabled(boolean enabled) {
        quickValidationCheckItem.setSelected(enabled);
    }

}
