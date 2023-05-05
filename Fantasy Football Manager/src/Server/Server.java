package Server;

import ClientGui.Model_Search.club;
import ClientGui.Model_Search.country;
import ClientGui.Model_Search.player;
import ClientGui.Networking.NetworkUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Server  {
    private ServerSocket serverSocket;
    public static List<NetworkUtil> clientList ;

    public static List<player> PlayersList       = new ArrayList<>() ;
    public static List<club>   ClubsList         = new ArrayList<>() ;
    public static List<country>CountriesList     = new ArrayList<>() ;
    private static final String INPUT_FILE_NAME_PLAYERS  = "players.txt";
    private static final String OUTPUT_FILE_NAME_PLAYERS = "players.txt";
    private static final String INPUT_FILE_NAME_CLUBS = "clubs.txt" ;
    private static final String OUTPUT_FILE_NAME_CLUBS = "clubs.txt" ;

    Server() throws Exception {
        clientList = new ArrayList<>() ;
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        clientList.add(networkUtil) ;
        System.out.println(clientList.size());
        new ReadThreadServer(networkUtil);
    }

    public static void readFile() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME_PLAYERS));

        while(true){
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            player Player   = new player() ;
            Player.setPlayerName(tokens[0]) ;
            Player.setCountryName(tokens[1]) ;
            Player.setAge(Integer.parseInt(tokens[2])) ;
            Player.setHeight(Double.parseDouble(tokens[3])) ;
            Player.setClubName(tokens[4]) ;
            Player.setPlayerPosition(tokens[5]) ;
            Player.setJerseyNumber(Integer.parseInt(tokens[6])) ;
            Player.setWeeklySalary(Double.parseDouble((tokens[7]))) ;
            Player.setReleaseClause(Double.parseDouble(tokens[8])) ;
            Player.setImage(tokens[9]);
            PlayersList.add(Player) ;
        }

        br.close() ;

        BufferedReader br2 = new BufferedReader((new FileReader(INPUT_FILE_NAME_CLUBS))) ;
        while (true){
            String line = br2.readLine() ;
            if(line == null) break;
            String []tokens = line.split(",") ;
            club Club = new club() ;
            Club.setClubName(tokens[0]);
            Club.setPassword(tokens[1]);
            Club.setAmount(Double.parseDouble(tokens[2]));
            Club.setManager(tokens[3]);
            Club.setImage(tokens[4]);
            Club.setManagerImage(tokens[5]);
            ClubsList.add(Club) ;
        }

        br2.close() ;
    }

    public static void loadClubs(){
        for( player Player : PlayersList){
            for( int i = 0 ;i < ClubsList.size();i++){
                if(ClubsList.get(i).getClubName().equalsIgnoreCase(Player.getClubName())){
                    ClubsList.get(i).addPlayer(Player);
                    Player.setClub(ClubsList.get(i));
                    break ;
                }
            }
        }
    }
    public static void loadCountries(){
        //CountriesList.clear() ;
        for( player Player : PlayersList){
            boolean flag = true ;
            for( country Country : CountriesList ){
                if(Country.getCountryName().toLowerCase().equals(Player.getCountryName().toLowerCase())){
                    Country.addPlayer(Player) ;
                    flag = false ;  break ;
                }
            }
            if( flag ){
                country c = new country() ;
                c.setCountryName(Player.getCountryName());
                c.addPlayer(Player) ;
                CountriesList.add( c ) ;
            }
        }
    }
    /*public static void writeFile() throws Exception{
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME_PLAYERS));
        for(player Player : PlayersList ) {
            bw.write(Player.getPlayerName() + "," + Player.getCountryName() + "," + Player.getAge() + "," + Player.getHeight() + "," + Player.getClubName() + "," + Player.getPlayerPosition() + "," + Player.getJerseyNumber() + "," + Player.getWeeklySalary());
            bw.write("\n");
        }
        bw.close() ;

        BufferedWriter bw2 = new BufferedWriter((new FileWriter(OUTPUT_FILE_NAME_CLUBS))) ;
        for(club Club : ClubsList){
            bw2.write(Club.getClubName()+ "," +Club.getPassword());
            bw2.write("\n");
        }

        bw2.close() ;
    }*/

    public List<player> getPlayersList() {
        return PlayersList;
    }

    public List<club> getClubsList() {
        return ClubsList;
    }

    public static void main (String args[]) throws Exception {
        readFile() ;
        loadClubs();
        loadCountries();
        for(club Club : ClubsList){
            System.out.println(Club.getClubName()+ " "+ Club.getPassword());
        }
        Server server = new Server();
    }
}
