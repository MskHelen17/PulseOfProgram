package view;

import java.awt.*;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class View {
    public static Stage primaryStage;
    private static Scene scene;
    private static Parent root;
    public static boolean master;


    public View(Stage primaryStage){
        master = false;
        this.primaryStage = primaryStage;
        this.primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/resources/images/logo.png")));
        this.primaryStage.setTitle("Пульс Программы");
        scene = new Scene(new Group());
        scene.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
    }
    public static void showNewScene(String fxmlName, int minWidth, int minHeight){
        try {
            root = FXMLLoader.load(View.class.getResource(fxmlName));
            scene.setRoot(root);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(minWidth);
            primaryStage.setMinHeight(minHeight);
            primaryStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }




}
