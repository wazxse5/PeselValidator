package com.wazxse5;

import java.time.LocalDate;

public class Pesel {

    private String number;
    private boolean validated;
    private boolean correct;
    private LocalDate birthDate;
    private Sex sex;

    public Pesel(String number) {
        this.number = number;
        this.validated = false;
        this.correct = false;
        this.birthDate = null;
        this.sex = Sex.INDETERMINATE;
    }

    public void validate() {
        boolean peselGood = true;
        int[] tab = new int[11];
        int checksum;
        int birthDay, birthMonth = 0, birthYear = 0;

        if (number.length() != 11) peselGood = false;

        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9') {
                peselGood = false;
            }
        }

        if (peselGood) {
            for (int i = 0; i < 11; i++) {
                tab[i] = Character.getNumericValue(number.charAt(i));
            }

            checksum = tab[0] + 3*tab[1] + 7*tab[2] + 9*tab[3] + tab[4] + 3*tab[5] + 7*tab[6] + 9*tab[7] + tab[8] + 3*tab[9];
            checksum = checksum % 10;
            if (checksum != 0) checksum = 10 - checksum;
            this.correct = checksum == tab[10];
            birthDay = 10 * tab[4] + tab[5];
            if (birthDay > 31) this.correct = false;

            if (tab[2] == 0 || tab[2] == 1) birthMonth = 10 * tab[2] + tab[3];
            else if (tab[2] == 2 || tab[2] == 3) birthMonth = 10 * (tab[2] - 2) + tab[3];
            else if (tab[2] == 4 || tab[2] == 5) birthMonth = 10 * (tab[2] - 4) + tab[3];
            else if (tab[2] == 6 || tab[2] == 7) birthMonth = 10 * (tab[2] - 6) + tab[3];
            else if (tab[2] == 7 || tab[2] == 8) birthMonth = 10 * (tab[2] - 8) + tab[3];
            if (tab[2] == 0 || tab[2] == 1) birthYear = 1900 + 10 * tab[0] + tab[1];
            else if (tab[2] == 2 || tab[2] == 3) birthYear = 2000 + 10 * tab[0] + tab[1];
            else if (tab[2] == 4 || tab[2] == 5) birthYear = 2100 + 10 * tab[0] + tab[1];
            else if (tab[2] == 6 || tab[2] == 7) birthYear = 2200 + 10 * tab[0] + tab[1];
            else if (tab[2] == 8 || tab[2] == 9) birthYear = 1800 + 10 * tab[0] + tab[1];
            this.birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

            if (tab[9] % 2 == 0) this.sex = Sex.FEMALE;
            if (tab[9] % 2 == 1) this.sex = Sex.MALE;
        }

        this.validated = true;
    }


    public String getNumber() {
        return number;
    }

    public boolean isValidated() {
        return validated;
    }

    public boolean isCorrect() {
        return correct;
    }

    public Sex getSex() {
        return sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public enum Sex {INDETERMINATE, MALE, FEMALE}
}
