package com.wazxse5.number;

import com.wazxse5.settings.NipSettings;

public class Nip extends Number {
    private static final int[] weightTable = {6, 5, 7, 2, 3, 4, 5, 6, 7};
    private static NipSettings taxOffices = new NipSettings();

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
        int[] tab = new int[10];
        int checksum = 0;

        if (isGood()) {
            // Wczytanie numeru jako tablica int
            for (int i = 0; i < 10; i++) {
                tab[i] = Character.getNumericValue(super.getNumber().charAt(i));
            }
            // Sprawdzenie warunków poprawności
            for (int i = 0; i < 9; i++) checksum += tab[i] * weightTable[i];
            checksum = checksum % 11;
            super.setCorrect(checksum == tab[9]);
        }
        super.setValidated();
    }

    /**
     * Sprawdza czy numer ma poprawną strukturę: długość, odpowiednie znaki itp
     *
     * @return true jeśli ma poprawną strukturę
     */
    @Override public boolean isGood() {
        boolean nipGood = true;
        if (super.getNumber() == null) nipGood = false;
        else if (super.getNumber().length() != 10) nipGood = false;
        return nipGood;
    }

    /**
     * Zwraca informacje które można wywnioskować na podstawie numeru.
     *
     * @return informacje w postaci String
     */
    @Override
    public String getAdditionalInfo() {
        String officeCode = super.getNumber().substring(0, 3);
        return taxOffices.getTaxOfficeName(officeCode);
    }

}
