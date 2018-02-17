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
        boolean peselCorrect = false;
        int birthDay = 0;
        int birthMonth = 0;
        int birthYear = 0;
        boolean sex = false;


        String pesel = peselField.getText().trim();
        boolean peselFieldGood = true;

        if (pesel.length() != 11) peselFieldGood = false;

        for (int i = 0; i < pesel.length(); i++) {
            char c = pesel.charAt(i);
            if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9') {
                peselFieldGood = false;
            }
        }

        if (peselFieldGood) {
            int[] tab = new int[11];

            for (int i = 0; i < 11; i++) {
                tab[i] = Character.getNumericValue(pesel.charAt(i));
            }

            int checksum = tab[0] + 3 * tab[1] + 7 * tab[2] + 9 * tab[3] + tab[4] + 3 * tab[5] + 7 * tab[6] + 9 * tab[7] + tab[8] + 3 * tab[9];
            checksum = checksum % 10;
            if (checksum != 0) {
                checksum = 10 - checksum;
            }

            peselCorrect = checksum == tab[10];

            birthDay = 10 * tab[4] + tab[5];
            if (birthDay > 31)
                peselCorrect = false; //ze względu na wadę algorytmu ten wers może wyłapać chociaż część błędów

            if (tab[2] == 0 || tab[2] == 1) birthMonth = 10 * tab[2] + tab[3];
            if (tab[2] == 2 || tab[2] == 3) birthMonth = 10 * (tab[2] - 2) + tab[3];
            if (tab[2] == 4 || tab[2] == 5) birthMonth = 10 * (tab[2] - 4) + tab[3];
            if (tab[2] == 6 || tab[2] == 7) birthMonth = 10 * (tab[2] - 6) + tab[3];
            if (tab[2] == 7 || tab[2] == 8) birthMonth = 10 * (tab[2] - 8) + tab[3];

            if (tab[2] == 0 || tab[2] == 1) birthYear = 1900 + 10 * tab[0] + tab[1];
            if (tab[2] == 2 || tab[2] == 3) birthYear = 2000 + 10 * tab[0] + tab[1];
            if (tab[2] == 4 || tab[2] == 5) birthYear = 2100 + 10 * tab[0] + tab[1];
            if (tab[2] == 6 || tab[2] == 7) birthYear = 2200 + 10 * tab[0] + tab[1];
            if (tab[2] == 8 || tab[2] == 9) birthYear = 1800 + 10 * tab[0] + tab[1];

            if (tab[9] % 2 == 0) sex = false;
            if (tab[9] % 2 == 1) sex = true;
        }


        if (peselFieldGood && peselCorrect) {
            validateCheckBox.setSelected(true);
            validateCheckBox.setText("TAK, poprawny");

            //// w zasadzie po to żeby daty były 01 a nie 1 itd.
            String sDay;
            if (birthDay < 10) sDay = "0" + birthDay;
            else sDay = "" + birthDay;
            String sMonth;
            if (birthMonth < 10) sMonth = "0" + birthMonth;
            else sMonth = "" + birthMonth;
            ////

            dateLabel.setText(sDay + "-" + sMonth + "-" + birthYear);

            if (sex) sexLabel.setText("Mężczyzna");
            else sexLabel.setText("Kobieta");

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
