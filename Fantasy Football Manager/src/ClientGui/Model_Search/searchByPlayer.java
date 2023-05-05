package ClientGui.Model_Search;


import java.util.ArrayList;
import java.util.List;

/*
All search for Player
 */

public class searchByPlayer {

     public List<player> searchByName(String Input,club Club){
        List<player> P = new ArrayList<>() ;
        for(player Player : Club.allPlayers()){
            if(Player.getPlayerName().toLowerCase().equals(Input.toLowerCase())){
                P.add(Player) ;
            }
        }
        return P ;
    }


    public List<player> searchByCountry(String Input,club Club){
         List< player > P = new ArrayList<>() ;
         for(player Player : Club.allPlayers()){
             if(Player.getCountryName().toLowerCase().equals(Input.toLowerCase())) {
                 P.add(Player);
             }
         }
         return P ;
    }


    public List<player> searchByPosition(String Input,club Club){
         List< player > P = new ArrayList<>() ;
         for(player Player : Club.allPlayers()){
            if(Player.getPlayerPosition().toLowerCase().equals(Input.toLowerCase())) {
                P.add(Player);
            }
        }
        return P ;
    }
    public List<player> searchBySalary(double Input1, double Input2,club Club){
        List< player > P = new ArrayList<>() ;
        for(player Player : Club.allPlayers()){
            if(Player.getWeeklySalary() >= Input1 && Player.getWeeklySalary() <= Input2) {
                P.add(Player);
            }
        }
        return P ;
    }
}
