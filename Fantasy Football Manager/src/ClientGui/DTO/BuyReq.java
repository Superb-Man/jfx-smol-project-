package ClientGui.DTO;

import ClientGui.Model_Search.club;
import ClientGui.Model_Search.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuyReq implements Serializable {
    private player Player ;
    private List <club> Clubs = new ArrayList<>() ;

    public BuyReq(player Player){
        this.Player = Player ;
    }
    public BuyReq(List<club> Clubs){
        this.Clubs = Clubs ;
    }

    public List<club> getClubs(){
        return Clubs ;
    }

    public player getPlayer(){
        return Player ;
    }
}
