package com.wazxse5.number;

import com.wazxse5.Sex;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Klasa <code>Pesel</code> reprezentuje numer PESEL.
 * Numer PESEL jest to numer ewidencyjny osoby fizycznej wykorzystywany w polskim Powszechnym Elektronicznym Systemiw Ewidencji Ludności
 */
public class Pesel extends Number {
    private static final int[] weightTable = {9, 7, 3, 1, 9, 7, 3, 1, 9, 7};    // tabela wag kolejnych cyfr

    private LocalDate birthDate;    // data urodzenia
    private int age;                // wiek, liczba lat
    private Sex sex;                // płeć

    /**
     * Podstawowy konstruktor
     *
     * @param number numer Pesel w postaci <code>String</code>
     */
    public Pesel(String number) {
        super(number);
        this.birthDate = null;
        this.age = 0;
        this.sex = Sex.INDETERMINATE;
    }

    /**
     * Sprawdza poprawność numeru Pesel
     */
    @Override
    public void validate() {
        boolean peselGood = true;
        int[] tab = new int[11];
        int checksum = 0;
        int birthDay, birthMonth, birthYear;

        if (super.getNumber() == null) peselGood = false;
        if (super.getNumber().length() != 11) peselGood = false;

        if (peselGood) {
            for (int i = 0; i < 11; i++) {
                tab[i] = Character.getNumericValue(super.getNumber().charAt(i));
            }

            for (int i = 0; i < 10; i++) checksum += (tab[i] * weightTable[i]);
            checksum = checksum % 10;
            super.setCorrect(checksum == tab[10]);

            birthDay = 10 * tab[4] + tab[5];
            birthYear = 10 * tab[0] + tab[1];
            birthMonth = 10 * tab[2] + tab[3];
            if (birthDay > 31) super.setCorrect(false);

            if (birthMonth <= 12) birthYear += 1900;
            else if (birthMonth <= 32) {
                birthYear += 2000;
                birthMonth -= 20;
            } else if (birthMonth <= 52) {
                birthYear += 2100;
                birthMonth -= 40;
            } else if (birthMonth <= 72) {
                birthYear += 2200;
                birthMonth -= 60;
            } else if (birthMonth <= 92) {
                birthYear += 1800;
                birthMonth -= 80;
            } else super.setCorrect(false);

            try {   // przydatne np. w przypadku zamiany dni z rokiem czego algorytm może nie wyłapać
                this.birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
                this.age = LocalDate.now().getYear() - this.birthDate.getYear();
                if (tab[9] % 2 == 0) this.sex = Sex.FEMALE;
                if (tab[9] % 2 == 1) this.sex = Sex.MALE;
            } catch (DateTimeException e) {
                super.setCorrect(false);
            }
        }

        super.setValidated();
    }

    /**
     * Zwraca informacje które można wywnioskować na podstawie numeru.
     *
     * @return informacje w postaci String
     */
    @Override
    public String getAdditionalInfo() {
        if (super.isCorrect()) {
            StringBuilder infoBuilder = new StringBuilder();
            if (sex == Sex.MALE) infoBuilder.append("Mężczyzna,   ur. ");
            else if (sex == Sex.FEMALE) infoBuilder.append("Kobieta, ur. ");
            infoBuilder.append(birthDate.toString());
            infoBuilder.append(",   (").append(age).append(" lat)");
            return infoBuilder.toString();
        } else return "";
    }

    /**
     * Zwraca płeć.
     *
     * @return <code>SEX.MALE</code> jeśli mężczyzna
     * <code>SEX.FEMALE</code> jeśli kobieta
     * <code>SEX.INDETERMINATE</code> jeśli nie można określić np. numer jest niepoprawny
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Zwraca datę urodzenia.
     *
     * @return obiekt <code>LocalDate</code> ustawiony na datę urodzenia.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Zwraca wiek.
     * Wiek liczony w latach, obliczony na podstawie daty urodzenia.
     *
     * @return <code>int</code> wiek
     */
    public int getAge() {
        return age;
    }
}

