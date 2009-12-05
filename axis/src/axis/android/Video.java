/**
 * Project name: Open Collaboration - Android Phone to AXIS 213 PTZ Camera
 * Members: David Kebo, Heather Smith
 * CSE537S Fall 2009 Washington University in St. Louis
 * Class Video, Video thread 
 * @author David Kebo davidkebo@gmail.com
 */
package axis.android;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

public class Video implements Runnable {

	Handler videoHandler;
	Video(Handler aHandler) {
		videoHandler = aHandler;
	}
	String IP = "", address = "";

	public void run() {
		try {
			while (true) {
				IP = axis.IPaddress;
				address = "http://" + IP + "/axis-cgi/jpg/image.cgi";
				final URL aURL = new URL(address);
				final URLConnection conn = aURL.openConnection();
				conn.connect();
				final BufferedInputStream bis = new BufferedInputStream(conn
						.getInputStream());
				final Bitmap bm = BitmapFactory.decodeStream(bis);
				bis.close();

				videoHandler.post(new Runnable() {
					public void run() {
						axis.cameraVideo.setImageBitmap(bm);
					}
				});
			}

		} catch (IOException e) {
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
