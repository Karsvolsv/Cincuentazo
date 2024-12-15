package ethan.cincuentazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainStage;

    /**
     * Método que inicia la aplicación JavaFX. Este método es invocado
     * cuando la aplicación es lanzada, y establece la escena inicial
     * cargando el archivo FXML de la vista principal.
     *
     * @param primaryStage El escenario principal donde se mostrará la interfaz.
     * @throws Exception Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        loadView("MenuPrincipal.fxml");
    }

    /**
     * Método estático que carga y muestra una nueva vista FXML en la escena.
     * Este método se utiliza para cambiar entre diferentes vistas en la aplicación.
     *
     * @param fxml El nombre del archivo FXML que define la vista a cargar.
     */
    public static void loadView(String fxml) {
        try {
            // Cargar el archivo FXML y establecer la nueva escena en el escenario
            Parent root = FXMLLoader.load(Main.class.getResource("/fxml/" + fxml));
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (Exception e) {
            // Imprimir el error en caso de que ocurra alguna excepción
            e.printStackTrace();
        }
    }

    /**
     * Método principal de la aplicación. Llama al método `launch` de la clase
     * `Application` para iniciar la ejecución de la aplicación JavaFX.
     *
     * @param args Argumentos que se pasan a la aplicación al iniciarla.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
