package ClientGui;

import ClientGui.Controller.Controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FXUtil {

    /*
    To load a fxml and set the Controller
     */
    public static <T extends Controller>  T loadFXML(String fxmlName) throws IOException {
        String fxmlPath = "view/" + fxmlName + ".fxml";
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));

        Parent rootNode = loader.load();

        Scene scene = new Scene(rootNode);


        T controller = loader.getController();
        controller.setRootNode(rootNode);

        return controller;
    }

    /*
    Loading fxml Without Controller
     */

    public static FXMLLoader getFXMLLoader(String fxmlName) {
        String fxmlPath = "view/" + fxmlName + ".fxml";
        return new FXMLLoader(Main.class.getResource(fxmlPath));
    }

    public static void startingAnimation(Controller controller){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), controller.getRootNode());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void closingAnimation(Controller controller){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), controller.getRootNode());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }
    public static void scaleTransitioningStarting(Node node){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500)) ;
        scaleTransition.setFromX(0);
        scaleTransition.setToX(1);
        scaleTransition.setFromY(0);
        scaleTransition.setByY(1);
        scaleTransition.setNode(node);
        scaleTransition.play();
    }

    public static void scaleTransitioningClosing(Node node){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500)) ;
        scaleTransition.setFromX(1);
        scaleTransition.setToX(0);
        scaleTransition.setFromY(1);
        scaleTransition.setByY(0);
        scaleTransition.setNode(node);
        scaleTransition.play();
    }

    public static void transLateTransitioning(Node node1 , FlowPane node2){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500)) ;
        translateTransition.fromXProperty().bind(node2.widthProperty());
        translateTransition.setToX(0);
        translateTransition.setNode(node1);
        translateTransition.play();
    }

    public static void showResult(String s){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("");
        alert.setHeaderText("");
        Label label = new Label() ;
        label.setText(s) ;
        label.setStyle("-fx-text-fill:#ffffff;-fx-font-size:18;-fx-alignment-:center;-fx-font-family:system;-fx-font-style:bold;");
        alert.getDialogPane().setContent(label);
        label.setVisible(true);
        //alert.getDialogPane().setContentText(s);
        alert.getDialogPane().setStyle("-fx-background-color: #23395d");
        alert.getDialogPane().setPrefWidth(500);
        alert.getDialogPane().setPrefHeight(100) ;
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK) ;
        alert.showAndWait();
    }

}