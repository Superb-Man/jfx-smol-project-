package ClientGui.Controller;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public abstract class Controller {
    private Node rootNode;

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }
    public Node getRootNode(){
        return rootNode ;
    }

    private Scene getScene() {
        return rootNode.getScene();
    }

    public void setStage(Stage stage) {
        stage.setScene(getScene());
    }

    public Stage getStage() {
        return (Stage) getScene().getWindow();
    }

    public void show(String title) {
        if (getStage() == null) {
            setStage(new Stage());
            getStage().setTitle(title);
            getStage().setResizable(false);
            getStage().showAndWait();
        } else {
            getStage().setTitle(title);
            getStage().sizeToScene();
            getStage().show();
        }
    }
}
