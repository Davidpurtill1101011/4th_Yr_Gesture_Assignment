package ie.atu.sw;

import java.io.IOException;

import com.leapmotion.leap.Controller;

public class LeapMouse {
	public static void main(String[] args) {
		Listeners l = new Listeners();
		Controller c = new Controller();
		c.addListener(l);
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.removeListener(l);
		
	}

}
