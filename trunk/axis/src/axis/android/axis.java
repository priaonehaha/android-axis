/**
 * Project name: Open Collaboration - Android Phone to AXIS 213 PTZ Camera
 * Members: David Kebo, Heather Smith
 * CSE537S Fall 2009 Washington University in St. Louis
 * Class Main, creates main activity
 * @author David Kebo davidkebo@gmail.com
 */
package axis.android;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

public class axis extends Activity {

	Camera camera = new Camera(); // Create a camera object
	ImageView cameraVideo;	int zoomcontrol = 0;
	boolean switchvertical = true;
	boolean switchhorizontal = true;
    masterPhone mPhone = null;



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Dialog resource list
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.resourcelist);
		dialog.setTitle("Resource list");

		Button checkoutButton = (Button) dialog
				.findViewById(R.id.checkoutButton);

		// Creating references to the UI Commands
		final ImageButton leftButton = (ImageButton) findViewById(R.id.leftbutton);
		final ImageButton rightButton = (ImageButton) findViewById(R.id.rightbutton);
		final ImageButton upButton = (ImageButton) findViewById(R.id.upbutton);
		final ImageButton downButton = (ImageButton) findViewById(R.id.downbutton);
		final EditText IPfield = (EditText) findViewById(R.id.ipaddress);
		final ImageButton scanVertical = (ImageButton) findViewById(R.id.scanVertical);
		final ImageButton scanHorizontal = (ImageButton) findViewById(R.id.scanHorizontal);
		final ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
		final ImageButton resourceButton = (ImageButton) findViewById(R.id.resourceButton);
		final Button okButton = (Button) findViewById(R.id.okbutton);
		final ZoomControls zoom = (ZoomControls) findViewById(R.id.zoombutton);
		final TextView status = (TextView) findViewById(R.id.status);
		cameraVideo = (ImageView) findViewById(R.id.ImageView);
		// Buttons are disabled until camera is connected
		leftButton.setVisibility(View.INVISIBLE);
		rightButton.setVisibility(View.INVISIBLE);
		upButton.setVisibility(View.INVISIBLE);
		downButton.setVisibility(View.INVISIBLE);
		homeButton.setVisibility(View.INVISIBLE);
		scanHorizontal.setVisibility(View.INVISIBLE);
		scanVertical.setVisibility(View.INVISIBLE);
		zoom.setVisibility(View.INVISIBLE);

        clientPhone cl = new clientPhone();
        Thread clt = new Thread(cl); 

        try {
        	clt.start();
			Thread.sleep(1000);
			if (!clt.isAlive()){
				mPhone = new masterPhone();
	           	Toast.makeText(axis.this, "Master", Toast.LENGTH_SHORT).show(); 
	            
				//camera.movePanTilt(30, 120);
				status.setText("Master");
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			
		}

		checkoutButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		leftButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				camera.movePanTilt("left");
				status.setText("Left Move");
				CaptureVideo();

			}
		});

		rightButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				camera.movePanTilt("right");
				status.setText("Right Move");
				CaptureVideo();

			}

		});

		upButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				camera.movePanTilt("up");
				URL urlCalled = camera.getURL();
				status.setText(urlCalled.toString());
				status.setText("Up Move");
				CaptureVideo();

			}
		});

		downButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				camera.movePanTilt("down");
				status.setText("Down Move");
				CaptureVideo();

			}
		});

		okButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				boolean validIP = true;
				String IPaddress = IPfield.getText().toString();
				String urlString = "http://" + IPaddress;
				URL url = null;
				URLConnection connection = null;
				try {
					url = new URL(urlString);
					connection = url.openConnection();
					connection.connect();
				} 
				catch (MalformedURLException failed) {
					String failText = "Failure: " + failed.getMessage();
					Toast.makeText(axis.this, failText, Toast.LENGTH_LONG)
							.show();
					validIP = false;
				}
				catch (IOException failed) {
					String failText = "Failure: " + failed.getMessage();
					Toast.makeText(axis.this, failText, Toast.LENGTH_LONG)
							.show();
					validIP = false;
				}
			
				if (validIP) {
					Toast.makeText(axis.this, IPaddress, Toast.LENGTH_SHORT)
							.show();
					camera.setIPAddress(IPaddress, 80);
					status.setText("Connect Requested");

					// Enable buttons
					leftButton.setVisibility(View.VISIBLE);
					rightButton.setVisibility(View.VISIBLE);
					upButton.setVisibility(View.VISIBLE);
					downButton.setVisibility(View.VISIBLE);
					homeButton.setVisibility(View.VISIBLE);
					scanHorizontal.setVisibility(View.VISIBLE);
					scanVertical.setVisibility(View.VISIBLE);
					zoom.setVisibility(View.VISIBLE);

					CaptureVideo();
				}

			}
		});

		homeButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				camera.movePanTilt("home");
				status.setText("Home Position");
				CaptureVideo();

			}
		});

		scanHorizontal.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				if (switchhorizontal)
					camera.movePanTilt("horizontalstart");
				else
					camera.movePanTilt("horizontalend");
				switchhorizontal = !switchhorizontal;
				status.setText("Horizontal scan");
				CaptureVideo();

			}
		});

		scanVertical.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				if (switchvertical)
					camera.movePanTilt("verticalstart");
				else
					camera.movePanTilt("verticalend");
				switchvertical = !switchvertical;
				status.setText("Vertical scan");
				CaptureVideo();
			}
		});

		zoom.setOnZoomInClickListener(new ZoomControls.OnClickListener() {
			public void onClick(View v) {

				if (zoomcontrol < 9000) // Maximum zoom-in value
					zoomcontrol += 1000;
				camera.moveZoom(zoomcontrol);
				status.setText("Zoom In");
				CaptureVideo();
			}
		});

		zoom.setOnZoomOutClickListener(new ZoomControls.OnClickListener() {
			public void onClick(View v) {

				if (zoomcontrol >= 1000) // Maximum zoom-out value
					zoomcontrol -= 1000;
				camera.moveZoom(zoomcontrol);
				status.setText("Zoom Out");
				CaptureVideo();
			}
		});

		resourceButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				dialog.show();

			}
		});

	}

	protected void CaptureVideo() {
		Bitmap bmp = null;

		try {
			URL url = new URL("http://192.168.1.111/axis-cgi/jpg/image.cgi");
			InputStream stream = url.openStream();
			bmp = BitmapFactory.decodeStream(stream);
			stream.close();
		} catch (MalformedURLException e) {
			Toast.makeText(this, "Exception", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(this, "Exception", Toast.LENGTH_LONG).show();
		}
		catch (Throwable t) {
			String failText = "Failure: " + t.getMessage();
			Toast.makeText(axis.this, failText, Toast.LENGTH_LONG)
					.show();
        }
		
		cameraVideo.setImageBitmap(bmp);
		
//		Bitmap bmp; 
//		bmp=BitmapFactory.decodeFile("/sdcard/cerber.jpg");
//		cameraVideo.setImageBitmap(bmp);
		
	}

}