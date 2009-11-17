package axis.android;

import java.util.Vector;

import android.content.Context;
import android.content.Intent;

public class phoneNet {
	
	private String masterIP;
	private String slaveIP;
	private Vector<String> observerIPs;
	
	public String discoverMaster() {
		Intent intent = new Intent();
	    // sendBroadcast(intent);
		return masterIP;
	}
	
	public boolean reserveControl(){
		// if master -- Reserve control
		
		// else slave request from master
			//wait for response		
		
		// else observer just display video
		
		return false;
	};
	
	public boolean pingNet(){
		
		boolean ret = true;
		// maintain communication between Master and Slave
		// if master and a slave exists
		    // ret = ping slave
		// else slave
		    // ret = ping master
		
		return ret;
	}
	
	public void setMaster(String ip){
		masterIP = ip;
		// if ip = self
		   //  initialize a Listener for slaves.
	}
	
	
};