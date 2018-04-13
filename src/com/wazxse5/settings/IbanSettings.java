package com.wazxse5.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Ma za zadanie wczytać i przechować informacje o dłogości numerów Iban.
 * Każdy kraj ma własną stałą długość numeru Iban.
 */
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

}
