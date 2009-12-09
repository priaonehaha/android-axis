package axis.android;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.Handler;

public class ServerSender implements Runnable {
	
	String clientAddress = axis.clientAddress;
	Handler serverSenderHandler;
	ServerSender(Handler aHandler) {
		serverSenderHandler = aHandler;
	}
	

	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket();
			displayStatus(" Server Socket created - ");
			byte[] buf = new byte[256];
			buf = (axis.Resources).getBytes();

			InetAddress address = InetAddress.getByName(clientAddress);
			DatagramPacket packet = new DatagramPacket(buf, buf.length,
					address, 4445);
			socket.send(packet);

			displayStatus("Server Packet sent - ");

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
