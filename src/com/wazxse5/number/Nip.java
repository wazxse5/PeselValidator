package com.wazxse5.number;

import java.io.InputStream;
import java.util.Properties;

public class Nip extends Number {
    private static final int[] weightTable = {6, 5, 7, 2, 3, 4, 5, 6, 7};
    private static Properties taxOffices;

    // wczytanie pliku z nazwami urzędów skarbowych
    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = loader.getResourceAsStream("NipTaxOffices.properties");
            taxOffices = new Properties();
            taxOffices.load(inputStream);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            taxOffices = null;
        }
    }

    /**
     * Podstawowy konstruktor
     *
     * @param number numer Nip w postaci <code>String</code>
     */
    public Nip(String number) {
        super(number);
    }

    /**
     * Sprawdza poprawność numeru.
     */
    @Override
    public void validate() {
        boolean nipGood = true;
        int[] tab = new int[10];
        int checksum = 0;

        if (super.getNumber() == null) nipGood = false;
        if (super.getNumber().length() != 10) nipGood = false;

        if (nipGood) {
            for (int i = 0; i < 10; i++) {
                tab[i] = Character.getNumericValue(super.getNumber().charAt(i));
            }

            for (int i = 0; i < 9; i++) checksum += tab[i] * weightTable[i];
            checksum = checksum % 11;
            super.setCorrect(checksum == tab[9]);
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
        if (taxOffices != null) {
            String officeCode = super.getNumber().substring(0, 3);
            return taxOffices.getProperty(officeCode);
        } else return "brak informacji";
    }

}
