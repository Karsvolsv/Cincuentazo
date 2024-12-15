package ethan.cincuentazo.model;

import java.util.Random;

public class Card {
    private String name;
    private String suit;
    private int value;

    /**
     * Constructor que crea una carta con un nombre y un palo.
     * El valor de la carta se asigna automáticamente según el nombre de la carta.
     *
     * @param name El nombre de la carta (por ejemplo, "A", "2", "J", etc.).
     * @param suit El palo de la carta (por ejemplo, "Diamantes", "Corazones", etc.).
     */
    public Card(String name, String suit) {
        this.name = name;
        this.suit = suit;
        this.value = assignValue(name);
    }

    /**
     * Obtiene el nombre de la carta como una cadena que combina el nombre y el palo.
     * Este nombre se usa para identificar la imagen asociada con la carta.
     *
     * @return Una cadena con el nombre y el palo de la carta (ej. "ADiamantes").
     */
    public String getImageName() {
        return name + suit;
    }

    /**
     * Asigna el valor de la carta según su nombre.
     * Las cartas numéricas tienen su valor numérico correspondiente.
     * Las cartas "A" pueden tener un valor de 1 o 10, las figuras "J", "Q" y "K" tienen un valor de -10,
     * y la carta "9" tiene un valor de 0.
     *
     * @param name El nombre de la carta (por ejemplo, "A", "J", "2", "9").
     * @return El valor asignado a la carta.
     */
    public int assignValue(String name) {
        Random random = new Random();

        switch (name) {
            case "A":
                // La carta "A" puede sumar 1 o 10 aleatoriamente
                return random.nextBoolean() ? 1 : 10;

            case "J":
            case "Q":
            case "K":
                // Las cartas "J", "Q" y "K" restan 10
                return -10;

            case "9":
                // La carta "9" ni suma ni resta
                return 0;

            default:
                try {
                    // Para las cartas numéricas (del 2 al 8 y 10)
                    int value = Integer.parseInt(name);
                    if (value >= 2 && value <= 8 || value == 10) {
                        return value; // Devuelve el valor numérico de la carta
                    } else {
                        throw new IllegalArgumentException("Nombre de carta fuera de rango: " + name);
                    }
                } catch (NumberFormatException e) {
                    // Manejo de error si el nombre no es un número válido
                    throw new IllegalArgumentException("Nombre de carta inválido: " + name);
                }
        }
    }

    /**
     * Devuelve una representación en formato de cadena de la carta.
     * Muestra el nombre, el palo y el valor de la carta.
     *
     * @return Una cadena representando la carta (por ejemplo, "Carta: A de Diamantes, Valor: 1").
     */
    @Override
    public String toString() {
        return "Carta: " + name + " de " + suit + ", Valor: " + value;
    }

    /**
     * Obtiene el nombre de la carta.
     *
     * @return El nombre de la carta (por ejemplo, "A", "2", "J", etc.).
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el palo de la carta.
     *
     * @return El palo de la carta (por ejemplo, "Diamantes", "Corazones", etc.).
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Obtiene el valor de la carta.
     * El valor depende del nombre de la carta según la lógica definida en el método `assignValue`.
     *
     * @return El valor de la carta.
     */
    public int getValue() {
        return value;
    }
}
