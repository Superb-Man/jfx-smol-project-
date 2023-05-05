package ClientGui.DTO ;

import ClientGui.Model_Search.player;

import java.io.Serializable;

public class EditReq implements Serializable {
    private player Player ;

    public EditReq(player Player){
        this.Player = new player(Player) ;
    }
    public player getPlayer(){
        return Player ;
    }
}
