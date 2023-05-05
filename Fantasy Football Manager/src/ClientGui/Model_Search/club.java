package ClientGui.Model_Search;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class club implements Serializable {

    private String clubName ;
    private List<player> Players ;
    private List<player> auctionPlayers ;
    private String password ;
    private String manager ;
    private double amount ;

    private byte [] managerImage ;
    private byte [] image ;

    public club () {
        clubName = null ;
        password = null ;
        auctionPlayers = new ArrayList<>() ;
        Players = new ArrayList<>();
        manager = null ;
        amount  = 0 ;
        managerImage = null ;
        image   = null ;
    }

    public void setClubName( String clubName ){
        this.clubName = clubName ;
    }

    public String getClubName(){
        return clubName ;
    }

    public void setPassword(String password){
        this.password = password ;
    }

    public String getPassword(){
        return password ;
    }

    public void addPlayer( player Player)
    {
        Players.add(Player) ;
    }
    public void removeFromMarket(player Player){
        auctionPlayers.remove(Player) ;
    }

    public void addAuctionPlayer(player Player ){
        auctionPlayers.add(Player) ;
    }

    public void showClubInfo(){
        for(player Player : Players){
            Player.showPlayerInfo() ;
        }
    }
    public int getPlayerCount(){
        return Players.size() ;
    }
    public List<player> allPlayers(){
        List<player> P = new ArrayList<>() ;
        for(player Player : Players){
            P.add( Player ) ;
        }

        return  P ;
    }

    public  List<player> buyPlayers(){
        List<player> P = new ArrayList<>() ;
        for (player Player : auctionPlayers){
            P.add(Player) ;
        }

        return P ;
    }

    public void setManager(String manager){
        this.manager = manager ;
    }
    public String getManager(){
        return manager ;
    }

    public void setAmount(double amount){
        this.amount = amount ;
    }

    public double getAmount() {
        return amount;
    }

    public void setManagerImage(String path) {
        try{
            this.managerImage = new FileInputStream(path).readAllBytes() ;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public byte[] getManagerImage() {
        return managerImage;
    }

    public void setImage(String path){
        try{
            this.image = new FileInputStream(path).readAllBytes() ;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public byte[] getImage() {
        return image;
    }

    public void clearClub(){
        Players.clear() ;
    }


}