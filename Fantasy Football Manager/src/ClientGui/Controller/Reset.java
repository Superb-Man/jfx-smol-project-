package ClientGui.Controller;

import ClientGui.Main;
import ClientGui.Model_Search.club;
import ClientGui.Networking.WriteData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class Reset extends Controller {
    @FXML
    private JFXTextField prePasswordText;

    @FXML
    private JFXTextField newPasswordText;

    @FXML
    private JFXTextField confirmPasswordText;

    @FXML
    private ImageView imageView;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label messageLabel;

    public void start(club Club){
        imageView.setImage(new Image(new ByteArrayInputStream(Club.getImage()))) ;
        prePasswordText.setStyle("-fx-text-fill: #ffffff;");
        newPasswordText.setStyle("-fx-text-fill: #ffffff;");
        confirmPasswordText.setStyle("-fx-text-fill: #ffffff;");
    }

    @FXML
    void saveData(ActionEvent actionEvent) throws InterruptedException {
        if(prePasswordText.getText().equals(Login.adminClub.getPassword()) && newPasswordText.getText().equals(confirmPasswordText.getText())){
            Login.adminClub.setPassword(newPasswordText.getText());
            Main.client.getNetworkUtil().setClub(Login.adminClub) ;
            WriteData writeData = new WriteData(Main.client.getNetworkUtil(),"Reset");
            writeData.run();
            Thread.sleep(100) ;
            messageLabel.setStyle("-fx-text-fill : #00ff00");
            messageLabel.setText("Updated!!");
            prePasswordText.setText(null) ; newPasswordText.setText(null) ; confirmPasswordText.setText(null) ;
        }
        else {
            messageLabel.setStyle("-fx-text-fill : #ff0000");
            messageLabel.setText("Did not match!!");
        }
    }
    @FXML
    public void cancelOnAction(ActionEvent actionEvent) {
        getStage().close() ;
    }
}
