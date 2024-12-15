package ethan.cincuentazo.model;

import java.util.Random;

/**
 * Interfaz que define la estrategia para calcular el valor de una carta.
 * El patrón de diseño Strategy permite cambiar la forma en que se calcula
 * el valor de una carta sin modificar la clase `Card`.
 */
interface CardValueStrategy {
    /**
     * Calcula el valor de una carta según su nombre.
     *
     * @param name El nombre de la carta (ej. "A", "2", "J", "9").
     * @return El valor calculado de la carta.
     */
    int calculateValue(String name);
}

/**
 * Implementación por defecto de la estrategia para calcular el valor de una carta.
 * - El "As" tiene un valor aleatorio entre 1 y 10.
 * - Las figuras ("J", "Q", "K") tienen un valor de -10.
 * - El "9" tiene un valor de 0.
 * - Las cartas numéricas entre "2" y "8" tienen su valor numérico, y el "10" también tiene valor 10.
 */
class DefaultCardValueStrategy implements CardValueStrategy {
    /**
     * Calcula el valor de una carta basándose en su nombre.
     *
     * @param name El nombre de la carta (ej. "A", "2", "J", "9").
     * @return El valor calculado de la carta.
     */
    @Override
    public int calculateValue(String name) {
        Random random = new Random();
        switch (name) {
            case "A":
                // El As puede ser 1 o 10 de forma aleatoria
                return random.nextBoolean() ? 1 : 10;
            case "J":
            case "Q":
            case "K":
                return -10;  // Las figuras (J, Q, K) valen -10
            case "9":
                return 0;  // El 9 tiene un valor de 0
            default:
                try {
                    int value = Integer.parseInt(name);
                    // Las cartas numéricas entre 2 y 8 (inclusive) tienen su valor
                    if (value >= 2 && value <= 8 || value == 10) {
                        return value;
                    } else {
                        throw new IllegalArgumentException("Nombre de carta fuera de rango: " + name);
                    }
                } catch (NumberFormatException e) {
                    // Si el nombre no es numérico ni válido, lanzamos una excepción
                    throw new IllegalArgumentException("Nombre de carta inválido: " + name);
                }
        }
    }
}

/**
 * Representa una carta en el juego. Cada carta tiene un nombre, un palo
 * (como "Corazones", "Diamantes") y un valor calculado según su nombre.
 *
 * Además, utiliza el patrón de diseño Strategy para calcular su valor, lo
 * que permite cambiar la forma en que se calcula el valor sin modificar la
 * clase `Card`.
 */
public class Card {
    private String name;  // Nombre de la carta (ej. "A", "2", "J")
    private String suit;  // Palo de la carta (ej. "Diamantes", "Corazones")
    private int value;    // Valor calculado de la carta
    private CardValueStrategy valueStrategy;  // Estrategia para calcular el valor de la carta

    /**
     * Constructor que crea una carta con un nombre y un palo específicos.
     * Se asigna la estrategia por defecto para calcular el valor de la carta.
     *
     * @param name El nombre de la carta (ej. "A", "2", "J").
     * @param suit El palo de la carta (ej. "Diamantes", "Corazones").
     */
    public Card(String name, String suit) {
        this.name = name;
        this.suit = suit;
        this.valueStrategy = new DefaultCardValueStrategy();  // Se usa la estrategia por defecto
        this.value = assignValue(name);  // Asignar valor de la carta al momento de crearla
    }

    /**
     * Asigna el valor de la carta utilizando la estrategia configurada.
     * Este método delega la lógica del cálculo del valor a la estrategia
     * seleccionada.
     *
     * @param name El nombre de la carta (ej. "A", "2", "J").
     * @return El valor calculado de la carta.
     */
    public int assignValue(String name) {
        return valueStrategy.calculateValue(name);  // Utiliza la estrategia para calcular el valor
    }

    /**
     * Devuelve el nombre completo de la carta, compuesto por su nombre y palo,
     * para utilizarlo en la representación visual de la carta.
     *
     * @return El nombre completo de la carta, como una cadena (ej. "A de Diamantes").
     */
    public String getImageName() {
        return name + suit;  // Retorna el nombre completo de la carta para representar su imagen
    }

    /**
     * Devuelve una representación en formato de cadena de la carta.
     * Esta representación incluye el nombre, el palo y el valor de la carta.
     *
     * @return Una cadena representando la carta (ej. "Carta: A de Diamantes, Valor: 1").
     */
    @Override
    public String toString() {
        return "Carta: " + name + " de " + suit + ", Valor: " + value;
    }

    /**
     * Obtiene el nombre de la carta.
     *
     * @return El nombre de la carta (ej. "A", "2", "J").
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el palo de la carta.
     *
     * @return El palo de la carta (ej. "Diamantes", "Corazones").
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Obtiene el valor de la carta, calculado según la estrategia configurada.
     *
     * @return El valor de la carta.
     */
    public int getValue() {
        return value;
    }

    /**
     * Cambia la estrategia de cálculo del valor de la carta.
     *
     * @param valueStrategy La nueva estrategia de cálculo de valor.
     */
    public void setValueStrategy(CardValueStrategy valueStrategy) {
        this.valueStrategy = valueStrategy;
        this.value = assignValue(name);  // Recalcular el valor con la nueva estrategia
    }
}
