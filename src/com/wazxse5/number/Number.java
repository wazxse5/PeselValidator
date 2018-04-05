package com.wazxse5.number;

/**
 * Obiekt <code>Number</code> reprezentuje abstrakcyjny numer.
 * Służy do tworzenia dokładniejszeych klas numerów np. Pesel
 */
public abstract class Number {

    private final String number;    // Wartość numeru konkretnego numeru w postaci String
    private boolean validated;      // czy poprawność numeru była sprawdzana
    private boolean correct;        // czy numer jest poprawny

    /**
     * Podstawowy konstruktor
     *
     * @param number numer w postaci <code>String</code>
     */
    public Number(String number) {
        number = number.trim();
        number = number.replaceAll("\\s+", "");
        number = number.toUpperCase();
        StringBuilder builder = new StringBuilder();
        for (char c : number.toCharArray()) {
            if (Character.isLetterOrDigit(c)) builder.append(c);
        }
        this.number = builder.toString();
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
    public abstract String getAdditionalInfo();

    /**
     * Zwraca numer.
     *
     * @return numer w postaci String
     */
    public final String getNumber() {
        return number;
    }

    /**
     * Ustawia czy numer był sprawdzany.
     * Do wykorzystania w podklasach.
     */
    protected void setValidated() {
        this.validated = true;
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
     * Ustawia poprawność numeru.
     * Do wykorzystania w podklasach.
     *
     * @param correct czy numer jest poprawny
     */
    protected void setCorrect(boolean correct) {
        this.correct = correct;
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
