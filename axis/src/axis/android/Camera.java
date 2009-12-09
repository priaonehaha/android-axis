/**
 * Project name: Open Collaboration - Android Phone to AXIS 213 PTZ Camera
 * Members: David Kebo, Heather Smith
 * CSE537S Fall 2009 Washington University in St. Louis
 * Class Camera, defines a camera object and camera functions
 * @author David Kebo davidkebo@gmail.com
 */
package axis.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Camera {

	private String cameraIPAddress;
	private int cameraPort;
	private String channel;
	private String resolution;
	private int compression;
	private int framerate;
	private URL url;

	/**
	 * Creates a new instance of Camera
	 */
	public Camera() {
		super();

		cameraIPAddress = "";
		cameraPort = 80;
		channel = "1";
	}

	/**
	 * Sets the camera IP address and port number
	 * 
	 * @param ipaddress
	 * @param port
	 */
	public void setIPAddress(String ipaddress, int port) {
		cameraIPAddress = ipaddress;
		cameraPort = port;
	}

	// Executes a given command according to VAPIX API
	public URL getURL() {
		return url;
	}

	/**
	 * @param command
	 *            defines action to insert in the url call
	 * @throws IOException
	 */
	public void runUrl(String command) throws IOException {

		url = new URL(composeURL("") + command);

		InputStream stream = url.openStream();

		stream.close();
	} // End runurl()

	/**
	 * Sends a pan/tilt command to the camera according to the user control
	 * 
	 * @param direction
	 *            indicates the direction to move the camera
	 */
	public void movePanTilt(String direction) {
		String command;

		if (direction == "horizontalstart")
			command = "axis-cgi/com/ptz.cgi?camera=" + channel + "&pan=-180"
					+ "&tilt=0";
		else if (direction == "horizontalend")
			command = "axis-cgi/com/ptz.cgi?camera=" + channel + "&pan=180"
					+ "&tilt=0";
		else if (direction == "verticalstart")
			command = "axis-cgi/com/ptz.cgi?camera=" + channel + "&pan=0"
					+ "&tilt=180";
		else if (direction == "verticalend")
			command = "axis-cgi/com/ptz.cgi?camera=" + channel + "&pan=0"
					+ "&tilt=-180";

		else {
			command = "axis-cgi/com/ptz.cgi?camera=" + channel;
			command = command + "&move=";
			command = command + direction;
		}

		try {
			runUrl(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sends a zoom command to the camera according to the user control
	 * 
	 * @param z
	 *            defines the zoom value
	 * 
	 */
	public void moveZoom(int z) {
		String command;
		
		command = "axis-cgi/com/ptz.cgi?camera=" + channel;
		command = command + "&zoom=";
		command = command + z;

		try {
			runUrl(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Composes a URL string for a snapshot
	private String composeURL(String streamType) {
		String urlString;

		urlString = "http://" + cameraIPAddress + ":"
				+ String.valueOf(cameraPort) + "/";

		if (streamType.length() == 0) {
			return urlString;
		}

		if (streamType.startsWith("MJPEG") == true) {
			urlString = urlString + "axis-cgi/mjpg/video.cgi?";
		}
		if (streamType.startsWith("JPEG") == true) {
			urlString = urlString + "axis-cgi/jpg/image.cgi?";
		}

		urlString = urlString + "showlength=1";
		urlString = urlString + "&camera=" + channel;

		if (resolution.length() > 0) {
			urlString = urlString + "&resolution=" + resolution;
		}
		if (compression != -1) {
			urlString = urlString + "&compression="
					+ String.valueOf(compression);
		}
		if ((framerate != -1) && (streamType.startsWith("MJPEG") == true)) {
			urlString = urlString + "&fps=" + String.valueOf(framerate);
		}

		return urlString;
	}

}
