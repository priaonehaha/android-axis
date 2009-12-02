package axis.android;

import java.io.IOException;
import java.net.ServerSocket;

public class masterPhone implements Runnable {
    public static final int SERVERPORT = 4444; 
    public static final int OBSERVERPORT = 4445; 
    public static resourceReservations RV = new resourceReservations();

	public void run() {
		// TODO Auto-generated method stub
        ServerSocket serverSocket = null;
        ServerSocket observerSocket = null;
        boolean listening = true;

		try {
			observerSocket = new ServerSocket(OBSERVERPORT);
			serverSocket = new ServerSocket(SERVERPORT);
			new masterPhoneServerThread(serverSocket.accept()).start();
		} catch (IOException e) {
		}

		while (listening){
			try {
				new masterPhoneServerThread(observerSocket.accept()).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}
	
}
