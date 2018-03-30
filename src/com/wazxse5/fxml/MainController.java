package com.wazxse5.fxml;

import com.wazxse5.number.Nip;
import com.wazxse5.number.Number;
import com.wazxse5.number.Pesel;
import com.wazxse5.number.Regon;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private StringProperty numberText;                     // tekst z pola tekstowego do wprowadzania numeru
    private StringProperty selectedNumberName;             // nazwa zaznaczonego numeru (PESEL, NIP itd.)
    private BooleanProperty quickValidationEnabled;        // czy jest włączone szybkie sprawdzanie

    @FXML private ListView<String> numberListView;         // służy do wyboru numeru do walidacji
    @FXML private Label numberFieldLabel;                  // podpis pola tekstowego do wprowadzania numeru
    @FXML private TextField numberField;                   // pole tekstowe do wprowadzania numeru
    @FXML private CheckBox validateCheckBox;               // informuje o poprawności numeru po walidacji
    @FXML private TextField infoTextField;                 // wyświetla dodatkowe info możliwe do uzyskania z numeru
    @FXML private TextField checksumTextField;             // wyświetla informacje o obliczonej sumie kontrolnej
    @FXML private CheckMenuItem quickValidationCheckItem;  // ustawia czy ma być włączone szybkie sprawdzanie

    public void initialize() {
        // Utworzenie listy z dostępnymi numerami do wyboru
        ObservableList<String> numberNames = FXCollections.observableArrayList("PESEL", "NIP", "REGON");
        // Załadowanie listy numerów do ListView
        numberListView.setItems(numberNames);
        // ustawienie zaznaczenia PESEL na liście
        numberListView.getSelectionModel().select("PESEL");


        // Property które zapamiętuje aktualnie wprowadzony numer
        numberText = new SimpleStringProperty();
        numberText.bindBidirectional(numberField.textProperty());
        // Dodanie listenera na zmianę wprowadzonego numeru
        numberText.addListener(observable1 -> textOrSelectionChanged());


        // Property które zapamiętuje zaznaczony aktualnie numer
        selectedNumberName = new SimpleStringProperty();
        // Zbindowanie do aktualnie zaznaczonego numeru w ListView
        selectedNumberName.bind(numberListView.getSelectionModel().selectedItemProperty());
        // Zbindowanie zaznaczonego numeru do Labala który zachęca do wprowadzenia numeru
        numberFieldLabel.textProperty().bind(Bindings.concat("Wprowadź numer ", selectedNumberName, ":"));
        // Ustawienie listenera na zmianę numeru
        selectedNumberName.addListener(observable -> textOrSelectionChanged());


        // Property które zapamiętuje czy jest włączone szybkie sprawdzanie
        quickValidationEnabled = new SimpleBooleanProperty();
        // Zbindowanie do CheckMenuItema
        quickValidationEnabled.bindBidirectional(quickValidationCheckItem.selectedProperty());


        // Ustawianie fokusa na pole wprowadzania numeru
        Platform.runLater(() -> numberField.requestFocus());
    }

    /**
     * Sprawdza poprawność wprowadzonego numeru.
     * Tworzy odpowiedni obiekt numeru, a następnie wywołuje na nim metodę {@link Number#validate()}.
     * Jeśli numer jest poprawny to pobiera i wyświetla odpowiednie informacje.
     */
    @FXML public void validateNumber() {
        // wybór odpowiedniego rodzaju numeru
        switch (selectedNumberName.getValue()) {
            case "PESEL":
                number = new Pesel(numberText.get());
                break;
            case "NIP":
                number = new Nip(numberText.get());
                break;
            case "REGON":
                number = new Regon(numberText.get());
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
     * Dzięki tej metodzie możliwe jest wywołanie metody {@link #validateNumber()} za pomocą klawisza ENTER.
     * Natomiast jeśli zostanie naciśnięty klawisz ESCAPE to zostanie wywołana metoda {@link #clearFields()}
     *
     * @param event umożliwia określenie który klawisz został naciśnięty
     */
    @FXML public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clearFields();
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
    @FXML public void clearFields() {
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

    public void setSelectedNumberName(String selectedNumberName) {
        numberListView.getSelectionModel().select(selectedNumberName);
    }

    public StringProperty selectedNumberNameProperty() {
        return selectedNumberName;
    }

    public StringProperty numberTextProperty() {
        return numberText;
    }

    public BooleanProperty quickValidationEnabledProperty() {
        return quickValidationEnabled;
    }

    private void textOrSelectionChanged() {
        if (numberText.getValue().equals("")) {
            validateCheckBox.setSelected(false);
            validateCheckBox.setText("Nie sprawdzono");
        } else if (quickValidationEnabled.getValue()) this.validateNumber();
        numberListView.getSelectionModel().select(selectedNumberName.getValue());
    }
}
