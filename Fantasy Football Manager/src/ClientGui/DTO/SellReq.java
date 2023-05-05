package ClientGui.DTO;

import ClientGui.Model_Search.club;
import ClientGui.Model_Search.player;

import java.io.Serializable;

public class SellReq implements Serializable {
    private player Player ;
    private club Club ;


    public SellReq( player Player) {
        this.Player = Player ;
    }
    public player getPlayer(){
        return Player ;
    }
    public club getClub(){
        return Club ;
    }

}
