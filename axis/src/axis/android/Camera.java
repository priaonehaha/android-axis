/* File: camera.java
Open Collaboration - Android Phone to AXIS 213 PTZ Camera
----------------------------------------------------------------
Class: CSE537	             	    Instructor: Dr. Roman
Assignment: Course Project	        Semester: Fall 2009
Programmers: David Houngninou, Heather Smith           */

package axis.android;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class Camera {

	private String cameraIPAddress;
    private int cameraPort;
    
    private boolean useAuthentication;
    private String cameraUsername;
    private String cameraPassword;
    
    private boolean useProxy;
    
    private String channel;
    
    private boolean ptzEnabled;
    
    private String resolution;
    private int compression;
    private int framerate;
    
    private URL url;
    private HttpResponse response;
    
   //FIX private Image lastImage;
    
    //FIX  private CameraReadMJPEG reader;
    
  //FIX private ActionEvent cameraEvent;
  //FIX private ActionListener cameraListener;
        
    /** 
     * Creates a new instance of Camera
     */
    public Camera() {
        super();
        
        cameraIPAddress = "";
        cameraPort = 80;
        
        useAuthentication = false;
        cameraUsername = "root";
        cameraPassword = "root";
        
        useProxy = false;
        
        channel = "1";
        
        ptzEnabled = false;
        
        resolution = "";
        compression = -1;
        framerate = -1;
        
      /*FIX  lastImage = null;
        reader = null;
        
        cameraEvent = null;
        cameraListener = null;*/
    }
    
    /**
     * Sets the IP address and port of the camera
     */
    public void setIPAddress(String ipaddress, int port) {
        cameraIPAddress = ipaddress;
        cameraPort = port;
    }
    
    /**
     * Enables or disables the use of authentication
     */
    public void enableAuthentication(boolean value) {
        useAuthentication = value;
    }
    
    /**
     * Sets user name and password
     */
    public void setAuthentication(String username, String password) {
        cameraUsername = username;
        cameraPassword = password;
    }
    
    /**
     * Enables or disables the use of a proxy server
     */
    public void enableProxy(boolean value) {
        useProxy = value;
    }
    
    /**
     * Sets the PTZ functionality
     */
    public void enablePTZ(boolean value) {
        ptzEnabled = value;
    }
    
    /**
     * Returns PTZ functionality
     */
    public boolean isPTZEnabled() {
        return ptzEnabled;
    }
    
    /**
     * Sets the resolution of the video stream
     * 
     * @param value has to be a valid resolution such as "640x480"
     */
    public void setResolution(String value) {
        resolution = value;
    }
    
    /**
     * Sets the compression of the video stream
     * 
     * @param value is the compression rate between 0 (lowest) and 100 (highest)
     */
    public void setCompression(int value) {
        compression = value;
    }
    
    /**
     * Sets the frame rate of the video stream
     * 
     * @param value has to be a valid frame rate in frame per second
     */
    public void setFramerate(int value) {
        framerate = value;
    }
  
 // Executes a given command according to VAPIX API
    public URL getURL()
    {
    	return url;
    }
    
    public HttpResponse getResponse()
    {
    	return response;
    }
    
   /* public void runUrl()
    {
    try {
        url = new URL("http://192.168.1.111/axis-cgi/com/ptz.cgi?camera=1&move=home");
       // URLConnection cameraConnection = url.openConnection();
        URLConnection cameraConnection = (URLConnection) url.openConnection(); 
        cameraConnection.connect();

    } catch (MalformedURLException e) {     // new URL() failed
        
    } catch (IOException e) {               // openConnection() failed
        
    }
    }*/
    
    public void runUrl()
    {
    
    HttpClient mClient= new DefaultHttpClient();
    HttpPost postcommand = new HttpPost("http://192.168.1.111/axis-cgi/com/ptz.cgi?camera=1&move=home");
    try 
    {
        response = mClient.execute(postcommand);
    } 
    catch (Exception e) 
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
   
    // Executes a given command according to VAPIX API
    private void executeCommand(String command) {
        url = null;
        URLConnection connection = null;
        
        try {
            if (useAuthentication == true) {
                Authenticator.setDefault(new MyAuthenticator());
            }
            
            //url = new URL("http://10.0.1.90/axis-cgi/com/ptz.cgi?camera=1&move=home");
            url = new URL(composeURL("") + command);
            
            if (useProxy == false) {
                connection = url.openConnection();
            }
            else {
            	connection = url.openConnection();
            }
            
            connection.setDoOutput(true);
            connection.connect();
            
            DataInputStream input = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
            
            
            input.close();
        }
        
        catch (MalformedURLException exeption) {
           // throw new CameraException("URL Exception");
        }
        
        catch (IOException exeption) {
           // throw new CameraException("IO Exception");
        }
        
        finally {
            connection = null;
            url = null;
        }
    }
    
   
    /**
     * Starts the video stream 
     */
    public void start() throws CameraException {
        
        URL url = null;
        URLConnection connection = null;
        
        try {
            if (useAuthentication == true) {
                Authenticator.setDefault(new MyAuthenticator());
            }
            
            url = new URL(composeURL("MJPEG"));
            
            if (useProxy == false) {
                connection = url.openConnection();
            }
            else {
            	connection = url.openConnection();
            }
            
            connection.connect();
            
            DataInputStream input = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
        /*FIX   cameraEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "message");
            
            reader = new CameraReadMJPEG(input);
            reader.addActionListener(this);
            reader.start(); */
        }
        
        catch (MalformedURLException exeption) {
            throw new CameraException("URL Exception");
        }
        
        catch (IOException exeption) {
            throw new CameraException("IO Exception");
        }
        
        finally {
            connection = null;
            url = null;
        }              
    }
    
    
    /**
     * Stops the video stream
     */
    /*FIX public void halt() {
        reader.halt();
    }*/
    
    /**
     * Gets last image from video stream
     */
  /*FIX  public Image getImage() {
        if(manipulateEnabled == true) {
            lastImage = manipulate(lastImage);
        }
        return lastImage;
    }*/
    
    /**
     * Returns the status of the video stream
     */
    /*FIX public boolean isRunning() {
        if(reader == null) {
            return false;
        }
        
        return reader.isRunning();
    }*/
    
    /**
     * Reacts on image event
     */
    /*FIX  public void actionPerformed(ActionEvent evt) {
        lastImage = reader.getImage();
        cameraListener.actionPerformed(cameraEvent);
    }*/
   
    
    /**
     * Sends a pan/tilt command to the camera
     * 
     * @param x defines the relative pan value
     * @param y defines the relative tilt value
     */
    public void movePanTilt(float x, float y) {
        String command;
        
        String xDirection = String.valueOf(x);
        String yDirection = String.valueOf(y);
        
        command = "axis-cgi/com/ptz.cgi?camera=" + channel;
        command = command + "&rpan=";
        command = command + xDirection;
        command = command + "&rtilt=";
        command = command + yDirection;
        
        executeCommand(command);
    }
    
    /**
     * Sends a zoom command to the camera
     * 
     * @param z defines the relative zoom value
     */
    public void moveZoom(int z){
        String command;
        
        String zDirection = String.valueOf(z);
        
        command = "axis-cgi/com/ptz.cgi?camera=" + channel;
        command = command + "&rzoom=";
        command = command + zDirection;
        
        executeCommand(command);
    }
    

    // Composes a URL string for a snapshot
    private String composeURL(String streamType) {
        String urlString;
        
        urlString = "http://" + cameraIPAddress + ":" + String.valueOf(cameraPort) + "/";
        
        if(streamType.length() == 0) {
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
        
        if(resolution.length() > 0) {
            urlString = urlString + "&resolution=" + resolution;
        }
        if(compression != -1) {
            urlString = urlString + "&compression=" + String.valueOf(compression);
        }
        if((framerate != -1) && (streamType.startsWith("MJPEG") == true)) {
            urlString = urlString + "&fps=" + String.valueOf(framerate);
        }
        
        return urlString;
    }
    
    // MyAuthenticator class
    class MyAuthenticator extends Authenticator {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(cameraUsername, cameraPassword.toCharArray());
        }
    }
    
    
    
}

