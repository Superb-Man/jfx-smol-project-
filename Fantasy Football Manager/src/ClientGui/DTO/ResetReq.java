package ClientGui.DTO;

import ClientGui.Model_Search.club;

import java.io.Serializable;

public class ResetReq implements Serializable {
    private club Club ;

    public ResetReq(club Club){
        this.Club = Club ;
    }

    public club getClub(){
        return Club ;
    }
}
