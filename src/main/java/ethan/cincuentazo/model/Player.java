package ethan.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un jugador en el juego.
 * Cada jugador tiene un nombre, una mano de cartas y un puntaje.
 */
public class Player {
    private String nombre;  // Nombre del jugador
    private List<Card> hand;  // Mano del jugador, lista de cartas
    private int puntaje;  // Puntaje acumulado del jugador

    /**
     * Constructor de la clase `Player`, inicializa el nombre del jugador,
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
     * @param card La carta que se agregará a la mano del jugador.
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
        return null;  // Retorna null si el índice no es válido
    }

    /**
     * Juega automáticamente una carta (para la computadora). La primera carta
     * de la mano del jugador es jugada y removida.
     *
     * @return La carta jugada, o `null` si la mano está vacía.
     */
    public Card playCard() {
        if (hand.isEmpty()) {
            return null;  // Si la mano está vacía, no se puede jugar ninguna carta
        }
        return hand.remove(0);  // Juega la primera carta de la mano
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
     * @return El puntaje acumulado del jugador.
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

    /**
     * Devuelve el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve una representación de la mano del jugador en formato legible.
     * Este método facilita la depuración o la visualización de las cartas del jugador.
     *
     * @return Una cadena representando la mano del jugador.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" - Cartas en mano: ");
        for (Card card : hand) {
            sb.append(card.getName()).append(" ");
        }
        return sb.toString();
    }
}
