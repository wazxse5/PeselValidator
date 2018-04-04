package com.wazxse5.number;

import com.wazxse5.settings.IbanSettings;

import java.math.BigInteger;

public class Iban extends Number {
    private static IbanSettings ibanCountryLength = new IbanSettings();

    /**
     * Podstawowy konstruktor
     *
     * @param number numer w postaci <code>String</code>
     */
    public Iban(String number) {
        super(number);
    }

    /**
     * Sprawdza poprawność numeru.
     */
    @Override public void validate() {
        boolean ibanGood = true;
        int ibanLength;

        if (super.getNumber().length() >= 2) {
            String countryCode = super.getNumber().substring(0, 2);
            ibanLength = ibanCountryLength.getIbanLength(countryCode);
            if (ibanLength == -1) ibanGood = false;
            else if (super.getNumber().length() != ibanLength) ibanGood = false;
        } else ibanGood = false;

        if (ibanGood) {
            String transformation = super.getNumber().substring(4) + super.getNumber().substring(0, 4);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < super.getNumber().length(); i++) {
                builder.append(IbanSettings.getCharValueAsString(transformation.charAt(i)));
            }
            BigInteger numberBI = new BigInteger(builder.toString());

            if (numberBI.mod(BigInteger.valueOf(97)).compareTo(BigInteger.valueOf(1)) == 0) {
                super.setCorrect(true);
            }
        }
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
