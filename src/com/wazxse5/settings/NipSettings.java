package com.wazxse5.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Ma za zadanie wczytać i przechować informacje o nazwach urzędów skarbowych.
 * Te nazwy są wykorzystywane w numerze NIP, na podstawie pierwszych trzechn cyfr numeru
 * można odczytać jaki urząd wydał numer.
 */
public class NipSettings {
    private Properties taxOfficesNames;

    /**
     * Wczytuje dane urzędów skarbowych z pliku properties w folderze resources.
     * Wystarczy uruchomić raz w ciągu działania programu.
     */
    public NipSettings() {
        this.taxOfficesNames = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = loader.getResourceAsStream("NipTaxOffices.properties");
            taxOfficesNames.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            taxOfficesNames = null;
        }
    }

    /**
     * Zwraca nazwę urzędu podatkowego na podstawie podanego numeru w postaci String
     *
     * @param officeCode numer urzędu podatkowego, dla uproszczenia jest on typu String
     * @return nazwa urzędu podatkowego
     */
    public String getTaxOfficeName(String officeCode) {
        if (taxOfficesNames != null && officeCode.length() == 3) {
            return taxOfficesNames.getProperty(officeCode);
        } else {
            return "Nie można wczytać informacji o urzędach podatkowych";
        }
    }
}
