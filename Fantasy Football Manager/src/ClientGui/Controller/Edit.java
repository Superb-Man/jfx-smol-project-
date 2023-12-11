package ClientGui.Controller;
import ClientGui.Model_Search.player;
import ClientGui.Main;
import ClientGui.Networking.WriteData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.ByteArrayInputStream;

public class Edit extends Controller{

    @FXML
    private ImageView imageView ;

    @FXML
    private ImageView clubImage ;

    @FXML
    private JFXTextField positionField;

    @FXML
    private JFXTextField jerseyField;

    @FXML
    private JFXTextField salaryField;

    @FXML
    private Label messageLabel;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label playerNameLabel;

    @FXML
    private JFXTextField price;

    private String token ;
    private player Player ;

    public void start(player Player, String token){
        //id 1 for sell
        // id 2 for edit
        this.token = token ;
        this.Player = Player ;
        if(token.equals("Sell")){
            salaryField.setVisible(false);
            jerseyField.setVisible(false);
            positionField.setVisible(false);
            messageLabel.setStyle("-fx-text-fill:#ffa500;") ;  price.setStyle("-fx-text-fill:#ffa500;") ;
            imageView.setImage(new Image(new ByteArrayInputStream(Player.getImage())));
            clubImage.setImage(new Image(new ByteArrayInputStream(Login.adminClub.getImage())));
            playerNameLabel.setText(Player.getPlayerName());
        }
        else if(token.equals("Edit")){
            salaryField.setStyle("-fx-text-fill:#ffffff;") ; jerseyField.setStyle("-fx-text-fill:#ffffff;") ; positionField.setStyle("-fx-text-fill:#ffffff;") ;
            imageView.setVisible(false);
            clubImage.setVisible(false);
            messageLabel.setVisible(false);
            price.setVisible(false);
            playerNameLabel.setVisible(false);
        }
    }

    @FXML
    void SaveData(ActionEvent event) {
            System.out.println(validate());
            if (this.token.equals("Sell") && validate() == true) {
                Player.setReleaseClause(Double.parseDouble(price.getText()));
                Main.client.getNetworkUtil().setPlayer(this.Player);
                WriteData writeData = new WriteData(Main.client.getNetworkUtil(),"Sell");
                writeData.run();
                getStage().close();
            }
            else if (this.token.equals("Edit") && validate() == true) {
                String S1 = salaryField.getText();
                String S2 = jerseyField.getText();
                String S3 = positionField.getText();
                Player.setWeeklySalary(Double.parseDouble(salaryField.getText()));
                Player.setJerseyNumber(Integer.parseInt(jerseyField.getText()));
                Player.setPlayerPosition(positionField.getText());

                //Sending the edit request to server to change data for all Client with same name
                Main.client.getNetworkUtil().setPlayer(this.Player);
                WriteData writeData = new WriteData(Main.client.getNetworkUtil(),"Edit");
                writeData.run();
                getStage().close();
            }
        }

    private boolean validate() {
        if (token.equals("Sell")) {
            String S = price.getText();
            try {
                double price = Double.parseDouble(S);
                if (price <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                price.setStyle("-fx-fill: #F000000");
                price.setFocusColor(Color.RED);

                return false;
            }

            return true ;
        }
        else if (token.equals("Edit")) {
            String[] pos = {"defender", "forward", "goalkeeper", "midfielder"};
            String S1 = salaryField.getText();
            String S2 = positionField.getText();
            String S3 = jerseyField.getText();
            if (S2.equalsIgnoreCase(pos[0]) == false && S2.equalsIgnoreCase(pos[1]) == false && S2.equalsIgnoreCase(pos[2]) == false && S2.equalsIgnoreCase(pos[3]) == false) {
                positionField.setStyle("-fx-fill: #F000000");
                positionField.setFocusColor(Color.RED);

                return false;
            }
            try {
                int jersey = Integer.parseInt(S3);
                for (player P : Login.adminClub.allPlayers()) {
                    if ((jersey == P.getJerseyNumber() && P.getPlayerName().equalsIgnoreCase(Player.getPlayerName())==false)  || jersey < 0) {
                        throw new Exception();
                    }
                }
            } catch (Exception e) {
                jerseyField.setStyle("-fx-fill: #F000000");
                jerseyField.setFocusColor(Color.RED);

                return false;
            }
            try {
                double salary = Double.parseDouble(S1);
                if (salary < 0 || salary>Login.adminClub.getAmount()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                salaryField.setStyle("-fx-fill: #F000000");
                salaryField.setFocusColor(Color.RED);

                return false;
            }
            return true;
        }
        return false;
    }

    @FXML
    void cancelEdit(ActionEvent event) {
        getStage().close();
    }
}
