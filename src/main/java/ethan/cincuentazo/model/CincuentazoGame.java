package ethan.cincuentazo.model;

import javafx.application.Platform;

public class CincuentazoGame {

    private Deck deck;  // El mazo de cartas utilizado en el juego
    private Player player;  // El jugador humano
    private Player computer;  // El jugador de la computadora
    private int tableSum;  // La suma de las cartas actualmente en la mesa
    private Card cardOnTable;

    private Card lastPlayerCard;  // La última carta jugada por el jugador
    private Card lastComputerCard;  // La última carta jugada por la computadora

    public CincuentazoGame() {
        deck = new Deck();
        player = new Player("Player");
        computer = new Player("Computer");
        tableSum = 0;
        dealCards();
    }


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

    public void playTurn(int selectedPlayerCard) {
        // El jugador juega su carta seleccionada
        Card playerCard = player.playCard(selectedPlayerCard);
        if (playerCard != null) {
            lastPlayerCard = playerCard;

            // Coloca la carta jugada encima de la carta anterior en la mesa
            cardOnTable = playerCard;  // La carta seleccionada por el jugador se pone en la mesa

            // Modifica la suma de la mesa con el valor de la carta jugada
            tableSum += playerCard.getValue();

            // Si la carta seleccionada es un 'A', puede sumar 1 o 10, dependiendo de la elección
            if (playerCard.getName().equals("A")) {
                tableSum += 10;  // Se suma 10 si el jugador elige el As como 10
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

    // Método para actualizar la interfaz
    private void updateUI() {
        // Aquí actualizarías la UI con los cambios en la mesa y las manos de los jugadores
        // Por ejemplo:
        System.out.println("Suma en la mesa: " + tableSum);
        System.out.println("Última carta jugador: " + (lastPlayerCard != null ? lastPlayerCard.getName() : "Ninguna"));
        System.out.println("Última carta computadora: " + (lastComputerCard != null ? lastComputerCard.getName() : "Ninguna"));
    }

    // Mostrar al ganador
    private void showWinner(String winner) {
        System.out.println("El ganador es: " + winner);
    }

    // Otros métodos como checkGameOver() y resetGame() permanecen igual...

    public boolean checkGameOver() {
        return player.getHand().isEmpty() || computer.getHand().isEmpty() || tableSum > 50;
    }

    public void resetGame() {
        player.clearHand();
        computer.clearHand();
        deck.clearDeck();
        tableSum = 0;
        dealCards();
    }

    // Getters
    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

    public int getTableSum() {
        return tableSum;
    }

    public Card getLastPlayerCard() {
        return lastPlayerCard;
    }

    public Card getLastComputerCard() {
        return lastComputerCard;
    }
}
