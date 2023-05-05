package Server;

import ClientGui.DTO.*;
import ClientGui.Model_Search.club;
import ClientGui.Model_Search.player;
import ClientGui.Networking.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;


    public ReadThreadServer(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();

                if(o instanceof Data){
                    Data obj = (Data) o ;
                    boolean var = false ;
                    System.out.println("I GET LOGIN REQUEST");
                    for(club Club : Server.ClubsList){
                        if(obj.getClubName().equalsIgnoreCase(Club.getClubName())  &&  obj.getPassword().equals(Club.getPassword())){
                            var = true ;
                            obj.setClub(Club);
                            obj.setChecker("TRUE");
                            networkUtil.write(new Data(obj.getClub(), obj.getChecker()));
                            break ;
                        }
                    }
                    if(var == false){
                        obj.setChecker("FALSE");
                        obj.setClub(null);
                        networkUtil.write(new Data(obj.getClub(), obj.getChecker()));
                    }
                }

                if (o instanceof EditReq) {
                    EditReq obj = (EditReq) o;
                    System.out.println("I GET EDIT REQUEST");

                    int idx = 0 ;
                    for(int i = 0 ;i<Server.PlayersList.size() ; i++){
                        if(Server.PlayersList.get(i).getPlayerName().equalsIgnoreCase(obj.getPlayer().getPlayerName())){
                            Server.PlayersList.get(i).setPlayerPosition(obj.getPlayer().getPlayerPosition());
                            Server.PlayersList.get(i).setJerseyNumber(obj.getPlayer().getJerseyNumber());
                            Server.PlayersList.get(i).setWeeklySalary(obj.getPlayer().getWeeklySalary());

                            idx = i ;

                            break ;
                        }
                    }

                    for(club Club :Server.ClubsList){
                        if(obj.getPlayer().getClubName().equalsIgnoreCase(Club.getClubName())== false ){
                            for(player Player : Club.buyPlayers()){
                                if(Player.getPlayerName().equalsIgnoreCase(obj.getPlayer().getPlayerName())){
                                    Player.setPlayerPosition(obj.getPlayer().getPlayerPosition()) ;
                                    Player.setWeeklySalary(obj.getPlayer().getWeeklySalary()) ;
                                    Player.setJerseyNumber(obj.getPlayer().getJerseyNumber()) ;

                                    break ;
                                }
                            }
                        }
                    }
                    for( NetworkUtil nu : Server.clientList){
                        if(nu != null){
                            nu.write(new EditReq(Server.PlayersList.get(idx)));
                            //System.out.println(obj.getPlayer().getWeeklySalary()+obj.getPlayer().getPlayerPosition());
                        }
                    }

                }

                if(o instanceof SellReq){
                    SellReq obj = (SellReq) o ;
                    System.out.println("I GET SELL REQUEST");

                    int idx = 0 ;
                    for(int i = 0 ;i<Server.PlayersList.size();i++){
                        if(Server.PlayersList.get(i).getPlayerName().equalsIgnoreCase(obj.getPlayer().getPlayerName())){
                            Server.PlayersList.get(i).setReleaseClause(obj.getPlayer().getReleaseClause());
                            idx = i ;
                            break ;
                        }
                    }

                    for(club Club : Server.ClubsList){
                        if(obj.getPlayer().getClubName().equalsIgnoreCase(Club.getClubName()) == false ){
                            Club.addAuctionPlayer(obj.getPlayer());
                        }
                    }

                    for(NetworkUtil nu : Server.clientList){
                        if(nu!=null){
                            nu.write(new SellReq(Server.PlayersList.get(idx)));
                        }
                    }

                    /*
                    Not useful. just to see whether it works!


                     */
                }

                if( o instanceof BuyReq){
                    BuyReq obj  = (BuyReq) o ;
                    String name = obj.getPlayer().getPlayerName() ;
                    System.out.println("I GET BUY REQUEST");
                    /*
                    To clear all players of club and reform them
                     */

                    for(player P : Server.PlayersList){
                        String s= P.getPlayerName();
                        if(s.equalsIgnoreCase(obj.getPlayer().getPlayerName())){
                            for(club Club : Server.ClubsList){
                                if(P.getClubName().equalsIgnoreCase(Club.getClubName())){
                                    Club.setAmount(Club.getAmount()+P.getReleaseClause());
                                }
                                if(obj.getPlayer().getClubName().equalsIgnoreCase(Club.getClubName())){
                                    Club.setAmount(Club.getAmount()-P.getReleaseClause());
                                }
                            }
                        }
                    }

                    for (club Club : Server.ClubsList){
                        Club.clearClub() ;
                    }


                    for(int  i = 0 ; i<Server.PlayersList.size();i++){
                        String s = Server.PlayersList.get(i).getPlayerName() ;
                        if(name.equalsIgnoreCase(s)){
                            Server.PlayersList.get(i).setClubName(obj.getPlayer().getClubName()) ;
                            Server.PlayersList.get(i).setReleaseClause(0);
                            obj.getPlayer().setReleaseClause(0);
                            break ;
                        }
                    }

                    //Now add the Players in to Club
                    Server.loadClubs();

                    /*
                    for(club Club : Server.ClubsList){
                        Club.showClubInfo() ;
                    }
                    */

                    for(club Club : Server.ClubsList){
                        for(player Player : Club.buyPlayers()){
                            // if the written player found in this list remove that//
                            String s = Player.getPlayerName();
                            if(name.equalsIgnoreCase(s)){
                                Club.removeFromMarket(Player) ;
                                //System.out.print(Club.buyPlayers().size());
                                break ;
                            }
                        }
                    }


                    /*
                    for(club Club: Server.ClubsList){
                        System.out.print(Club.buyPlayers().size());
                    }
                    */

                    List <club> Clubslist = new ArrayList<>(Server.ClubsList) ;
                    for(NetworkUtil nu : Server.clientList){
                        if(nu!=null){
                            nu.write(new BuyReq(Clubslist));
                        }
                    }
                }

                if(o instanceof ResetReq){
                    ResetReq obj = (ResetReq) o ;
                    for(club Club :Server.ClubsList){
                        if(obj.getClub().getClubName().equalsIgnoreCase(Club.getClubName())){
                            Club.setPassword(obj.getClub().getPassword());
                            for(NetworkUtil nu : Server.clientList){
                                if(nu!= null) nu.write(new ResetReq(Club)) ;
                            }
                            break ;
                        }
                    }
                }


            }
        } catch (Exception e) {
            System.out.println(e) ;
            Server.clientList.remove(networkUtil) ;
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
