package ClientGui.Controller;

import ClientGui.Model_Search.club;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class ClubInfo extends Controller {

    @FXML
    private ImageView coachImage;

    @FXML
    private ImageView clubImage;

    @FXML
    private Label coachNameLabel;

    @FXML
    private Label clubPasswordLabel;

    @FXML
    private Label clubAmountLabel;

    @FXML
    private JFXButton backButton;

    public void start(club Club){
        clubImage.setImage(new Image(new ByteArrayInputStream(Club.getImage())));
        coachImage.setImage(new Image(new ByteArrayInputStream(Club.getManagerImage())));
        coachNameLabel.setText(Club.getManager());
        clubPasswordLabel.setText("Password  : "+Club.getPassword());
        clubAmountLabel.setText(  "Club Net Worth : "+Double.toString(Club.getAmount()/1e9)+" B $");
    }

    @FXML
    void backOnAction(ActionEvent event) {
        getStage().close();
    }

}
