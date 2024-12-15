package ethan.cincuentazo.controllers;

import javafx.fxml.FXML;
import ethan.cincuentazo.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class MenuController {

    /**
     * Method that starts the game by loading the game board view.
     */
    @FXML
    private void startGame() {
        Main.loadView("GameBoard.fxml");
    }

    /**
     * Method that displays the rules of the Cincuentazo game in an alert.
     * The rules include the game objective, turn rules, and game-ending conditions.
     */
    @FXML
    private void showRules() {
        String rules = "1. Objetivo: Ser el último jugador sin superar 50 en la mesa.\n\n"
                + "2. Turno: El jugador juega una carta que modifica la suma en la mesa.\n"
                + "   o Las cartas del 2 al 8 y el 10 suman su valor.\n"
                + "   o La carta 9 no afecta la suma.\n"
                + "   o Las cartas J, Q, K restan 10.\n"
                + "   o La carta As suma 1 o 10.\n"
                + "   o Si un jugador no puede jugar sin superar 50, queda eliminado.\n\n"
                + "3. Fin del juego: El último jugador que quede gana.\n\n";

        // Crear la alerta para mostrar las reglas
        Alert alert = new Alert(Alert.AlertType.INFORMATION, rules, ButtonType.OK);
        alert.setTitle("Reglas del Juego");
        alert.setHeaderText("Instrucciones de Cincuentazo");

        // Mostrar la alerta
        alert.showAndWait();
    }


    /**
     * Prompts the user to enter the number of players for the game.
     * The number of players must be between 1 and 3.
     *
     * @return The number of players selected by the user. If the entered value is invalid, returns -1.
     */
    private int askForNumberOfPlayers() {
        // Create a text input dialog for the user to enter the number of players
        TextInputDialog dialog = new TextInputDialog("3");  // Default value is 3
        dialog.setTitle("Number of Players");
        dialog.setHeaderText("Select the number of players (1-3)");
        dialog.setContentText("Number of players:");

        // Get the value entered by the user
        try {
            String input = dialog.showAndWait().orElse("");
            int numPlayers = Integer.parseInt(input);

            // Validate that the number is between 1 and 3
            if (numPlayers < 1 || numPlayers > 3) {
                showError("Invalid Number of Players", "The number of players must be between 1 and 3.");
                return -1;
            }

            return numPlayers;
        } catch (NumberFormatException e) {
            showError("Invalid Number of Players", "Please enter a valid number.");
            return -1;
        }
    }

    /**
     * Shows an error alert with the given title and message.
     *
     * @param title The title of the error alert.
     * @param message The message displayed in the alert.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    /**
     * Method that closes the game and terminates the application.
     */
    @FXML
    private void exitGame() {
        System.exit(0);
    }
}
