package ClientGui.Controller;
import ClientGui.Model_Search.player;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.dnd.DragGestureEvent;
import java.io.ByteArrayInputStream;

public class Details extends Controller{


    @FXML
    private ImageView backGround ;

    @FXML
    private Label nameField ;

    @FXML
    private Label countryField;

    @FXML
    private Label ageField;

    @FXML
    private Label heightField;

    @FXML
    private Label clubField;

    @FXML
    private Label positionField;

    @FXML
    private Label jerseyField;

    @FXML
    private Label salaryField;

    @FXML
    private Label statusField;

    @FXML
    private JFXButton backButton;

    @FXML
    private ImageView imageView ;

    @FXML private ImageView clubImage ;


    public void start(player Player){
        //backGround.isResizable();

        imageView.setImage(new Image(new ByteArrayInputStream(Player.getImage())));
        clubImage.setImage(new Image(new ByteArrayInputStream(Login.adminClub.getImage())));

        nameField.setText("        "+Player.getPlayerName()) ;
        countryField.setText("   "+Player.getCountryName());
        clubField.setText("   "+Player.getClubName());
        ageField.setText("   "+Double.toString(Player.getAge())+" Yrs");
        salaryField.setText("   "+Double.toString(Player.getWeeklySalary()/1000.0) +" K $") ;
        heightField.setText("   "+Double.toString(Player.getHeight())+" m");
        positionField.setText("   #"+Player.getPlayerPosition());
        jerseyField.setText("   "+Integer.toString(Player.getJerseyNumber()));
        if(Player.getReleaseClause() == 0)  statusField.setText("   No Release Clause");
        else statusField.setText("   "+Double.toString(Player.getReleaseClause()/1000.0)+ "K $");
    }

    /*
    This implements show&wait as stage was null before
    Function is in Controller Class
     */

    @FXML
    void backButtonAction(ActionEvent event) {
        getStage().close() ;
    }

}
