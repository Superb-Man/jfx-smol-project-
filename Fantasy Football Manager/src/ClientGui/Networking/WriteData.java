package ClientGui.Networking;

import ClientGui.Controller.Login;
import ClientGui.DTO.*;

public class WriteData {

    private Thread thr;
    private NetworkUtil networkUtil;
    private String requestAccess ;

    public WriteData(NetworkUtil networkUtil,String requestAccess) {
        this.requestAccess = requestAccess ;
        this.networkUtil = networkUtil ;
        //System.out.println("CALLED");
    }
    public void run() {
        try {
            System.out.println("RUNNING");
            if(requestAccess.equals("Login")){
                String s = Login.user_name ;
                String s2 =Login.login_password ;
                networkUtil.write(new Data(s,s2));
                System.out.println("SENDING LOGIN REQUEST........");
            }

            if (requestAccess.equals("Edit")) {
                System.out.println("EDITING REQUEST SENDING TO SERVER.....");
                networkUtil.write(new EditReq(networkUtil.getPlayer())) ;
            }
            if(requestAccess.equals("Sell")){
                System.out.println("SELL REQUEST SENDING TO SERVER......");
                networkUtil.write(new SellReq(networkUtil.getPlayer()));
            }
            if(requestAccess.equals("Buy")){
                System.out.println("BUY REQUEST SENDING TO SERVER.......");
                networkUtil.write(new BuyReq(networkUtil.getPlayer()));
            }
            if(requestAccess.equals("Reset")){
                System.out.println("PASSWORD CHANGING REQUEST TO SERVER....... ");
                networkUtil.write(new ResetReq(networkUtil.getClub()));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
