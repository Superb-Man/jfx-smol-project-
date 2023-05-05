package ClientGui.Networking;

import ClientGui.Controller.Login;
import ClientGui.Controller.Menu;
import ClientGui.Controller.Reset;
import ClientGui.Model_Search.club;
import ClientGui.Model_Search.player;
import ClientGui.DTO.*;
import javafx.application.Platform;

import java.io.IOException;

public class ReadData implements Runnable{
    private Thread thr;
    private NetworkUtil networkUtil;

    private Menu menu  ;

    public ReadData(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void updateMenu(Menu menu){
        this.menu = menu ;
    }
    public void run() {
        try {
            while (true ) {
                Object o = networkUtil.read();

                //Response from server
                if(o instanceof Data){
                    Data obj = (Data) o ;
                    if(obj.getChecker().equals("TRUE")){
                        //System.out.println(obj.getClub().getClubName());
                        Login.adminClub = obj.getClub() ;
                        networkUtil.setClub(Login.adminClub);
                        //System.out.println(Login.adminClub.getClubName());
                    }
                    else Login.adminClub = null ;
                }

                if (o instanceof EditReq) {
                    EditReq obj = (EditReq) o ;
                    for(int i =0 ;i<networkUtil.getClub().allPlayers().size() ;i++){
                        if(obj.getPlayer().getPlayerName().equalsIgnoreCase(networkUtil.getClub().allPlayers().get(i).getPlayerName())){
                            networkUtil.getClub().allPlayers().get(i).setPlayerPosition(obj.getPlayer().getPlayerPosition());
                            networkUtil.getClub().allPlayers().get(i).setWeeklySalary(obj.getPlayer().getWeeklySalary());
                            networkUtil.getClub().allPlayers().get(i).setJerseyNumber(obj.getPlayer().getJerseyNumber());

                            break;
                        }
                    }

                    for(int i =0 ;i<networkUtil.getClub().buyPlayers().size() ;i++){
                        if(obj.getPlayer().getPlayerName().equalsIgnoreCase(networkUtil.getClub().buyPlayers().get(i).getPlayerName())){
                            networkUtil.getClub().buyPlayers().get(i).setPlayerPosition(obj.getPlayer().getPlayerPosition());
                            networkUtil.getClub().buyPlayers().get(i).setWeeklySalary(obj.getPlayer().getWeeklySalary());
                            networkUtil.getClub().buyPlayers().get(i).setJerseyNumber(obj.getPlayer().getJerseyNumber());
                            Login.adminClub = networkUtil.getClub();

                            break ;
                        }
                    }

                }

                if(o instanceof SellReq){
                    SellReq obj = (SellReq) o ;

                    if(networkUtil.getClub().getClubName().equalsIgnoreCase(obj.getPlayer().getClubName()) == false){
                        boolean var = false ;
                        for(player Player : networkUtil.getClub().buyPlayers()){
                            if(Player.getPlayerName().equalsIgnoreCase(obj.getPlayer().getPlayerName())){
                                var = true ;
                                break;
                            }
                        }
                        if(var == false) {

                            networkUtil.getClub().addAuctionPlayer(obj.getPlayer());
                        }
                    }
                    //networkUtil.getClub().addAuctionPlayer(obj.getPlayer());
                    Login.adminClub = networkUtil.getClub();

                    Platform.runLater(()->{
                        updateMenu(networkUtil.getMenu());
                        menu.updateUI();
                    });

                }

                if(o instanceof BuyReq){

                    BuyReq obj =(BuyReq) o ;

                    /*
                    for(club Club : obj.getClubs()){
                        System.out.println(Club);
                    }*/

                    for(club Club : obj.getClubs()){
                        if(Club.getClubName().equalsIgnoreCase(networkUtil.getClub().getClubName())){
                            networkUtil.setClub(Club) ;
                            break ;
                        }
                    }

                    Login.adminClub = networkUtil.getClub() ;

                    Platform.runLater(()->{
                        updateMenu(networkUtil.getMenu());
                        menu.updateUI() ;
                    });
                }

                if(o instanceof ResetReq){
                    ResetReq obj = (ResetReq) o ;
                    if(obj.getClub().getClubName().equalsIgnoreCase(networkUtil.getClub().getClubName())){
                        networkUtil.setClub(obj.getClub()) ;
                        Login.adminClub = networkUtil.getClub() ;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
