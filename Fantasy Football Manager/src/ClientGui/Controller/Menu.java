package ClientGui.Controller;
import ClientGui.Model_Search.club;
import ClientGui.Model_Search.player;
import ClientGui.Model_Search.searchByPlayer;
import ClientGui.Model_Search.searchByClub ;
import ClientGui.FXUtil;
import ClientGui.Main;
import ClientGui.view.PlayerCard;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Controller{

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton playerButton;

    @FXML
    private JFXButton clubButton;

    @FXML
    private JFXButton marketButton;

    @FXML
    private Label userNameField;

    @FXML
    private JFXButton logOutButton;

    @FXML
    private AnchorPane OptionTab;

    @FXML
    private JFXButton nameButton;

    @FXML
    private JFXButton countryButton;

    @FXML
    private JFXButton positionButton;

    @FXML
    private JFXButton salaryButton;

    @FXML
    private JFXButton maxSalaryButton;

    @FXML
    private JFXButton maxHeightButton;

    @FXML
    private JFXButton maxAgeButton;

    @FXML
    private JFXButton clubCostButton;

    @FXML
    private JFXButton clubInfoButton ;

    @FXML
    private JFXButton resetButton ;

    @FXML
    private AnchorPane oneFieldTab;

    @FXML
    private JFXTextField Input;

    @FXML
    private Label SearchName;

    @FXML
    private JFXButton searchButton_one;

    @FXML
    private AnchorPane twoFieldTab;

    @FXML
    private JFXTextField Input1;

    @FXML
    private JFXTextField Input2;

    @FXML
    private JFXButton searchButton_two;

    @FXML
    private FlowPane playerCardFlowPane;

    @FXML
    private ImageView imageView ;



    private ObservableList<Node> playerCards ;

    //private Map<String, PlayerCard> playerCardMap ;

    private List<player> plist ;
    private String str1  ;
    private String str2  ;

    private double lowBound ;
    private double higherBound ;

    private String option ;
    private String subOption ;
    private String clubOption  ;


    private JFXButton [] buttons ;
    public void start(club Club){

        //to load all cards
        //System.out.println(Club.getClubName());
        playerCards = playerCardFlowPane.getChildren() ;

        plist = new ArrayList<>() ;

        buttons = new JFXButton[]{nameButton, countryButton, positionButton, salaryButton , maxAgeButton ,maxHeightButton,maxSalaryButton ,clubCostButton};
        for(JFXButton button : buttons){
            button.managedProperty().bind(button.visibleProperty()) ;
        }

        imageView.setImage(new Image(new ByteArrayInputStream(Login.adminClub.getImage())));

        userNameField.setText(Club.getClubName()) ;
        option              = "Home" ;
        subOption           = null ;
        clubOption          = null ;
        hideAndShowTabs() ;

        //just for starting -> home menu
        showAllCards(Login.adminClub.allPlayers()) ;

        FXUtil.startingAnimation(this);

        str1 = null ; str2 = null ;

    }

    public void showAllCards(List<player> plist2){

        //at least one card is changed-> clear and refresh
        if(plist.equals(plist2) == false){
            playerCards.clear() ;

            for(player Player : plist2){
                PlayerCard playerCard = new PlayerCard(Player) ;
                FXUtil.scaleTransitioningStarting(playerCard) ;
                playerCards.add(playerCard) ;
            }
        }

        //else do nothing

        plist = plist2 ;
    }

    public void allPlayerSearchResults(){
        str1 = Input.getText() ;
        //search By Name -> Search Button
        if(subOption.equals("Name") && str1!= null){
            searchByPlayer list = new searchByPlayer() ;
            List <player> playerList = list.searchByName(str1,Login.adminClub) ;
            showAllCards(playerList) ;
            if(playerCards.isEmpty() == true){
                FXUtil.showResult("No Player with that Name");
            }
        }
        //Search By Country ->Search Button
        else if(subOption.equals("Country") && str1!= null){
            searchByPlayer list = new searchByPlayer() ;
            List <player> playerList = list.searchByCountry(str1,Login.adminClub) ;
            showAllCards(playerList) ;
            if(playerCards.isEmpty() == true){
                FXUtil.showResult("No Player with this Country");
            }
        }
        //search By Position -> Search Button
        else if(subOption.equals("Position") && str1!=null){
            if(validation(subOption) == true){
                searchByPlayer list = new searchByPlayer() ;
                List <player> playerList = list.searchByPosition(str1,Login.adminClub) ;
                showAllCards(playerList) ;
                if(playerCards.isEmpty() == true){
                    FXUtil.showResult("No Player with that position");
                }
            }
            //else Do Nothing
        }

        //Search By Salary Range-> 2nd Search Button
        else if(subOption.equals("Salary")){
            str1 = Input1.getText() ;
            str2 = Input2.getText() ;

            if(validation(subOption) == true && str1!= null && str2!= null){
                searchByPlayer list = new searchByPlayer()  ;
                List<player>playerList = list.searchBySalary(lowBound, higherBound, Login.adminClub) ;
                showAllCards(playerList) ;
                if(playerCards.isEmpty() == true){
                    FXUtil.showResult("No such Player with that Salary");
                }

            }
        }

    }

    public void allClubResults( String  token){
        if(token.equals("MaxAge")){
            hideAndShowTabs();
            maxSalaryButton.setStyle("-fx-background-color: #454545"); maxHeightButton.setStyle("-fx-background-color: #454545"); maxAgeButton.setStyle("-fx-background-color: #000000"); clubCostButton.setStyle("-fx-background-color: #454545");

            searchByClub list = new searchByClub() ;
            List <player> playerList = list.maxAgePlayers(Login.adminClub) ;
            showAllCards(playerList) ;
        }
        else if(token.equals("MaxSalary")){
            hideAndShowTabs() ;
            maxSalaryButton.setStyle("-fx-background-color: #000000"); maxHeightButton.setStyle("-fx-background-color: #454545"); maxAgeButton.setStyle("-fx-background-color: #454545"); clubCostButton.setStyle("-fx-background-color: #454545");

            searchByClub list = new searchByClub() ;
            List <player> playerList = list.maxSalaryPlayers(Login.adminClub) ;
            showAllCards(playerList) ;
        }
        else if(token.equals("MaxHeight")){
            hideAndShowTabs() ;
            maxSalaryButton.setStyle("-fx-background-color: #454545"); maxHeightButton.setStyle("-fx-background-color: #000000"); maxAgeButton.setStyle("-fx-background-color: #454545"); clubCostButton.setStyle("-fx-background-color: #454545");

            searchByClub list = new searchByClub() ;
            List <player> playerList = list.maxHeightPlayers(Login.adminClub) ;
            showAllCards(playerList) ;
        }

        else if(token.equals("ClubCost")){
            hideAndShowTabs() ;
            maxSalaryButton.setStyle("-fx-background-color: #454545"); maxHeightButton.setStyle("-fx-background-color: #454545"); maxAgeButton.setStyle("-fx-background-color: #454545"); clubCostButton.setStyle("-fx-background-color: #000000");

            String str ;
            searchByClub totalCost = new searchByClub() ;
            double d = totalCost.ClubCost(Login.adminClub)/1000000.0 ;
            str = "Yearly Cost of "+ Login.adminClub.getClubName()+ " is "+Double.toString(d)+" M $" ;
            //str = String.format("%.1f", d) ;
            //str = "Yearly Cost of " + Login.adminClub.getClubName() + " : "+ str ;
            FXUtil.showResult(str) ;
        }

    }


    //Setting the visible and manage property of nodes

    public void hideAndShowTabs(){
        if(option.equals("Home")){

            homeButton.setStyle("-fx-background-color: #000000");  playerButton.setStyle("-fx-background-color: #5c5c5c"); clubButton.setStyle("-fx-background-color:#5c5c5c"); marketButton.setStyle("-fx-background-color: #5c5c5c");
            nameButton.setStyle("-fx-background-color: #454545"); countryButton.setStyle("-fx-background-color: #454545"); positionButton.setStyle("-fx-background-color: #454545"); salaryButton.setStyle("-fx-background-color: #454545");
            maxSalaryButton.setStyle("-fx-background-color: #454545"); maxHeightButton.setStyle("-fx-background-color: #454545"); maxAgeButton.setStyle("-fx-background-color: #454545"); clubCostButton.setStyle("-fx-background-color: #454545");



            //System.out.println("Home") ;
            OptionTab.setManaged(false) ;
            OptionTab.setVisible(false) ;
            //System.out.println(playerSearchOptionTab.isDisable());

            oneFieldTab.setManaged(false) ;
            oneFieldTab.setVisible(false) ;

            twoFieldTab.setManaged(false) ;
            twoFieldTab.setVisible(false) ;
        }
        else if(option.equals("Player")){

            homeButton.setStyle("-fx-background-color: #5c5c5c");  playerButton.setStyle("-fx-background-color: #000000"); clubButton.setStyle("-fx-background-color:#5c5c5c"); marketButton.setStyle("-fx-background-color: #5c5c5c");
            nameButton.setStyle("-fx-background-color: #454545"); countryButton.setStyle("-fx-background-color: #454545"); positionButton.setStyle("-fx-background-color: #454545"); salaryButton.setStyle("-fx-background-color: #454545");
            maxSalaryButton.setStyle("-fx-background-color: #454545"); maxHeightButton.setStyle("-fx-background-color: #454545"); maxAgeButton.setStyle("-fx-background-color: #454545"); clubCostButton.setStyle("-fx-background-color: #454545");

            //System.out.println("Player");
            if(OptionTab.isVisible() == false) {
                OptionTab.setManaged(true);
                OptionTab.setVisible(true);
                FXUtil.scaleTransitioningStarting(OptionTab);
            }


            nameButton.setVisible(true);
            countryButton.setVisible(true);

            positionButton.setVisible(true);
            salaryButton.setVisible(true);

            maxSalaryButton.setVisible(false);
            maxAgeButton.setVisible(false);
            maxHeightButton.setVisible(false);
            clubCostButton.setVisible(false);

            if(subOption == null){

                nameButton.setStyle("-fx-background-color: #454545"); countryButton.setStyle("-fx-background-color: #454545"); positionButton.setStyle("-fx-background-color: #454545"); salaryButton.setStyle("-fx-background-color: #454545");

                oneFieldTab.setManaged(false) ;
                oneFieldTab.setVisible(false) ;

                twoFieldTab.setManaged(false) ;
                twoFieldTab.setVisible(false) ;
            }
            else if(subOption.equals("Name") || subOption.equals("Country") || subOption.equals("Position")){
                twoFieldTab.setManaged(false);
                twoFieldTab.setVisible(false);
                if(oneFieldTab.isVisible() == false){
                    oneFieldTab.setManaged(true);
                    oneFieldTab.setVisible(true);
                    FXUtil.transLateTransitioning(oneFieldTab , playerCardFlowPane) ;
                }
                if(subOption.equals("Name")){
                    nameButton.setStyle("-fx-background-color: #000000"); countryButton.setStyle("-fx-background-color: #454545"); positionButton.setStyle("-fx-background-color: #454545"); salaryButton.setStyle("-fx-background-color: #454545");
                    SearchName.setText("Name");
                }
                if(subOption.equals("Country")){

                    nameButton.setStyle("-fx-background-color: #454545"); countryButton.setStyle("-fx-background-color: #000000"); positionButton.setStyle("-fx-background-color: #454545"); salaryButton.setStyle("-fx-background-color: #454545");
                    SearchName.setText("Country");
                }
                if(subOption.equals("Position")){

                    nameButton.setStyle("-fx-background-color: #454545"); countryButton.setStyle("-fx-background-color: #454545"); positionButton.setStyle("-fx-background-color: #000000"); salaryButton.setStyle("-fx-background-color: #454545");
                    SearchName.setText("Position");
                }

            }
            else if( subOption.equals("Salary")){

                nameButton.setStyle("-fx-background-color: #454545"); countryButton.setStyle("-fx-background-color: #454545"); positionButton.setStyle("-fx-background-color: #454545"); salaryButton.setStyle("-fx-background-color: #000000");

                oneFieldTab.setManaged(false) ;
                oneFieldTab.setVisible(false) ;
                if(twoFieldTab.isVisible() == false){
                    twoFieldTab.setManaged(true) ;
                    twoFieldTab.setVisible(true) ;
                    FXUtil.transLateTransitioning(twoFieldTab , playerCardFlowPane) ;
                }
            }
        }

        else if(option.equals("Club")){

            homeButton.setStyle("-fx-background-color: #5c5c5c");  playerButton.setStyle("-fx-background-color: #5c5c5c"); clubButton.setStyle("-fx-background-color: #000000"); marketButton.setStyle("-fx-background-color: #5c5c5c");
            nameButton.setStyle("-fx-background-color: #454545"); countryButton.setStyle("-fx-background-color: #454545"); positionButton.setStyle("-fx-background-color: #454545"); salaryButton.setStyle("-fx-background-color: #454545");
            maxSalaryButton.setStyle("-fx-background-color: #454545"); maxHeightButton.setStyle("-fx-background-color: #454545"); maxAgeButton.setStyle("-fx-background-color: #454545"); clubCostButton.setStyle("-fx-background-color: #454545");


            if(OptionTab.isVisible() == false){
                OptionTab.setManaged(true);
                OptionTab.setVisible(true);
                FXUtil.scaleTransitioningStarting(OptionTab);
            }

            nameButton.setVisible(false);
            countryButton.setVisible(false);
            positionButton.setVisible(false);
            salaryButton.setVisible(false);
            maxSalaryButton.setVisible(true);
            maxAgeButton.setVisible(true);
            maxHeightButton.setVisible(true);
            clubCostButton.setVisible(true);

            if(subOption == null){

                maxSalaryButton.setStyle("-fx-background-color: #454545"); maxHeightButton.setStyle("-fx-background-color: #454545"); maxAgeButton.setStyle("-fx-background-color: #454545"); clubCostButton.setStyle("-fx-background-color: #454545");
                oneFieldTab.setManaged(false) ;
                oneFieldTab.setVisible(false) ;

                twoFieldTab.setManaged(false) ;
                twoFieldTab.setVisible(false) ;
            }
        }

        else if(option.equals("Market")){

            homeButton.setStyle("-fx-background-color: #5c5c5c;");  playerButton.setStyle("-fx-background-color: #5c5c5c;"); clubButton.setStyle("-fx-background-color:#5c5c5c;"); marketButton.setStyle("-fx-background-color: #000000");
            nameButton.setStyle("-fx-background-color: #454545"); countryButton.setStyle("-fx-background-color: #454545"); positionButton.setStyle("-fx-background-color: #454545"); salaryButton.setStyle("-fx-background-color: #454545");
            maxSalaryButton.setStyle("-fx-background-color: #454545"); maxHeightButton.setStyle("-fx-background-color: #454545"); maxAgeButton.setStyle("-fx-background-color: #454545"); clubCostButton.setStyle("-fx-background-color: #454545");

            OptionTab.setManaged(false);
            OptionTab.setVisible(false);
            oneFieldTab.setManaged(false) ;
            oneFieldTab.setVisible(false) ;

            twoFieldTab.setManaged(false) ;
            twoFieldTab.setVisible(false) ;

        }

    }

    //Search By Name->
    @FXML
    void setSubOption1(ActionEvent event) {
        option = "Player"  ;subOption = "Name" ;
        hideAndShowTabs() ;
    }

    //Search By Country
    @FXML
    void setSubOption2(ActionEvent event) {
        option = "Player";  subOption = "Country" ;
        hideAndShowTabs() ;
    }

    //Position Search->
    @FXML
    void setSubOption3(ActionEvent event) {
        option = "Player"; subOption = "Position" ;
        hideAndShowTabs() ;
    }

    //Search By Salary->
    @FXML
    void setSubOption4(ActionEvent event) {
        option = "Player" ; subOption = "Salary" ;
        hideAndShowTabs() ;
    }

    @FXML
    void getClubCost(ActionEvent event) {
        clubOption = "ClubCost";
        allClubResults(clubOption);

    }

    //Players with MaxAge -> Max Age Button
    @FXML
    void getMaxAgeList(ActionEvent event) {
        clubOption = "MaxAge" ;
        allClubResults(clubOption);

    }

    //Players with MaxSalary -> Max Salary Button
    @FXML
    void getMaxSalaryList(ActionEvent event) {
        clubOption = "MaxSalary" ;
        allClubResults(clubOption);
    }

    //Players with MaxHeight -> Max Height Button
    @FXML
    void getMaxHeightList(ActionEvent event) {
        clubOption = "MaxHeight" ;
        allClubResults(clubOption);
    }

    //List of Players according to search
    @FXML
    void getTheList(ActionEvent event) {
        allPlayerSearchResults() ;
    }

    //Players with SalaryBound after 2nd search button is pressed
    @FXML
    void getTheListBySalary(ActionEvent event) {
        allPlayerSearchResults() ;
    }

    @FXML
    void logOut(ActionEvent event) {
        try {
            Login.adminClub = null ;
            Main.client.getNetworkUtil().setClub(null);
            Login loginPage = FXUtil.loadFXML("login");
            loginPage.setStage(getStage());
            loginPage.show("LOG IN PAGE");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //All Players of Club,Home Button
    @FXML
    void showCards(ActionEvent event) {
        option = "Home" ; subOption = null ;
        hideAndShowTabs() ;
        showAllCards(Login.adminClub.allPlayers()) ;
    }

    //After click on Club button
    @FXML
    void showClubOptionTab(ActionEvent event) {
        option = "Club" ;
        subOption = null ;
        hideAndShowTabs() ;
    }

    //After click on Player Button
    @FXML
    void showPlayerSearchOptionTab(ActionEvent event) {
        option = "Player" ;
        subOption = null ;
        hideAndShowTabs() ;
    }


    @FXML
    void getMarketList(ActionEvent event){
        option = "Market" ;
        hideAndShowTabs();
        showAllCards(Login.adminClub.buyPlayers());
    }

    @FXML
    void setResetButton(ActionEvent actionEvent){
        try {
            Reset reset = FXUtil.loadFXML("Reset");
            FXUtil.startingAnimation(reset);
            reset.start(Login.adminClub);

            reset.show("Reset");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void showClubInfo(ActionEvent actionEvent){
        try {
            ClubInfo clubInfo = FXUtil.loadFXML("ClubInfo");
            FXUtil.startingAnimation(clubInfo);
            clubInfo.start(Login.adminClub);

            clubInfo.show("Info");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //To update the UI after Server Response

    public void updateUI(){

        if(option.equals("Home")){
            hideAndShowTabs() ;
            showAllCards(Login.adminClub.allPlayers()) ;
        }

        else if(option.equals("Player")){
            allPlayerSearchResults() ;
        }

        else if(option.equals("Club")){
            allClubResults(clubOption);
        }
        else if(option.equals("Market")){
            hideAndShowTabs();
            showAllCards(Login.adminClub.buyPlayers()) ;
        }

    }

    public boolean validation(String valid){
        if(valid.equals("Position")){
            String []pos ={"defender","forward","goalkeeper","midfielder"} ;
            for(var s : pos){
                if(str1.equalsIgnoreCase(s)) {
                    return true;
                }
            }
            Input.setStyle("-fx-fill: #F000000");
            Input.setFocusColor(Color.RED);
            Input.requestFocus();
            return false;
        }
        else if(valid.equals("Salary")){
            try{
                lowBound    = Double.parseDouble(str1) ;
                higherBound = Double.parseDouble(str2) ;
                if(lowBound > higherBound || lowBound < 0 || higherBound < 0){
                    throw new Exception() ;
                }
            }catch (Exception e){
                Input1.setStyle("-fx-fill: #F00");
                Input1.setFocusColor(Color.RED);
                Input2.setStyle("-fx-fill: #F00");
                Input2.setFocusColor(Color.RED);
                return false;
            }
            return true ;
        }
        return false ;
    }

}
