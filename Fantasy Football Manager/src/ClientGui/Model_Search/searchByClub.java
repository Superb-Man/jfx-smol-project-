package ClientGui.Model_Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
All search method for Club option
 */

public class searchByClub {
    public List<player> maxSalaryPlayers(club Club){
        List< player > P = new ArrayList<>() ;
        double max = 0.0 ;
        for(player Player : Club.allPlayers()){
            if(Player.getWeeklySalary() > max ){
                max = Player.getWeeklySalary() ;
            }
        }

        for(player Player : Club.allPlayers()){
            if(Player.getWeeklySalary() == max)
                P.add(Player) ;
        }

        return P ;
    }

    public List<player> maxAgePlayers(club Club){
        List< player > P = new ArrayList<>() ;
        int max = 0 ;
        for(player PLayer : Club.allPlayers()){
            if(max < PLayer.getAge()){
                max = PLayer.getAge() ;
            }
        }
        for(player Player : Club.allPlayers()){
            if( max == Player.getAge()){
                P.add( Player ) ;
            }
        }

        return P ;
    }
    public List<player> maxHeightPlayers(club Club){
        List< player > P = new ArrayList<>() ;
        double max = 0.0 ;
        for(player PLayer : Club.allPlayers()){
            if(max < PLayer.getHeight()){
                max = PLayer.getHeight() ;
            }
        }
        for(player Player : Club.allPlayers()){
            if( max == Player.getHeight()){
                P.add( Player ) ;
            }
        }

        return  P ;

    }
    public double ClubCost(club Club){
        double total = 0.0 ;
        for(player Player : Club.allPlayers()) {
            total = total + Player.getWeeklySalary();
        }
        total = total * (52.0) ;
        return total ;
    }

}
