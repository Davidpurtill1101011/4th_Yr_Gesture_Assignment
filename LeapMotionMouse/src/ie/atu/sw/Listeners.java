package ie.atu.sw;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;
import java.awt.*;

public class Listeners extends Listener {
	
	public Robot r;
	public void onConnect(Controller c) {
		c.enableGesture(Gesture.Type.TYPE_CIRCLE);
		c.enableGesture(Gesture.Type.TYPE_SWIPE);
		c.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		
	}
	
	public void onFrame(Controller c) {
		
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Frame frame = c.frame();
		InteractionBox ib = frame.interactionBox();
		for(Finger f : frame.fingers()) {
			if(f.type() == Finger.Type.TYPE_INDEX) {
				Vector fingerPos = f.tipPosition();
				Vector boxFingerPos = ib.normalizePoint(fingerPos);
				Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				r.mouseMove((int)(screen.width * boxFingerPos.getX()), (int)(screen.height - boxFingerPos.getY() * screen.height));
			}
		}
	}
}
