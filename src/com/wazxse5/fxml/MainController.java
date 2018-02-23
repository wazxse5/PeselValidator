package com.wazxse5.fxml;

import com.wazxse5.number.Nip;
import com.wazxse5.number.Number;
import com.wazxse5.number.Pesel;
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
    private String currentNumberName;

    @FXML private ListView<String> numberListView;  // służy do wyboru numeru do walidacji
    @FXML private Label enterNumberLabel;           // podpis pola tekstowego do wprowadzania numeru
    @FXML private TextField numberField;            // pole tekstowe do wprowadzania numeru
    @FXML private CheckBox validateCheckBox;        // informuje o poprawności numeru po walidacji
    @FXML private TextField infoTextField;          // wyświetla dodatkowe informacje możliwe do uzyskania z numeru
    @FXML private TextField checksumTextField;      // wyświetla informacje o obliczonej sumie kontrolnej

    public void initialize() {
        ObservableList<String> numberNames = FXCollections.observableArrayList("PESEL", "NIP");
        numberListView.setItems(numberNames);

        numberListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentNumberName = newValue;
            enterNumberLabel.setText("Wprowadź numer " + currentNumberName + ":");
        });

        numberListView.getSelectionModel().selectFirst();
    }

    /**
     * Sprawdza poprawność wprowadzonego numeru.
     * Tworzy odpowiedni obiekt numeru, a następnie wywołuje na nim metodę {@link Number#validate()}.
     * Jeśli numer jest poprawny to pobiera i wyświetla odpowiednie informacje.
     */
    @FXML
    public void validateNumber() {
        if (currentNumberName.equals("PESEL")) number = new Pesel(numberField.getText().trim());
        else if (currentNumberName.equals("NIP")) number = new Nip(numberField.getText().trim());
        number.validate();

        if (number.isCorrect()) {
            validateCheckBox.setSelected(true);
            validateCheckBox.setText("TAK, poprawny");
            infoTextField.setText(number.getSimpleInfo());
        } else {
            validateCheckBox.setSelected(false);
            validateCheckBox.setText("NIE, niepoprawny");
            infoTextField.setText("");
        }
    }

    /**
     * Czyści wszystkie pola tekstowe i CheckBox.
     */
    @FXML
    public void clear() {
        numberField.setText("");
        validateCheckBox.setSelected(false);
        validateCheckBox.setText("Nie sprawdzono");
        infoTextField.setText("");
        checksumTextField.setText("");
    }

    /**
     * Kończy działanie programu.
     */
    @FXML
    public void exit() {
        System.exit(0);
    }

    /**
     * Dzięki tej metodzie możliwe jest w całym kontenerze wywołanie metody {@link #validateNumber()} za pomocą klawisza Enter.
     * Natomiast jeśli zostanie naciśnięty klawisz ESCAPE to zostanie wywołana metoda {@link #clear()}
     *
     * @param event umożliwia określenie który klawisz został naciśnięty
     */
    @FXML
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) clear();
        if (event.getCode() == KeyCode.ENTER) validateNumber();
    }

    /**
     * Przesłania działanie metody {@link #keyPressed(KeyEvent)} na przycisku Wyczyść.
     * Jeśli na aktywnym przycisku Wyczyść zostanie naciśnięty klawisz ENTER to zostanie wywołana metoda {@link #clear()} i wszystie pola zostaną wyczyszczone.
     * Gdyby nie ta metoda to {@link #keyPressed(KeyEvent)} powodowałaby w takim przypadku walidację numeru.
     *
     * @param event umożliwia określenie który klawisz został naciśnięty
     */
    @FXML
    public void keyPressedClearButton(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) clear();
    }

    /**
     * Ta metoda pozwala zachować aktualny stan {@link #validateCheckBox} w przypdaku gdyby użytkownik w niego kliknął.
     */
    @FXML
    public void keepCheckBoxState() {
        if (validateCheckBox.isSelected()) validateCheckBox.setSelected(false);
        else validateCheckBox.setSelected(true);
    }
}
