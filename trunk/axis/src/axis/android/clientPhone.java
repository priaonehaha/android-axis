package axis.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientPhone implements Runnable {

	private boolean runClient = true;
	private boolean client = false;
	private boolean observer = false;
	private String commandReq = "DISCOVERY";
	Socket cSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	private int observerCounter = 0;

	public void run() {
		// TODO Auto-generated method stub
		try {
			while (runClient) {
				if (cSocket == null) {
					cSocket = new Socket("255.255.255.255",
							masterPhone.SERVERPORT);
					// If the Server port is reserved try an observer port
					if (cSocket == null || !cSocket.isConnected()) {
						cSocket = new Socket("255.255.255.255",
								masterPhone.OBSERVERPORT);
					}

					if (cSocket != null) {
						in = new BufferedReader(new InputStreamReader(cSocket
								.getInputStream()));
						out = new PrintWriter(cSocket.getOutputStream(), true);
					}
				}

				if (cSocket != null) {
					if (cSocket.isConnected()) {
						if (commandReq.length() > 0) {
							out.println(commandReq);
							Thread.sleep(10);
						}

						String clientLine = in.readLine();
						if (clientLine != null) {
							if (clientLine
									.compareToIgnoreCase("CONFIRM_DISCOVERY") == 0) {
								client = true;
								observer = false;
								commandReq = "";
							} else if (clientLine
									.compareToIgnoreCase("CONFIRM_OBSERVE") == 0) {
								observer = true;
								client = false;
								commandReq = "";
								observerCounter = 0;
							} else if (clientLine.compareToIgnoreCase("PING") == 0) {
								out.println("PING_RESPONSE");
							}
						}

						if (observer) {
							observerCounter += 10;
							if (observerCounter >= 5000) {
								// as an observer, try for the server port again
								cSocket.close();
								cSocket = null;
								in.close();
								out.close();
								commandReq = "DISCOVERY";
							}
						}
					} else {
						runClient = false;
						if (cSocket != null) {
							cSocket.close();
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}

	public boolean requestReservation(String resReq) {
		boolean res = false;
		if (client) {
			out.println(resReq);
			// do I need a sleep here?
			String clientLine;
			try {
				clientLine = in.readLine();
				Thread.sleep(10);
				if (clientLine != null
						&& clientLine
								.compareToIgnoreCase("CONFIRM_RESERVATION") == 0) {
					res = true;
				} else if (clientLine.compareToIgnoreCase("RESERVATION_DENIED") == 0) {
					res = false;
				} else {
					// Lost connection to the master .. need to close client and
					// start master process
					cSocket = null;
				}
			} catch (IOException e) {
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
		commandReq = "";
		return res;
	}

}
