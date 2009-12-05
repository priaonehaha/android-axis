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
import java.net.InetAddress;

import android.os.Handler;

public class Client implements Runnable {
	static String received = "111111";
	String serverAddress = axis.serverAddress;
	Handler clientHandler;
	Client(Handler aHandler) {
		clientHandler = aHandler;
	}

	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket();
			displayStatus(" Socket created - ");
			byte[] buf = new byte[256];
			buf = (axis.Resources).getBytes();

			InetAddress address = InetAddress.getByName(serverAddress);
			DatagramPacket packet = new DatagramPacket(buf, buf.length,
					address, 4444);
			socket.send(packet);

			displayStatus("Packet sent - ");

			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);

			displayStatus("Packet received - ");
			received = new String(packet.getData(), 0, packet.getLength());
			axis.ResourceSequence = received;

			clientHandler.post(new Runnable() {
				public void run() {
					axis.updateControls();
				}
			});

			displayStatus("Received: " + received + " - ");
			socket.close();

		} catch (final Exception e) {
			displayStatus(" ERROR: " + e);
		}
	}

	public void displayStatus(final String status) {
		axis.status.post(new Runnable() {
			public void run() {
				axis.status.append(status);
			}
		});
	}
}
