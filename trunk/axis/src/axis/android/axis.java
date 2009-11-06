		/* File: axis.java
Open Collaboration - Android Phone to AXIS 213 PTZ Camera
----------------------------------------------------------------
Class: CSE537	             	    Instructor: Dr. Roman
Assignment: Course Project	        Semester: Fall 2009
Programmers: David Houngninou, Heather Smith           */

package axis.android;


import java.net.URL;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

public class axis extends Activity {

	Camera camera = new Camera(); // Create a camera object
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
       
     // Creating references to the UI Commands 
        ImageButton leftButton = (ImageButton) findViewById(R.id.leftbutton);
        ImageButton rightButton = (ImageButton) findViewById(R.id.rightbutton);
        ImageButton upButton = (ImageButton) findViewById(R.id.upbutton);
        ImageButton downButton = (ImageButton) findViewById(R.id.downbutton);
        final EditText IPfield = (EditText) findViewById(R.id.ipaddress);
        Button okButton = (Button) findViewById(R.id.okbutton);
        ZoomControls zoom = (ZoomControls) findViewById(R.id.zoombutton);
        final TextView status = (TextView) findViewById(R.id.status);
        
        leftButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(axis.this, "Move Left", Toast.LENGTH_SHORT).show(); 
   
					//camera.movePanTilt(30, 120);
					status.setText("Left Move");
			
            }
       }); 
        
        rightButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(axis.this, "Move Right", Toast.LENGTH_SHORT).show(); 

					//camera.movePanTilt(60, 86);
					status.setText("Right Move");
		
            }
       }); 
        
        upButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(axis.this, "Move Up", Toast.LENGTH_SHORT).show(); 
        
            	//camera.movePanTilt(120, 180);
        	    camera.runUrl();
			//	URL urlCalled = camera.getURL();
				HttpResponse responseBack = camera.getResponse();
				status.setText(responseBack.toString());
            }
       }); 
        
        downButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(axis.this, "Moved Down", Toast.LENGTH_SHORT).show(); 
  
					//camera.movePanTilt(70, 50);
					status.setText("Down Move");

            }
       }); 
        
        okButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
            	String IPaddress = IPfield.getText().toString();
            	Toast.makeText(axis.this, IPaddress, Toast.LENGTH_SHORT).show(); 
            	camera.setIPAddress(IPaddress, 80);
            	status.setText("Connect Requested");
            }
       }); 
        
        zoom.setOnZoomInClickListener(new ZoomControls.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(axis.this, "Zoom In", Toast.LENGTH_SHORT).show(); 
            	status.setText("Zoom In");
            }
          });
      zoom.setOnZoomOutClickListener(new ZoomControls.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(axis.this, "Zoom Out", Toast.LENGTH_SHORT).show();
            	status.setText("Zoom Out");
            }
          });
      
    }
  
} 