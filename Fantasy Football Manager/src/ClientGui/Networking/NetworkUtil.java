package ClientGui.Networking;

import ClientGui.Controller.Menu;
import ClientGui.Model_Search.club;
import ClientGui.Model_Search.player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkUtil {

    private club Club = null ;
    private player Player =null ;
    private Menu menu ;

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public void setPlayer(player Player){
        this.Player = Player ;
    }

    public player getPlayer(){
        return Player ;
    }

    public void setClub(club Club){
        this.Club = Club ;
    }

    public void setMenu(Menu menu){
        this.menu = menu ;
    }

    public Menu getMenu(){
        return menu ;
    }

    public club getClub(){
        return Club ;
    }

    public NetworkUtil(String s, int port) throws IOException {
        this.socket = new Socket(s, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public NetworkUtil(Socket s) throws IOException {
        this.socket = s;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {
        return ois.readUnshared();
    }

    public void write(Object o) throws IOException {
        oos.writeUnshared(o);
        oos.reset();
        oos.flush();
    }

    public void closeConnection() throws IOException {
        ois.close();
        oos.close();
    }

}
