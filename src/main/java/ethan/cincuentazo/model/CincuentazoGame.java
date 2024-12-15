package ethan.cincuentazo.model;

import javafx.application.Platform;

/**
 * Clase que gestiona el juego de Cincuentazo. Controla el mazo de cartas, los jugadores,
 * la mesa de juego, la lógica de las jugadas y el flujo general del juego.
 *
 * El juego consiste en que un jugador humano y una computadora jueguen turnos, seleccionando
 * cartas que sumen a una acumulación en la mesa. El objetivo es que la suma de las cartas en
 * la mesa no supere los 50 puntos.
 */
public class CincuentazoGame {

    private Deck deck;  // El mazo de cartas utilizado en el juego
    private Player player;  // El jugador humano
    private Player computer;  // El jugador de la computadora
    private int tableSum;  // La suma de las cartas actualmente en la mesa
    private Card cardOnTable;  // La carta que está sobre la mesa actualmente

    private Card lastPlayerCard;  // La última carta jugada por el jugador
    private Card lastComputerCard;  // La última carta jugada por la computadora

    /**
     * Constructor de la clase `CincuentazoGame`. Inicializa el mazo de cartas, los jugadores,
     * la suma de la mesa y reparte las cartas a los jugadores.
     */
    public CincuentazoGame() {
        deck = new Deck();
        player = new Player("Player");
        computer = new Player("Computer");
        tableSum = 0;
        dealCards();
    }

    /**
     * Reparte las cartas al jugador y la computadora. Baraja el mazo y reparte 4 cartas a cada uno.
     * Además, coloca una carta inicial en la mesa y establece su valor.
     */
    private void dealCards() {
        deck.shuffle();  // Asegúrate de barajar el mazo antes de repartir
        for (int i = 0; i < 4; i++) {
            player.addCard(deck.drawCard());  // Añadir cartas al jugador
            computer.addCard(deck.drawCard());  // Añadir cartas a la computadora
        }
        // Coloca una carta inicial en la mesa
        Card initialCard = deck.drawCard();
        tableSum = initialCard.getValue();  // Asignar el valor de la carta inicial
        cardOnTable = initialCard;  // Guardar la carta en mesa
    }

    /**
     * Método que maneja un turno del jugador y la computadora. El jugador selecciona una carta
     * y la juega, luego la computadora juega su carta de forma automática.
     *
     * @param selectedPlayerCard El índice de la carta que el jugador selecciona para jugar.
     */
    public void playTurn(int selectedPlayerCard) {
        // El jugador juega su carta seleccionada
        Card playerCard = player.playCard(selectedPlayerCard);
        if (playerCard != null) {
            lastPlayerCard = playerCard;

            // Coloca la carta jugada encima de la carta anterior en la mesa
            cardOnTable = playerCard;

            // Modifica la suma de la mesa con el valor de la carta jugada
            tableSum += playerCard.getValue();

            // Si la carta seleccionada es un 'A', puede sumar 1 o 10, dependiendo de la elección
            if (playerCard.getName().equals("A")) {
                // Lógica para que el jugador elija entre 1 o 10 para el As
                // Actualmente, se suma 10 de forma predeterminada
                tableSum += 10;
            }
        }

        // El jugador roba una nueva carta del mazo para tener siempre 4 cartas en la mano
        if (deck.hasCards()) {
            player.addCard(deck.drawCard());
        }

        // Verifica que la computadora siempre tenga 4 cartas
        if (computer.getHand().size() < 4 && deck.hasCards()) {
            computer.addCard(deck.drawCard());  // La computadora roba una carta si tiene menos de 4
        }

        // Iniciar un hilo para la jugada de la computadora
        new Thread(() -> {
            try {
                // Simulamos un retraso para la jugada de la computadora
                Thread.sleep(1000);  // Esperar 1 segundo (simulando que la computadora "piensa")

                // La computadora juega su carta
                Card computerCard = computer.playCard();
                lastComputerCard = computerCard;
                tableSum += computerCard.getValue();

                // Usamos Platform.runLater() para actualizar la UI desde el hilo principal
                Platform.runLater(() -> {
                    // Actualizamos la interfaz de usuario después de que la computadora haya jugado
                    updateUI();
                });

                // Verificar si la suma supera 50 y terminar el juego
                if (tableSum > 50) {
                    Platform.runLater(() -> showWinner("Nadie"));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();  // Inicia el hilo
    }

    /**
     * Actualiza la interfaz de usuario con los cambios en la mesa y las cartas jugadas.
     * Este método debe ser implementado según la lógica de la interfaz gráfica del juego.
     */
    private void updateUI() {
        // Aquí actualizarías la UI con los cambios en la mesa y las manos de los jugadores
        // Por ejemplo, se puede actualizar con los valores de la suma en la mesa y las últimas cartas jugadas
        System.out.println("Suma en la mesa: " + tableSum);
        System.out.println("Última carta jugador: " + (lastPlayerCard != null ? lastPlayerCard.getName() : "Ninguna"));
        System.out.println("Última carta computadora: " + (lastComputerCard != null ? lastComputerCard.getName() : "Ninguna"));
    }

    /**
     * Muestra el ganador del juego.
     *
     * @param winner El nombre del ganador, puede ser "Player", "Computer" o "Nadie".
     */
    private void showWinner(String winner) {
        System.out.println("El ganador es: " + winner);
    }

    /**
     * Verifica si el juego ha terminado. El juego termina si la suma de las cartas en la mesa
     * supera 50 o si alguno de los jugadores se queda sin cartas.
     *
     * @return `true` si el juego ha terminado, de lo contrario `false`.
     */
    public boolean checkGameOver() {
        // El juego termina si el jugador o la computadora se quedan sin cartas o si la suma de la mesa es mayor a 50
        return player.getHand().isEmpty() || computer.getHand().isEmpty() || tableSum > 50;
    }

    /**
     * Reinicia el juego. Se vacían las manos de los jugadores, se limpia el mazo y se reparte
     * nuevas cartas para comenzar una nueva partida.
     */
    public void resetGame() {
        player.clearHand();
        computer.clearHand();
        deck.clearDeck();
        tableSum = 0;
        dealCards();
    }

    // Métodos getter para obtener el estado del juego

    /**
     * Obtiene el jugador humano.
     *
     * @return El jugador humano.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Obtiene el jugador de la computadora.
     *
     * @return El jugador de la computadora.
     */
    public Player getComputer() {
        return computer;
    }

    /**
     * Obtiene la suma de las cartas en la mesa.
     *
     * @return La suma de las cartas en la mesa.
     */
    public int getTableSum() {
        return tableSum;
    }

    /**
     * Obtiene la última carta jugada por el jugador.
     *
     * @return La última carta jugada por el jugador.
     */
    public Card getLastPlayerCard() {
        return lastPlayerCard;
    }

    /**
     * Obtiene la última carta jugada por la computadora.
     *
     * @return La última carta jugada por la computadora.
     */
    public Card getLastComputerCard() {
        return lastComputerCard;
    }
}
