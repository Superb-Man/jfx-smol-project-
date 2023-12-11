package ClientGui.Controller;

import ClientGui.Model_Search.club;
import ClientGui.FXUtil;
import ClientGui.Main;
import ClientGui.Networking.WriteData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;

public class Login extends Controller {

    public static club adminClub = null  ;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton loginButton;

    public static String user_name = null ;
    public static String login_password = null ;

    public static boolean loginRequest = false ;

    @FXML
    public void logIn(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {
        user_name      = user.getText() ;
        login_password = password.getText() ;
        if(user_name != null && login_password != null) {
            WriteData writeData = new WriteData(Main.client.getNetworkUtil(), "Login");
            writeData.run();
        }
        Thread.sleep(100);

        if(adminClub != null) {
            try {

                /*
                Set the club in networkUtil
                set the admin Club
                 */
                Main.client.getNetworkUtil().setClub(Login.adminClub);
                Menu menu = FXUtil.loadFXML("Menu");
                menu.start(adminClub);
                Main.client.getNetworkUtil().setMenu(menu);
                // Show in Current Stage
                menu.setStage(getStage());
                menu.show("We love football");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if( adminClub == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Login Failed");
            alert.setContentText("Enter the correct username and password");
            alert.showAndWait();
        }
    }
}
