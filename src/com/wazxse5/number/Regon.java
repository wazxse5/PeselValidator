package com.wazxse5.number;

public class Regon extends Number {
    private static final int[] weightTable9 = {8, 9, 2, 3, 4, 5, 6, 7};


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

            if (regonLength == 9) {
                for (int i = 0; i < 8; i++) checksum += tab[i] * weightTable9[i];
                checksum = checksum % 11;
                if (checksum == 10) checksum = 0;
                super.setCorrect(checksum == tab[8]);
            } else if (regonLength == 14) {

            } else super.setCorrect(false);

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
