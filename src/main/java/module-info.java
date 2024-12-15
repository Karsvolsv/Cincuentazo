module ethan.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens ethan.cincuentazo to javafx.fxml;
    exports ethan.cincuentazo;
    exports ethan.cincuentazo.controllers;
    opens ethan.cincuentazo.controllers to javafx.fxml;
    exports ethan.cincuentazo.model;
    opens ethan.cincuentazo.model to javafx.fxml;
}