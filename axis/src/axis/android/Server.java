/**
 * Project name: Open Collaboration - Android Phone to AXIS 213 PTZ Camera
 * Members: David Kebo, Heather Smith
 * CSE537S Fall 2009 Washington University in St. Louis
 * Class Client, Server Thread
 * @author David Kebo davidkebo@gmail.com
 */
package axis.android;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.Handler;

public class Server implements Runnable {
	static String received = "111111";
	Handler serverHandler;
	Server(Handler aHandler) {
		serverHandler = aHandler;
	}

	public void run() {
		DatagramSocket socket = null;

		try {

			socket = new DatagramSocket(4444);
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			// Start server loop
			while (true) {

				displayStatus("Waiting for client packet - ");
				socket.receive(packet);
				received = new String(packet.getData(), 0, packet.getLength());
				axis.ResourceSequence = received;
				displayStatus("Received: " + received + " - ");
				
				serverHandler.post(new Runnable() {
					public void run() {
						axis.requestdialog.show();
					}
				});

				buf = (axis.Resources).getBytes();
				// send the response to the client at "address" and "port"
				InetAddress address = packet.getAddress();
				int port = packet.getPort();

				packet = new DatagramPacket(buf, buf.length, address, port);
				socket.send(packet);

				displayStatus(" Server Sent packet back - ");
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
