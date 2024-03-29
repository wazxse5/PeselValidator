package com.wazxse5.controller;

import com.wazxse5.AboutWindow;
import com.wazxse5.NumberInfoWindow;
import com.wazxse5.number.*;
import com.wazxse5.number.Number;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Klasa kontrolera FXML głównego okna programu.
 * Program składa się z jednego okna i jednej sceny.
 * Ta klasa odpowiada za obsługę zdarzeń takich jak naciśnięcia przycisków, prezentację wyników czy wybór numeru do sprawdzenia poprawności.
 */
public class MainController {
    private Number number;
    private StringProperty selectedNumberName;       // nazwa zaznaczonego numeru (PESEL, NIP itd.)

    @FXML private ListView<String> numberListView;   // służy do wyboru numeru do walidacji
    @FXML private Label numberTitleLab;              // podpis pola tekstowego do wprowadzania numeru
    @FXML private TextField numberTF;                // pole tekstowe do wprowadzania numeru
    @FXML private CheckBox resultCB;                 // informuje o poprawności numeru po walidacji
    @FXML private Label resultInfoLab;               // wyświetla dodatkowe info możliwe do uzyskania z numeru
    @FXML private Label resultChecksumLab;           // wyświetla informacje o obliczonej sumie kontrolnej

    public MainController() {
        number = new Pesel("");
    }

    /**
     * Inicjalizuje wszystkie kontrolki, ich zawartość i listenery
     */
    public void initialize() {
        // Utworzenie listy z dostępnymi numerami do wyboru
        ObservableList<String> numberNames = FXCollections.observableArrayList("PESEL", "NIP", "REGON", "IBAN");
        // Załadowanie listy numerów do ListView
        numberListView.setItems(numberNames);
        // ustawienie zaznaczenia PESEL na liście
        numberListView.getSelectionModel().select("PESEL");
        // Ustawienie listenera na zmianę zaznaczonego numeru na liście wyboru
        numberListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            String numberText = numberTF.getText();
            switch (selectedNumberName.getValue()) {
                case "PESEL":
                    number = new Pesel(numberText);
                    break;
                case "NIP":
                    number = new Nip(numberText);
                    break;
                case "REGON":
                    number = new Regon(numberText);
                    break;
                case "IBAN":
                    number = new Iban(numberText);
                    break;
            }
            validateNumber();
            formatIban();
        }));


        // Dodanie listenera na zmianę wprowadzonego numeru
        numberTF.textProperty().addListener((observable, oldValue, newValue) -> {
            number.setNumber(newValue);
            validateNumber();
            formatIban();
        });


        selectedNumberName = new SimpleStringProperty();
        // Zbindowanie do aktualnie zaznaczonego numeru w ListView
        selectedNumberName.bind(numberListView.getSelectionModel().selectedItemProperty());
        // Zbindowanie zaznaczonego numeru do Labela który zachęca do wprowadzenia numeru
        numberTitleLab.textProperty().bind(Bindings.concat("Wprowadź numer ", selectedNumberName, ":"));


        // Ustawienie fokusa na pole do wpisywania numeru
        Platform.runLater(() -> numberTF.requestFocus());
    }

/////////////////////////////////////////////////////////////
//    Funkcje główne dotyczące bezpośredniej obsługi tego okna
// ///////////////////////////////////////////////////////////

    /**
     * Sprawdza poprawność wprowadzonego numeru.
     * Jeśli numer jest poprawny to pobiera i wyświetla odpowiednie informacje.
     */
    private void validateNumber() {
        number.validate();

        // ustawienie pól na odpowiednie wartości
        if (number.isCorrect()) {
            resultCB.setSelected(true);
            resultCB.setText("TAK, poprawny");
            resultInfoLab.setText(number.getAdditionalInfo());
        } else {
            resultCB.setSelected(false);
            resultCB.setText("NIE, niepoprawny");
            resultInfoLab.setText("");
            if (numberTF.getText().isEmpty()) resultCB.setText("Nic nie wpisano");
        }
    }

    /**
     * Formatuje zawartość pola do wprowadzania numeru.
     * Wykorzystuje funkcję {@link Iban#formatIban(String)}
     * Ta funkcja jest wykorzystywana w listenerach
     */
    private void formatIban() {
        Platform.runLater(() -> {
            if (selectedNumberName.get().equals("IBAN")) {
                numberTF.setText(Iban.formatIban(numberTF.getText()));
            } else {
                numberTF.setText(numberTF.getText().replaceAll("\\s", ""));
            }
            numberTF.end();
        });
    }

    /**
     * Czyści wszystkie pola tekstowe i CheckBox.
     */
    @FXML public void clearFields() {
        numberTF.setText("");
        resultCB.setSelected(false);
        resultCB.setText("Nie sprawdzono");
        resultInfoLab.setText("");
        resultChecksumLab.setText("");
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
     * Ta metoda pozwala zachować aktualny stan {@link #resultCB} w przypdaku gdyby użytkownik w niego kliknął.
     */
    @FXML public void keepResultCB() {
        resultCB.setSelected(!resultCB.isSelected());
    }


/////////////////////////////////////////////////////////////
//    Funkcje główne dotyczące wyświetlania innych okien
/////////////////////////////////////////////////////////////

    /**
     * Wyświetla okienienko informacyjne z paska menu.
     */
    @FXML public void showAboutWindow() {
        new AboutWindow(this.numberTF.getScene().getWindow());
    }

    @FXML public void showNumberInfoWindow() {
        new NumberInfoWindow(this.selectedNumberName.getValue(), this.numberTF.getScene().getWindow());
    }

/////////////////////////////////////////////////////////////
//   Gettery i settery propertiesów
/////////////////////////////////////////////////////////////

    public void setSelectedNumberName(String selectedNumberName) {
        numberListView.getSelectionModel().select(selectedNumberName);
    }

    public String getSelectedNumberName() {
        return selectedNumberName.getValue();
    }

    public void setNumberText(String numberText) {
        numberTF.setText(numberText);
    }

    public String getNumberText() {
        return numberTF.getText();
    }

    @FXML public void exit() {
        Platform.exit();
    }

}

