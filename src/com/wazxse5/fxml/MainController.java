package com.wazxse5.fxml;

import com.wazxse5.number.Number;
import com.wazxse5.number.Pesel;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Klasa kontrolera FXML głównego okna programu.
 * Program składa się z jednego okna i jednej sceny.
 * Ta klasa odpowiada za obsługę zdarzeń takich jak naciśnięcia przycisków, prezentację wyników czy wybór numeru do sprawdzenia poprawności.
 */
public class MainController {

    /**
     * Pole tekstowe służące do wprowadzenia numeru do walidacji.
     */
    @FXML private TextField numberField;
    /**
     * Służy do prezentacji wyników obliczeń poprawności. Jeśli numer jest poprawny to ten <code>CheckBox jest zaznaczony</code>. Jeśli nie to jest odznaczony.
     * Użytkownik nie może modyfikować stanu tego przycisku, ponieważ za utrzymanie jego aktualnego stanu w przypadku kliknięcia odpowiada funkcja {@link #keepCheckBoxState()}.
     */
    @FXML private CheckBox validateCheckBox;
    /**
     * To pole tekstowe służy wyświetlenia dodatkowych informacji jakie można uzyskać z numeru.
     * Tekst w tym polu jest ustawiany przez funkcję {@link Number#getSimpleInfo()}
     */
    @FXML private TextField infoTextField;
    /**
     * To pole tekstowe będzie wyświetlać wyniki obliczeń sumy kontrolnej.
     * Obecnie jeszcze nie działa.
     */
    @FXML private TextField checksumTextField;


    /**
     * Sprawdza poprawność wprowadzonego numeru.
     * Tworzy odpowiedni obiekt numeru, a następnie wywołuje na nim metodę {@link Number#validate()}.
     * Jeśli numer jest poprawny to pobiera i wyświetla odpowiednie informacje.
     */
    @FXML
    public void validateNumber() {
        Pesel pesel = new Pesel(numberField.getText().trim());
        pesel.validate();

        if (pesel.isCorrect()) {
            validateCheckBox.setSelected(true);
            validateCheckBox.setText("TAK, poprawny");

            infoTextField.setText(pesel.getSimpleInfo());
        } else {
            validateCheckBox.setSelected(false);
            validateCheckBox.setText("NIE, niepoprawny");
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
