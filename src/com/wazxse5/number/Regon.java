package com.wazxse5.number;

public class Regon extends Number {
    private static final int[] weightTable9 = {8, 9, 2, 3, 4, 5, 6, 7};
    private static final int[] weightTable14 = {2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8};


    public Regon(String number) {
        super(number);
    }

    /**
     * Sprawdza poprawność numeru.
     */
    @Override public void validate() {
        boolean regonGood = true;
        int regonLength;
        int[] tab;
        int checksum = 0;

        if (super.getNumber() == null) regonGood = false;
        if (super.getNumber().length() != 9 && super.getNumber().length() != 14) regonGood = false;

        if (regonGood) {
            regonLength = super.getNumber().length();
            tab = new int[regonLength];

            for (int i = 0; i < regonLength; i++) {
                tab[i] = Character.getNumericValue(super.getNumber().charAt(i));
            }

            for (int i = 0; i < regonLength - 1; i++) {
                if (regonLength == 9) checksum += tab[i] * weightTable9[i];
                else checksum += tab[i] * weightTable14[i];
            }
            checksum = checksum % 11;
            if (checksum == 10) checksum = 0;
            super.setCorrect(checksum == tab[regonLength - 1]);
        }
        super.setValidated();
    }

    /**
     * Zwraca informacje które można wywnioskować na podstawie numeru.
     *
     * @return informacje w postaci String
     */
    @Override public String getAdditionalInfo() {
        return null;
    }
}
