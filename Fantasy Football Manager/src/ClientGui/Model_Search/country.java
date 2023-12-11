package ClientGui.Model_Search;

import java.util.List;
import java.util.ArrayList;

public class country {

    private String countryName ;
    private List<player> Players ;

    public country(){
        countryName = null ;
        Players     = new ArrayList<>() ;
    }
    public void setCountryName( String countryName){
        this.countryName = countryName ;
    }

    public String getCountryName(){
        return countryName ;
    }

    public void addPlayer(player Player){
        Players.add(Player) ;
    }
    public int getPlayerCount(){
        return  Players.size();
    }

}
