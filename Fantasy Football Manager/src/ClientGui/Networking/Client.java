package ClientGui.Networking;

public class Client {
    private NetworkUtil networkUtil ;

    public Client(String serverAddress, int serverPort ) {
        try {
            this.networkUtil = new NetworkUtil(serverAddress, serverPort) ;
            ReadData readData = new ReadData(networkUtil) ;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }
}
