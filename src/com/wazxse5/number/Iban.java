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
        if (isGood()) {
            // transformacja numeru, pierwsze cztery znaki idą na koniec numeru
            String transformated = super.getNumber().substring(4) + super.getNumber().substring(0, 4);

            // zamiana wszystkich liter na odpowiadajacee im wartości
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < super.getNumber().length(); i++) {
                String c = getCharValueAsString(transformated.charAt(i));
                builder.append(c);
            }

            // obliczenie reszty z dzielenia przez 97
            BigInteger numberBI = new BigInteger(builder.toString());
            if (numberBI.mod(BigInteger.valueOf(97)).compareTo(BigInteger.valueOf(1)) == 0) {
                super.setCorrect(true);
            }
        }
    }

    /**
     * Sprawdza czy numer ma poprawną strukturę: długość, odpowiednie znaki itp
     * @return true jeśli ma poprawną strukturę
     */
    @Override public boolean isGood() {
        boolean ibanGood = true;
        if (super.getNumber().length() >= 2) {
            String countryCode = super.getNumber().substring(0, 2);
            int ibanLength = ibanCountryLength.getIbanLength(countryCode);
            if (ibanLength == -1) ibanGood = false;
            else if (super.getNumber().length() != ibanLength) ibanGood = false;
        } else ibanGood = false;
        return ibanGood;
    }

    /**
     * Zwraca informacje które można wywnioskować na podstawie numeru.
     *
     * @return informacje w postaci String
     */
    @Override public String getAdditionalInfo() {
        return null;
    }

    /**
     * Przekształca String do postaci numeru konta bankowego.
     * Co cztery znaki dodaje spacje.
     *
     * @param ibanText tekst który ma być sformatowany
     * @return sformatowany tekst
     */
    public static String formatIban(String ibanText) {
        ibanText = ibanText.trim();
        ibanText = ibanText.replaceAll("\\s", "");
        ibanText = ibanText.toUpperCase();

        StringBuilder formattedIban = new StringBuilder();
        for (int i = 0; i < ibanText.length(); i += 4) {
            if (i + 4 < ibanText.length()) {
                formattedIban.append(ibanText, i, i + 4);
                formattedIban.append(" ");
            } else formattedIban.append(ibanText.substring(i));
        }
        return formattedIban.toString().trim();
    }

    /**
     * Zwraca odpowiednią wartość liczbową w postaci String.
     * Dla cyfry zwraca ją samą jako String.
     * Dla litery dla A zwraca 10, dla B zwraca 11, dla C zwraca 12 itd.
     *
     * @param character znak dla którego wartość ma być zwrócona
     * @return dla cyfry reprezentacja String, dla litery A=10, B=11, C=12 itd.
     */
    public static String getCharValueAsString(char character) {
        if (Character.isLetter(character)) {
            int charValue = (int) character;
            charValue -= 55;
            return String.valueOf(charValue);
        } else return String.valueOf(character);
    }
}
