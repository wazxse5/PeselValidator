package com.wazxse5.number;

import com.wazxse5.Sex;

import java.time.LocalDate;

/**
 * Klasa <code>Pesel</code> reprezentuje numer PESEL.
 * Numer PESEL jest to numer ewidencyjny osoby fizycznej wykorzystywany w polskim Powszechnym Elektronicznym Systemiw Ewidencji Ludności
 */
public class Pesel extends Number {

    /**
     * Data urodzenia
     */
    private LocalDate birthDate;
    /**
     * Wiek, liczba lat
     */
    private int age;
    /**
     * Płeć
     */
    private Sex sex;

    /**
     * Przyjmuje numer pesel w postaci <code>String</code>
     *
     * @param number numer Pesel
     */
    public Pesel(String number) {
        super(number);
        this.validated = false;
        this.correct = false;
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
        int checksum;
        int birthDay, birthMonth, birthYear;

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

            checksum = tab[0] + 3 * tab[1] + 7 * tab[2] + 9 * tab[3] + tab[4] + 3 * tab[5] + 7 * tab[6] + 9 * tab[7] + tab[8] + 3 * tab[9];
            checksum = checksum % 10;
            if (checksum != 0) checksum = 10 - checksum;
            this.correct = checksum == tab[10];


            birthDay = 10 * tab[4] + tab[5];
            birthYear = 10 * tab[0] + tab[1];
            birthMonth = 10 * tab[2] + tab[3];
            if (birthDay > 31) this.correct = false;

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
            } else this.correct = false;


            this.birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
            this.age = LocalDate.now().getYear() - this.birthDate.getYear();

            if (tab[9] % 2 == 0) this.sex = Sex.FEMALE;
            if (tab[9] % 2 == 1) this.sex = Sex.MALE;
        }

        this.validated = true;
    }

    /**
     * Zwraca informacje które można wywnioskować na podstawie numeru.
     *
     * @return informacje w postaci String
     */
    @Override
    public String getSimpleInfo() {
        StringBuilder infoBuilder = new StringBuilder();
        if (sex == Sex.MALE) infoBuilder.append("Mężczyzna,   ur. ");
        else if (sex == Sex.FEMALE) infoBuilder.append("Kobieta, ur. ");
        infoBuilder.append(birthDate.toString());
        infoBuilder.append(",   (").append(age).append(" lat)");
        return infoBuilder.toString();
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

