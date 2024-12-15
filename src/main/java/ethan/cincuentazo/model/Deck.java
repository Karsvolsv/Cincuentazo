package ethan.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el mazo de cartas del juego.
 * Se encarga de la creación, barajado, y distribución de cartas.
 */
public class Deck {
    private ArrayList<Card> cartas;

    // Constructor que crea un mazo estándar de 52 cartas (sin comodines).
    public Deck() {
        cartas = new ArrayList<>();
        // Patron Creacional
        for (String suit : new String[]{"Corazones", "Diamantes", "Treboles", "Picas"}) {
            for (String rank : new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"}) {
                cartas.add(createCard(rank, suit)); // Crear la carta usando el Factory Method
            }
        }
    }

    // Método Factory para crear una carta
    private Card createCard(String rank, String suit) {
        return new Card(rank, suit);
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
     * Si el mazo está vacío, lanza una excepción.
     *
     * @return La carta que se ha sacado del mazo.
     * @throws IllegalStateException Si el mazo está vacío.
     */
    public Card drawCard() {
        if (cartas.isEmpty()) {
            throw new IllegalStateException("El mazo está vacío. No se pueden sacar más cartas.");
        }
        return cartas.remove(cartas.size() - 1); // Saca la última carta del mazo
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

    /**
     * Devuelve el mazo completo de cartas. Puede ser útil para obtener una referencia directa a todas las cartas.
     *
     * @return La lista completa de cartas del mazo.
     */
    public List<Card> getDeck() {
        return Collections.unmodifiableList(cartas); // Devuelve una lista no modificable
    }
}
