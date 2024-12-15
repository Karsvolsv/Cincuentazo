package ethan.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nombre;
    private List<Card> hand;
    private int puntaje;

    /**
     * Constructor de la clase `Player`, que inicializa el nombre del jugador,
     * su mano como una lista vacía y el puntaje en 0.
     *
     * @param nombre El nombre del jugador.
     */
    public Player(String nombre) {
        this.nombre = nombre;
        this.hand = new ArrayList<>();
        this.puntaje = 0;
    }

    /**
     * Agrega una carta a la mano del jugador.
     *
     * @param card La carta a agregar a la mano del jugador.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Juega una carta seleccionada de la mano del jugador. La carta es removida
     * de la mano y retornada.
     *
     * @param index El índice de la carta que se desea jugar.
     * @return La carta jugada, o `null` si el índice es inválido.
     */
    public Card playCard(int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.remove(index); // Remueve la carta jugada
        }
        return null;
    }

    /**
     * Juega una carta automáticamente (para la computadora). La primera carta
     * de la mano del jugador es jugada y removida.
     *
     * @return La carta jugada, o `null` si la mano está vacía.
     */
    public Card playCard() {
        return hand.isEmpty() ? null : hand.remove(0); // Juega la primera carta de la mano
    }

    /**
     * Obtiene la lista de cartas en la mano del jugador.
     *
     * @return La lista de cartas del jugador.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Limpia la mano del jugador, eliminando todas las cartas.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Obtiene el puntaje actual del jugador.
     *
     * @return El puntaje del jugador.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Restablece el puntaje del jugador a 0.
     */
    public void resetPuntaje() {
        puntaje = 0;
    }

    /**
     * Agrega puntos al puntaje del jugador. El puntaje se incrementa por
     * la cantidad especificada en el parámetro.
     *
     * @param puntos Los puntos a agregar al puntaje del jugador.
     */
    public void agregarPuntaje(int puntos) {
        puntaje += puntos;
    }
}
