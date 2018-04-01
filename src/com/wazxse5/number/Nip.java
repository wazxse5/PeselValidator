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
        String officeCode = super.getNumber().substring(0, 3);
        return taxOffices.getTaxOfficeName(officeCode);
    }

}
