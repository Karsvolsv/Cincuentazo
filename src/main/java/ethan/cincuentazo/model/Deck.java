package ethan.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private ArrayList<Card> cartas;

    /**
     * Constructor que crea un mazo estándar de 52 cartas.
     * El mazo incluye 4 palos (Corazones, Diamantes, Tréboles, Picas)
     * y 13 rangos (2 al 10, J, Q, K, A).
     */
    public Deck() {
        cartas = new ArrayList<>();
        // Crear un mazo estándar con 52 cartas
        for (String suit : new String[]{"Corazones", "Diamantes", "Treboles", "Picas"}) {
            for (String rank : new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"}) {
                cartas.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Baraja el mazo de cartas de manera aleatoria.
     * Utiliza la función de barajado de la clase `Collections` para mezclar las cartas.
     */
    public void shuffle() {
        Collections.shuffle(cartas);
    }

    /**
     * Saca una carta del mazo.
     * Si el mazo tiene cartas disponibles, se saca la última carta y se devuelve.
     * Si el mazo está vacío, se muestra un mensaje y se devuelve `null`.
     *
     * @return La carta que se ha sacado del mazo o `null` si el mazo está vacío.
     */
    public Card drawCard() {
        if (cartas.size() > 0) {
            return cartas.remove(cartas.size() - 1); // Saca la última carta del mazo
        }
        System.out.println("El mazo está vacío.");
        return null; // Si no hay cartas, retorna null
    }

    /**
     * Verifica si el mazo tiene cartas disponibles.
     *
     * @return `true` si el mazo tiene cartas, `false` si está vacío.
     */
    public boolean hasCards() {
        return !cartas.isEmpty();
    }

    /**
     * Agrega una lista de cartas al mazo.
     *
     * @param cards Las cartas que se van a agregar al mazo.
     */
    public void addCards(List<Card> cards) {
        cartas.addAll(cards);
    }

    /**
     * Vacía el mazo eliminando todas las cartas.
     * Este método elimina todas las cartas contenidas en el mazo.
     */
    public void clearDeck() {
        cartas.clear();  // Elimina todas las cartas del mazo
    }

    /**
     * Obtiene el tamaño actual del mazo, es decir, la cantidad de cartas disponibles.
     *
     * @return El número de cartas en el mazo.
     */
    public int size() {
        return cartas.size();
    }

    /**
     * Muestra el estado actual del mazo, imprimiendo cada carta en consola.
     * Utiliza el método `toString` de la clase `Card` para mostrar la carta de manera legible.
     */
    public void showDeck() {
        for (Card card : cartas) {
            System.out.println(card.toString());
        }
    }
}
