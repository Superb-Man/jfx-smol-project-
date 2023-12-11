package ClientGui.DTO;

import ClientGui.Model_Search.club;

import java.io.Serializable;

public class Data implements Serializable {
    private String clubName ;
    private String password ;
    private String checker ;
    private club Club ;

    public Data(club Club , String checker){
        this.Club = Club ;
        this.checker = checker ;
    }
    public Data(String clubName , String password){
        this.clubName = clubName ;
        this.password = password ;
    }

    public void setClubName(String str){
        this.clubName = str;
    }
    public void setPassword(String str){
        this.password = str ;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getChecker() {
        return checker;
    }
    public void setClub( club Club ){
        this.Club = Club ;
    }

    public club getClub(){
        return Club ;
    }

    public String getClubName(){
        return clubName ;
    }

    public String getPassword(){
        return password ;
    }
}
