package com.wazxse5.number;

/**
 * Obiekt <code>Number</code> reprezentuje abstrakcyjny numer.
 * Służy do tworzenia dokładniejszeych klas numerów np. Pesel
 */
public abstract class Number {

    /**
     * Wartość numeru konkretnego numeru w postaci <code>String</code>
     */
    final String number;
    /**
     * Czy poprawność numeru została sprawdzona
     */
    boolean validated;
    /**
     * Czy numer jest poprawny
     */
    boolean correct;

    /**
     * Podstawowy konstruktor
     *
     * @param number numer w postaci <code>String</code>
     */
    public Number(String number) {
        this.number = number;
        this.validated = false;
        this.correct = false;
    }

    /**
     * Sprawdza poprawność numeru.
     */
    public abstract void validate();

    /**
     * Zwraca informacje które można wywnioskować na podstawie numeru.
     *
     * @return informacje w postaci String
     */
    public abstract String getSimpleInfo();

    /**
     * Zwraca numer.
     *
     * @return numer w postaci String
     */
    public final String getNumber() {
        return number;
    }

    /**
     * Zwraca informację o tym czy poprawność numeru jest sprawdzona.
     *
     * @return <code>true</code> jeśli poprawność jest sprawdzona.
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * Zwraca informację o tym czy numer jest poprawny.
     *
     * @return <code>true</code> jeśli numer jest poprawny.
     */
    public final boolean isCorrect() {
        return correct;
    }
}
