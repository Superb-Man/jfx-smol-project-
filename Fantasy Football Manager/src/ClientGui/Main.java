package ClientGui;

import ClientGui.Controller.Login;
import ClientGui.Model_Search.club;
import ClientGui.Networking.Client;
import Server.Server;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    final String serverAddress = "127.0.0.1";
    final int serverPort = 33333;

    public static Client client ;

    @Override
    public void start(Stage primaryStage) throws Exception {


        // Start from Login Page
        client = new Client(serverAddress,serverPort) ;

        for(club Club : Server.ClubsList){
            System.out.println(Club.getClubName()) ;
        }
        Login loginPage = FXUtil.loadFXML("Login");
        FXUtil.startingAnimation(loginPage) ;
        loginPage.setStage(primaryStage);

        loginPage.show("LOG IN PAGE");

        //Stop all currently running thread
        primaryStage.setOnCloseRequest(event ->{
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }

            System.exit(0);
        }) ;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
