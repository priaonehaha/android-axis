package axis.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class masterPhoneServerThread extends Thread {

    private boolean runMaster = true;
    private boolean connected = false; 
	private static final int RESOURCE_CLIENT_OWNER = 2;
    
    private Socket socket = null;

    public masterPhoneServerThread(Socket socket) {
		super("masterPhoneServerThread");
		this.socket = socket;
  }

	public void run() {
		int counter = 0;
		while (runMaster){
			
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				PrintWriter out = new PrintWriter(
						socket.getOutputStream(), true);

				//check for slave
				String clientReq = in.readLine();

				if (clientReq != null) {
					counter = 0;
					if (clientReq.compareToIgnoreCase("DISCOVERY") == 0){
						if (!connected){
							connected = true;
							out.print("CONFIRM_DISCOVERY");
							
						}else{
							out.print("CONFIRM_OBSERVE");
							
						}
					}else if (clientReq.compareToIgnoreCase("RESERVE_PAN") == 0){
						// ReserveManager
						if (resourceReservations.reserveResource("Pan", RESOURCE_CLIENT_OWNER)){
							out.print("CONFIRM_RESERVATION");
						} else {
							out.print("RESERVATION_DENIED");
						}
						
					} else if (clientReq.compareToIgnoreCase("RESERVE_TILT") == 0){
						// ReserveManager
						if (resourceReservations.reserveResource("Tilt", RESOURCE_CLIENT_OWNER)){
							out.print("CONFIRM_RESERVATION");
						} else {
							out.print("RESERVATION_DENIED");
						}
						
					} else if (clientReq.compareToIgnoreCase("RESERVE_ZOOM") == 0 ){
						// ReserveManager
						if (resourceReservations.reserveResource("ZOOM", RESOURCE_CLIENT_OWNER)){
							out.print("CONFIRM_RESERVATION");
						} else {
							out.print("RESERVATION_DENIED");
						}
						
					}
				} else if ( counter == 9000){
					out.println("PING");
				} else if ( counter > 10000){
					connected = false;
				}
				
				Thread.sleep(10);
				counter += 10;
			} catch (InterruptedException e) {
		
			} catch (IOException e) {
			}
		}

  } 

}
