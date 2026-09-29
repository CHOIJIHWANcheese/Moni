package com.moni.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/** Entry point for the Moni desktop application. */
public final class MoniApplication extends Application {

    @Override
    public void start(Stage stage) {
        var scene = new Scene(new Label("Moni Project"), 320, 180);
        stage.setTitle("Moni Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

