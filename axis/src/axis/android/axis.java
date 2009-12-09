/**
 * Project name: Open Collaboration - Android Phone to AXIS 213 PTZ Camera
 * Members: David Kebo, Heather Smith
 * CSE537S Fall 2009 Washington University in St. Louis
 * Class Main, creates main activity
 * @author David Kebo davidkebo@gmail.com
 */
package axis.android;

import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

public class axis extends Activity {

	static ImageButton leftButton, rightButton, upButton, downButton,
			scanVertical, scanHorizontal, homeButton;
	static ZoomControls zoom;
	Camera camera = new Camera(); // Create a camera object
	int zoomcontrol = 0;
	boolean switchvertical = true, switchhorizontal = true,
			clientApplication = false, serverApplication = false;
	static String serverAddress, clientAddress, IPaddress,
			Resources = "111111", ResourceSequence = "111111";
	static TextView status;
	static ImageView cameraVideo;
	static Dialog requestdialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Handler ServerUIHandler = new Handler();
		final Handler ClientUIHandler = new Handler();
		final Handler ServerSenderUIHandler = new Handler();
		final Handler ClientSenderUIHandler = new Handler();
		final Handler VideoUIHandler = new Handler();

		// Dialog resource list
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.resourcelist);
		dialog.setTitle("Resource list");

		// Dialog Application choice
		final Dialog clientserverdialog = new Dialog(this);
		clientserverdialog.setContentView(R.layout.clientserver);
		clientserverdialog.setTitle("Application type");

		// Dialog Accept/Reject Requests
		requestdialog = new Dialog(this);
		requestdialog.setContentView(R.layout.request);
		requestdialog.setTitle("Peer Request");
		clientserverdialog.show();

		// CheckBoxes
		final CheckBox checkboxVertical = (CheckBox) dialog
				.findViewById(R.id.checkboxVertical);
		final CheckBox checkboxHorizontal = (CheckBox) dialog
				.findViewById(R.id.checkboxHorizontal);
		final CheckBox checkboxZoom = (CheckBox) dialog
				.findViewById(R.id.checkboxZoom);
		final CheckBox checkboxHome = (CheckBox) dialog
				.findViewById(R.id.checkboxHome);
		final CheckBox checkboxPan = (CheckBox) dialog
				.findViewById(R.id.checkboxPan);
		final CheckBox checkboxTilt = (CheckBox) dialog
				.findViewById(R.id.checkboxTilt);

		// Creating references to the UI Commands
		leftButton = (ImageButton) findViewById(R.id.leftbutton);
		rightButton = (ImageButton) findViewById(R.id.rightbutton);
		upButton = (ImageButton) findViewById(R.id.upbutton);
		downButton = (ImageButton) findViewById(R.id.downbutton);
		scanVertical = (ImageButton) findViewById(R.id.scanVertical);
		scanHorizontal = (ImageButton) findViewById(R.id.scanHorizontal);
		homeButton = (ImageButton) findViewById(R.id.homeButton);
		zoom = (ZoomControls) findViewById(R.id.zoombutton);
		status = (TextView) findViewById(R.id.status);
		cameraVideo = (ImageView) findViewById(R.id.ImageView);
		final EditText IPfield = (EditText) findViewById(R.id.ipaddress);
		final ImageButton resourceButton = (ImageButton) findViewById(R.id.resourceButton);
		final Button okButton = (Button) findViewById(R.id.okbutton);

		Button checkoutButton = (Button) dialog
				.findViewById(R.id.checkoutButton);
		Button serverApp = (Button) clientserverdialog
				.findViewById(R.id.ServerApp);
		Button clientApp = (Button) clientserverdialog
				.findViewById(R.id.ClientApp);
		final EditText serverIP = (EditText) clientserverdialog
				.findViewById(R.id.serverip);
		final EditText clientIP = (EditText) clientserverdialog
				.findViewById(R.id.clientip);
		Button acceptRequest = (Button) requestdialog
				.findViewById(R.id.acceptRequest);
		Button rejectRequest = (Button) requestdialog
				.findViewById(R.id.rejectRequest);

		// Buttons are disabled until camera is connected
		disableAllButtons();

		// Accept client resources request
		acceptRequest.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				updateControls();
				requestdialog.dismiss();
			}
		});

		// Reject client resources request
		rejectRequest.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				requestdialog.dismiss();
			}
		});

		// Server application choice
		serverApp.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				serverApplication = true;
				clientAddress = clientIP.getText().toString();
				serverAddress = serverIP.getText().toString();
				new Thread(new Server(ServerUIHandler)).start();
				clientserverdialog.dismiss();
			}
		});

		// Client application choice
		clientApp.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				clientApplication = true;
				clientAddress = clientIP.getText().toString();
				serverAddress = serverIP.getText().toString();
				new Thread(new Client(ClientUIHandler)).start();
				clientserverdialog.dismiss();
			}
		});

		// Checkout Resources
		checkoutButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Resources = "";
				ResourcesBinary(checkboxVertical.isChecked());
				ResourcesBinary(checkboxHorizontal.isChecked());
				ResourcesBinary(checkboxZoom.isChecked());
				ResourcesBinary(checkboxHome.isChecked());
				ResourcesBinary(checkboxPan.isChecked());
				ResourcesBinary(checkboxTilt.isChecked());

				if (serverApplication)
					new Thread(new ServerSender(ServerSenderUIHandler)).start();
				else if (clientApplication)
					new Thread(new ClientSender(ClientSenderUIHandler)).start();

				dialog.dismiss();
			}
		});

		// Left direction control
		leftButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				camera.movePanTilt("left");
				status.append("Left Move - ");
			}
		});

		// Right direction control
		rightButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				camera.movePanTilt("right");
				status.append("Right Move - ");
			}
		});

		// Up direction control
		upButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				camera.movePanTilt("down");
				status.append("Up Move - ");
			}
		});

		// Down direction control
		downButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				camera.movePanTilt("up");
				status.append("Down Move - ");
			}
		});

		// Connect to a camera and start video capture
		okButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				boolean validIP = true;
				IPaddress = IPfield.getText().toString();
				String urlString = "http://" + IPaddress;
				URL url = null;
				URLConnection connection = null;
				try {
					url = new URL(urlString);
					connection = url.openConnection();
					connection.connect();
				} catch (Exception failed) {
					Toast.makeText(axis.this, "Home: " + failed.getMessage(),
							Toast.LENGTH_LONG).show();
					validIP = false;
				}

				if (validIP) {
					camera.setIPAddress(IPaddress, 80);
					status.append("Connect Requested - ");
					// Enable all buttons
					enableAllButtons();
					// Start The Video Capture
					new Thread(new Video(VideoUIHandler)).start();
				}
			}
		});

		// Home reset control
		homeButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				camera.movePanTilt("home");
				status.append("Home Position - ");
				zoomcontrol = 0;
			}
		});

		// Horizontal scan control
		scanHorizontal.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				if (switchhorizontal)
					camera.movePanTilt("horizontalstart");
				else
					camera.movePanTilt("horizontalend");
				switchhorizontal = !switchhorizontal;
				status.append("Horizontal scan - ");
			}
		});

		// Vertical scan control
		scanVertical.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				if (switchvertical)
					camera.movePanTilt("verticalstart");
				else
					camera.movePanTilt("verticalend");
				switchvertical = !switchvertical;
				status.append("Vertical scan - ");
			}
		});

		// Zoom in control
		zoom.setOnZoomInClickListener(new ZoomControls.OnClickListener() {
			public void onClick(View v) {
				if (zoomcontrol < 8500) // Maximum zoom-in value
					zoomcontrol += 1000;
				camera.moveZoom(zoomcontrol);
				status.append("Zoom In - ");
			}
		});

		// Zoom out control
		zoom.setOnZoomOutClickListener(new ZoomControls.OnClickListener() {
			public void onClick(View v) {
				if (zoomcontrol > 1500) // Maximum zoom-out value
					zoomcontrol -= 1000;
				camera.moveZoom(zoomcontrol);
				status.append("Zoom Out - ");
			}
		});

		// Display resources dialog
		resourceButton.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				dialog.show();
			}
		});
	}

	// Resources checked
	protected void ResourcesBinary(boolean isChecked) {
		if (isChecked)
			Resources += "1";
		else
			Resources += "0";
	}

	// Update control panel
	protected static void updateControls() {
		enableAllButtons();

		if (ResourceSequence.charAt(0) == '0') {
			upButton.setVisibility(View.INVISIBLE);
			downButton.setVisibility(View.INVISIBLE);
		}
		if (ResourceSequence.charAt(1) == '0') {
			leftButton.setVisibility(View.INVISIBLE);
			rightButton.setVisibility(View.INVISIBLE);
		}
		if (ResourceSequence.charAt(2) == '0')
			zoom.setVisibility(View.INVISIBLE);

		if (ResourceSequence.charAt(3) == '0')
			homeButton.setVisibility(View.INVISIBLE);

		if (ResourceSequence.charAt(4) == '0')
			scanHorizontal.setVisibility(View.INVISIBLE);

		if (ResourceSequence.charAt(5) == '0')
			scanVertical.setVisibility(View.INVISIBLE);
	}

	// Enable all buttons
	protected static void enableAllButtons() {
		leftButton.setVisibility(View.VISIBLE);
		rightButton.setVisibility(View.VISIBLE);
		upButton.setVisibility(View.VISIBLE);
		downButton.setVisibility(View.VISIBLE);
		homeButton.setVisibility(View.VISIBLE);
		scanHorizontal.setVisibility(View.VISIBLE);
		scanVertical.setVisibility(View.VISIBLE);
		zoom.setVisibility(View.VISIBLE);
	}

	// Disable all buttons
	protected static void disableAllButtons() {
		leftButton.setVisibility(View.INVISIBLE);
		rightButton.setVisibility(View.INVISIBLE);
		upButton.setVisibility(View.INVISIBLE);
		downButton.setVisibility(View.INVISIBLE);
		homeButton.setVisibility(View.INVISIBLE);
		scanHorizontal.setVisibility(View.INVISIBLE);
		scanVertical.setVisibility(View.INVISIBLE);
		zoom.setVisibility(View.INVISIBLE);
	}

	// Trackball Control
	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		status.append("Trackball Control - ");
		switch (event.getAction()) {

		// Multidirectional
		case MotionEvent.ACTION_MOVE:
			float x = event.getX();
			float y = event.getY();

			if (x > 0)
				camera.movePanTilt("right");
			else if (x < 0)
				camera.movePanTilt("left");
			if (y > 0)
				camera.movePanTilt("down");
			else if (y < 0)
				camera.movePanTilt("up");
			break;

		// Zoom
		case MotionEvent.ACTION_DOWN:
			if (zoomcontrol < 8500) // Maximum zoom-in value
				zoomcontrol += 1000;
			camera.moveZoom(zoomcontrol);

			break;
		}
		return true;
	}
	
	public void onTerminate() {
		this.finish();
	}
}