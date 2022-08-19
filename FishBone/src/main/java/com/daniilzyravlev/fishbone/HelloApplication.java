package com.daniilzyravlev.fishbone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class
 */
public class HelloApplication extends Application {
    /**
     * @param stage for project
     * @throws IOException for missing fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Редактор диаграмм 'Рыбья кость'");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args in console
     */
    public static void main(String[] args) {
        launch();
    }
}