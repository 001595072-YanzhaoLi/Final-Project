package com.example.finalporject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * image management tools application
 *
 * @author Zhe Ding
 * @date 2021/11/19
 */
public class ImageManagementToolApplication extends Application {
    /**
     * start
     *
     * @param stage start stage
     */
    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(ImageManagementToolApplication.class.getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 700, 390);
        } catch (IOException e) {
            System.err.println("Application launch fail");
        }
        stage.setTitle("Image Management Tool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}