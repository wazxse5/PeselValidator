package com.wazxse5.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IbanSettings {
    private Properties countryIbanLength;

    public IbanSettings() {
        this.countryIbanLength = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = loader.getResourceAsStream("IbanLength.properties");
            countryIbanLength.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            countryIbanLength = null;
        }
    }

    /**
     * Zwraca długość numeru IBAN jaka obowiązuje w danym kraju na podstawie podanego kodu zgodnego ze standardem
     * ISO 3166-1 alfa-2.
     *
     * @param countryCode2 dwuznakowy kod kraju
     * @return długość numeru IBAN
     */
    public int getIbanLength(String countryCode2) {
        int length = -1;
        if (countryCode2.length() == 2) {
            String lengthAsString = countryIbanLength.getProperty(countryCode2);
            if (lengthAsString != null) {
                length = Integer.parseInt(lengthAsString);
            }
        }
        return length;
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
