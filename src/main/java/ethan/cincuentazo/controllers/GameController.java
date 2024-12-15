package ethan.cincuentazo.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import ethan.cincuentazo.model.CincuentazoGame;
import ethan.cincuentazo.model.Player;
import ethan.cincuentazo.model.Card;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Controlador de la vista del juego Cincuentazo.
 * Gestiona las interacciones entre la lógica del juego y la interfaz gráfica.
 */
public class GameController {

    @FXML
    private Label tableSumLabel;  // Label para mostrar la suma de la mesa
    @FXML
    private Label winnerLabel;  // Label para mostrar el ganador
    @FXML
    private Label lastCardsLabel;  // Label para mostrar las últimas cartas
    @FXML
    private Label lastPlayerCardLabel;  // Label para mostrar la última carta del jugador
    @FXML
    private Label lastComputerCardLabel;  // Label para mostrar la última carta de la computadora

    @FXML
    private ImageView playerCard1;
    @FXML
    private ImageView playerCard2;
    @FXML
    private ImageView playerCard3;
    @FXML
    private ImageView playerCard4;

    @FXML
    private ImageView computerCard1;
    @FXML
    private ImageView computerCard2;
    @FXML
    private ImageView computerCard3;
    @FXML
    private ImageView computerCard4;

    @FXML
    private ImageView tableCard; // Imagen de la carta en la mesa

    private Card currentTableCard; // Carta actual en la mesa

    @FXML
    private Button endGameButton;

    private CincuentazoGame game;
    private Card lastPlayerCard;
    private Card lastComputerCard;

    /**
     * Constructor de la clase. Inicializa el objeto juego.
     */
    public GameController() {
        game = new CincuentazoGame();
    }

    /**
     * Método que se ejecuta al inicializar la vista. Inicia el juego y actualiza la interfaz de usuario.
     */
    @FXML
    private void initialize() {
        game = new CincuentazoGame();  // Asegúrate de que game se inicializa correctamente
        updateTableCard();
        updateUI();
    }

    /**
     * Método que maneja la selección de una carta por parte del jugador.
     *
     * @param event El evento de clic en la carta.
     */
    @FXML
    public void selectPlayerCard(MouseEvent event) {
        ImageView selectedCardImageView = (ImageView) event.getSource();
        int cardIndex = getCardIndex(selectedCardImageView);

        if (cardIndex != -1) {
            System.out.println("Carta seleccionada: " + cardIndex);
            playTurn(cardIndex);  // Pasar el índice de la carta seleccionada
        }
    }

    /**
     * Obtiene el índice de la carta seleccionada.
     *
     * @param selectedCardImageView La imagen de la carta seleccionada.
     * @return El índice de la carta o -1 si no se encuentra.
     */
    private int getCardIndex(ImageView selectedCardImageView) {
        if (selectedCardImageView.equals(playerCard1)) {
            return 0;
        } else if (selectedCardImageView.equals(playerCard2)) {
            return 1;
        } else if (selectedCardImageView.equals(playerCard3)) {
            return 2;
        } else if (selectedCardImageView.equals(playerCard4)) {
            return 3;
        }
        return -1;  // Si no se encuentra la carta seleccionada
    }

