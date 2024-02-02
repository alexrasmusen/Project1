package edu.okcu.teamalpha.project1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ImageApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //a bunch of required stuff for it to load
        FXMLLoader fxmlLoader = new FXMLLoader(ImageApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("Image Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}