package axis.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.*;
import android.widget.TextView;
import android.widget.Toast;

public class masterDiscoveryReceiver extends BroadcastReceiver{

	private boolean masterFound = false;
	private int numReceives = 0;
	@Override
	
	public void onReceive(Context arg0, Intent arg1) {
		
		// TODO Auto-generated method stub
		// Send a response to the slave
		
		WifiManager wifiManager = (WifiManager) arg0.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 

        if (wifiInfo.getMacAddress() != arg1.getCharArrayExtra("ip").toString())
        {
        	//Found a master return an IP Address for networking
        	masterFound = true;
        }
 	}

	public boolean getMasterFound() {
/*		try {
			wait(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return masterFound;
	}

}