    /**
     * Método que maneja el turno de juego después de que el jugador selecciona una carta.
     * Actualiza la UI y verifica si alguien ha ganado.
     *
     * @param selectedCard El índice de la carta seleccionada.
     */
    @FXML
    private void playTurn(int selectedCard) {
        game.playTurn(selectedCard); // Lógica del turno

        // Actualizar la carta en la mesa con la última carta jugada por el jugador
        currentTableCard = game.getLastPlayerCard();
        updateTableCard();

        lastPlayerCard = game.getLastPlayerCard();
        lastComputerCard = game.getLastComputerCard();

        if (lastPlayerCard != null) {
            lastPlayerCardLabel.setText("Última carta jugador: " + lastPlayerCard.getName());
        }
        if (lastComputerCard != null) {
            lastComputerCardLabel.setText("Última carta computadora: " + lastComputerCard.getName());
        }

        updateUI();

        if (game.getTableSum() > 50) {
            showWinner("Nadie");
        } else {
            // Iniciar un hilo para la jugada de la computadora
            new Thread(() -> {
                try {
                    // Simulamos un retraso para la jugada de la computadora
                    Thread.sleep(1000);  // Espera de 1 segundo

                    // La computadora juega su carta
                    game.playTurn(-1);  // Aquí, la computadora juega automáticamente

                    Platform.runLater(() -> {
                        lastPlayerCard = game.getLastPlayerCard();
                        lastComputerCard = game.getLastComputerCard();

                        if (lastPlayerCard != null) {
                            lastPlayerCardLabel.setText("Última carta jugador: " + lastPlayerCard.getName());
                        }
                        if (lastComputerCard != null) {
                            lastComputerCardLabel.setText("Última carta computadora: " + lastComputerCard.getName());
                        }

                        // Actualizar la carta en la mesa
                        currentTableCard = game.getLastComputerCard();
                        updateTableCard();
                        updateUI();

                        if (game.getTableSum() > 50) {
                            showWinner("Nadie");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();  // Inicia el hilo
        }
    }


    /**
     * Actualiza la carta en la mesa en la interfaz gráfica.
     */
    private void updateTableCard() {
        if (currentTableCard != null) {
            try {
                Image image = new Image(getClass().getResourceAsStream("/images/" + currentTableCard.getImageName() + ".png"));
                tableCard.setImage(image);
            } catch (NullPointerException e) {
                System.out.println("Imagen no encontrada para: " + currentTableCard.getImageName());
            }
        }
    }

    /**
     * Método para terminar el juego y volver al menú principal.
     */
    @FXML
    private void endGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) endGameButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); // Muestra el menú principal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los elementos de la interfaz de usuario con los datos actuales del juego.
     */
    private void updateUI() {
        tableSumLabel.setText("Suma de la mesa: " + game.getTableSum());
        updateCards(game.getPlayer(), playerCard1, playerCard2, playerCard3, playerCard4);
        updateCards(game.getComputer(), computerCard1, computerCard2, computerCard3, computerCard4);
    }

    /**
     * Actualiza las cartas visibles de un jugador en la interfaz de usuario.
     *
     * @param player El jugador cuyas cartas se van a actualizar.
     * @param card1 La primera carta del jugador.
     * @param card2 La segunda carta del jugador.
     * @param card3 La tercera carta del jugador.
     * @param card4 La cuarta carta del jugador.
     */
    private void updateCards(Player player, ImageView card1, ImageView card2, ImageView card3, ImageView card4) {
        List<Card> cards = player.getHand();

        // Asegurarse de que la computadora siempre tenga 4 cartas visibles
        for (int i = 0; i < 4; i++) {
            ImageView currentCardView = null;

            // Dependiendo del índice, asignamos la carta correspondiente
            switch (i) {
                case 0: currentCardView = card1; break;
                case 1: currentCardView = card2; break;
                case 2: currentCardView = card3; break;
                case 3: currentCardView = card4; break;
            }

            // Si el jugador tiene suficientes cartas, actualizamos la imagen
            if (i < cards.size()) {
                try {
                    // Intentamos cargar la imagen de la carta actual
                    Image image = new Image(getClass().getResourceAsStream("/images/" + cards.get(i).getImageName() + ".png"));
                    currentCardView.setImage(image);  // Actualizamos la imagen de la carta
                } catch (NullPointerException e) {
                    // Si no se encuentra la imagen, mostramos un mensaje de error
                    System.out.println("Imagen no encontrada para: " + cards.get(i).getImageName());
                }
            } else {
                // Si no hay más cartas, seleccionamos una carta aleatoria
                Random rand = new Random();
                int randomIndex = rand.nextInt(cards.size());  // Generamos un índice aleatorio

                // Obtenemos la carta aleatoria
                Card randomCard = cards.get(randomIndex);
                try {
                    // Intentamos cargar la imagen de la carta aleatoria
                    Image image = new Image(getClass().getResourceAsStream("/images/" + randomCard.getImageName() + ".png"));
                    currentCardView.setImage(image);  // Actualizamos la imagen con la carta aleatoria
                } catch (NullPointerException e) {
                    // Si no se encuentra la imagen, mostramos un mensaje de error
                    System.out.println("Imagen no encontrada para: " + randomCard.getImageName());
                }
            }
        }
    }

    /**
     * Muestra el mensaje de ganador en la interfaz de usuario.
     *
     * @param winner El nombre del jugador que ganó, o "Nadie" si no hay ganador.
     */
    private void showWinner(String winner) {
        if (playerEliminated() && computerEliminated()) {
            showPopup("¡Nadie ha ganado, la suma ha superado 50!", true);
        } else if (playerEliminated()) {
            showPopup("¡El jugador ha sido eliminado, la suma ha superado 50!", true);
        } else if (computerEliminated()) {
            showPopup("¡La computadora ha sido eliminada, la suma ha superado 50!", true);
        } else {
            String message = "¡" + winner + " ha ganado!";
            showPopup(message, false);
        }

        endGameButton.setDisable(true);
    }

    /**
     * Método que verifica si el jugador está eliminado porque no puede jugar debido a la suma en la mesa.
     */
    private boolean playerEliminated() {
        return game.getPlayer().getHand().stream()
                .noneMatch(card -> (game.getTableSum() + card.getValue()) <= 50);
    }

    /**
     * Método que verifica si la computadora está eliminada porque no puede jugar debido a la suma en la mesa.
     */
    private boolean computerEliminated() {
        return game.getComputer().getHand().stream()
                .noneMatch(card -> (game.getTableSum() + card.getValue()) <= 50);
    }

    @FXML
    public void endGameButton(javafx.event.ActionEvent actionEvent) {
        try {
            // Cargar la vista del menú principal (MenuPrincipal.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuPrincipal.fxml"));
            Parent root = loader.load();

            // Obtener el Stage (ventana actual) desde el evento
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            // Establecer la nueva escena
            stage.setScene(new Scene(root));

            // Mostrar la nueva ventana
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra una ventana emergente con el mensaje del ganador o de eliminación.
     *
     * @param message El mensaje a mostrar en la ventana emergente.
     * @param closeApp Si es verdadero, se cierra la aplicación después de dar "OK".
     */
    private void showPopup(String message, boolean closeApp) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Fin del juego");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (closeApp) {
                    Stage stage = (Stage) endGameButton.getScene().getWindow();
                    stage.close();  // Cierra la ventana
                }
            }
        });
    }
}
