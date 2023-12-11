package ClientGui.view;
import ClientGui.Controller.Details;
import ClientGui.Controller.Edit;
import ClientGui.Controller.Login;
import ClientGui.Model_Search.player;
import ClientGui.FXUtil;
import ClientGui.Main;
import ClientGui.Networking.WriteData;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PlayerCard extends AnchorPane {

    @FXML
    private ImageView imageView ;

    @FXML
    private Label cardName;

    @FXML
    private JFXButton auctionButton;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXButton detailButton;

    private player Player ;

    public PlayerCard( player Player) {
        // Loads the template from Card FXML
        FXMLLoader loader = FXUtil.getFXMLLoader("PlayerCard");
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            System.out.println(Player.getPlayerName());
            e.printStackTrace();
        }

        this.Player = Player;

        if(Player.getClubName().equalsIgnoreCase(Login.adminClub.getClubName())){
            auctionButton.setText("Sell");
        }
        else {
            auctionButton.setText("Buy");
            editButton.setDisable(true) ;
        }
        cardName.setText(Player.getPlayerName());
        editButton.setText("Edit");
        detailButton.setText("Details");
        imageView.setImage(new Image(new ByteArrayInputStream(Player.getImage())));

        //Edit button->

        editButton.setOnAction(e->{
            try {
                Edit edit = FXUtil.loadFXML("Edit");
                FXUtil.startingAnimation(edit);
                edit.start(this.Player, "Edit");

                edit.show("EDIT");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        //Details Button->
        detailButton.setOnAction(e->{
            try{
                Details details = FXUtil.loadFXML("Details") ;
                FXUtil.startingAnimation(details); ;
                details.start(this.Player);

                details.show("Details of a Player");
            }catch (IOException exception){
                exception.printStackTrace() ;
            }
        });

        //Buy or Sell Button->
        auctionButton.setOnAction(e->{
            try{
                if(auctionButton.getText().equals("Sell")){

                    //Chk whether a request already sent before ->
                    if(Player.getReleaseClause() > 0){
                        FXUtil.showResult("Request Sent before");
                    }
                    else {
                        Edit edit = FXUtil.loadFXML("Edit");
                        FXUtil.startingAnimation(edit);
                        edit.start(this.Player, "Sell");

                        edit.show("SELL");
                    }
                }
                else if(auctionButton.getText().equals("Buy")){

                    //This part is not used after using Platform.Run later ->

                    if(Player.getReleaseClause() == 0.0){
                        FXUtil.showResult("      Bought");
                    }
                    else if(Player.getReleaseClause()> Login.adminClub.getAmount()){
                        FXUtil.showResult("You have lesser amount of $");
                    }
                    else {

                        /*
                        setting the player's  new Club name
                        Sending a request to server to remove from the current Club before setting the club
                        To remove the player from all market lists
                         */

                        //System.out.println("I BOUGHT IT");
                        this.Player.setClubName(Login.adminClub.getClubName());
                        Main.client.getNetworkUtil().setPlayer(this.Player);
                        WriteData writeData = new WriteData(Main.client.getNetworkUtil(), "Buy") ;
                        writeData.run() ;
                    }
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
    public player getPlayer(){
        return Player ;
    }


}
