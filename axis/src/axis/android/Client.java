/**
 * Project name: Open Collaboration - Android Phone to AXIS 213 PTZ Camera
 * Members: David Kebo, Heather Smith
 * CSE537S Fall 2009 Washington University in St. Louis
 * Class Client, Client Thread
 * @author David Kebo davidkebo@gmail.com
 */
package axis.android;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import android.os.Handler;

public class Client implements Runnable {
	
	static String received = "111111";
	Handler clientHandler;
	Client(Handler aHandler) {
		clientHandler = aHandler;
	}

	public void run() {
		DatagramSocket socket = null;

		try {

			socket = new DatagramSocket(4445);
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			// Start server loop
			while (true) {

				displayStatus("Waiting for server packet - ");
				socket.receive(packet);
				received = new String(packet.getData(), 0, packet.getLength());
				axis.ResourceSequence = received;
				displayStatus("Client Received: " + received + " - ");
				
				clientHandler.post(new Runnable() {
					public void run() {
						axis.requestdialog.show();
					}
				});
			}
		}

		catch (final Exception e) {
			displayStatus(" ERROR: " + e);
		}

		socket.close();
		displayStatus("Server Socket Closed ... ");
	}

	public void displayStatus(final String status) {
		axis.status.post(new Runnable() {
			public void run() {
				axis.status.append(status);
			}
		});
	}
}
