package ie.atu.sw;

import java.io.IOException;

// leap motion library
import com.leapmotion.leap.*;

public class LeapMouse {
	
	public static void main(String[] args) {
		
		// create instances of CustomListener and Controller
		CustomListeners l = new CustomListeners();
		Controller c = new Controller();
		
		// pass customListeners object to Listener
		c.addListener(l);		
		
		// input from LeapMotion device
		try {
			System.in.read();			
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		
		c.removeListener(l);

	}

}
