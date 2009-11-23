package axis.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.net.wifi.*;

public class phoneNet extends Context {
	
	private String masterIP;
	private String slaveIP;
	private Vector<String> observerIPs;
	
	public String discoverMaster() {
		Intent intent = new Intent(this, masterDiscoveryReceiver.class);
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE); 
		WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 
		intent.putExtra("ip", wifiInfo.getMacAddress());
		sendBroadcast(intent);		
		
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

	@Override
	public boolean bindService(Intent arg0, ServiceConnection arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int checkCallingOrSelfPermission(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkCallingOrSelfUriPermission(Uri arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkCallingPermission(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkCallingUriPermission(Uri arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkPermission(String arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkUriPermission(Uri arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkUriPermission(Uri arg0, String arg1, String arg2, int arg3,
			int arg4, int arg5) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clearWallpaper() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Context createPackageContext(String arg0, int arg1)
			throws NameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] databaseList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteDatabase(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFile(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void enforceCallingOrSelfPermission(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enforceCallingOrSelfUriPermission(Uri arg0, int arg1,
			String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enforceCallingPermission(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enforceCallingUriPermission(Uri arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enforcePermission(String arg0, int arg1, int arg2, String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enforceUriPermission(Uri arg0, int arg1, int arg2, int arg3,
			String arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enforceUriPermission(Uri arg0, String arg1, String arg2,
			int arg3, int arg4, int arg5, String arg6) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] fileList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationInfo getApplicationInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AssetManager getAssets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getCacheDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContentResolver getContentResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getDatabasePath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getDir(String arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFileStreamPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFilesDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Looper getMainLooper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PackageManager getPackageManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPackageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SharedPreferences getSharedPreferences(String arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSystemService(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Theme getTheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drawable getWallpaper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWallpaperDesiredMinimumHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWallpaperDesiredMinimumWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void grantUriPermission(String arg0, Uri arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FileInputStream openFileInput(String arg0)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileOutputStream openFileOutput(String arg0, int arg1)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLiteDatabase openOrCreateDatabase(String arg0, int arg1,
			CursorFactory arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drawable peekWallpaper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intent registerReceiver(BroadcastReceiver arg0, IntentFilter arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intent registerReceiver(BroadcastReceiver arg0, IntentFilter arg1,
			String arg2, Handler arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeStickyBroadcast(Intent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revokeUriPermission(Uri arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendBroadcast(Intent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendBroadcast(Intent arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendOrderedBroadcast(Intent arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendOrderedBroadcast(Intent arg0, String arg1,
			BroadcastReceiver arg2, Handler arg3, int arg4, String arg5,
			Bundle arg6) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendStickyBroadcast(Intent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTheme(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWallpaper(Bitmap arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWallpaper(InputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startActivity(Intent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean startInstrumentation(ComponentName arg0, String arg1,
			Bundle arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ComponentName startService(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean stopService(Intent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unbindService(ServiceConnection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterReceiver(BroadcastReceiver arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
};