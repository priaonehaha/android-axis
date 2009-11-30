package axis.android;

import java.util.Iterator;
import java.util.Vector;

public class resourceReservations {
	
	private static final int RESOURCE_FREE = 0;
	private static final int RESOURCE_MASTER_OWNED = 1;
	private static final int RESOURCE_CLIENT_OWNED = 2;
	
	private class Resource {
		public String name;
		public long timestamp;
		public int status;
	}
	
	private static Vector<Resource> resources = new Vector();
	
	public resourceReservations(){
		Resource pan = new Resource();
		pan.name = "Pan";
		pan.status = RESOURCE_FREE;
		pan.timestamp = System.currentTimeMillis();
		resources.add(pan);
		Resource tilt = new Resource();
		tilt.name = "Tilt";
		tilt.status = RESOURCE_FREE;
		tilt.timestamp = System.currentTimeMillis();
		resources.add(tilt);
		Resource zoom = new Resource();
		zoom.name = "Zoom";
		zoom.status = RESOURCE_FREE;
		zoom.timestamp = System.currentTimeMillis();
		resources.add(zoom);		
		Resource home = new Resource();
		home.name = "Home";
		home.status = RESOURCE_FREE;
		home.timestamp = System.currentTimeMillis();
		resources.add(home);
		
	}
	public static boolean reserveResource (String rName, int newOwner){
		boolean status = false;
		boolean found = false;
		Iterator<Resource> it = resources.iterator();
		while (it != null && !found) {
			Resource r = (Resource) it;
			if (r.name.compareToIgnoreCase(rName) == 0) {
				if (r.status == RESOURCE_FREE) {
					r.status = newOwner;
					status = true;
				} else if ( System.currentTimeMillis() - r.timestamp > 5000){
					r.status = newOwner;
					status = true;
				}
				found = true;

			}
			if (it.hasNext()){
				it = (Iterator<Resource>) it.next();
			} else{
				it = null;
			}
				
		}
		// clear unused reservations by this owner
		it = resources.iterator();
		while (it != null  && found){
			Resource r = (Resource)it;
			if (r.status == newOwner  && r.name.compareToIgnoreCase(rName) != 0){
				r.status = RESOURCE_FREE;
			}
			if (it.hasNext()){
				it = (Iterator<Resource>) it.next();
			} else{
				it = null;
			}
		}
		return status;
	}
}
