package ie.atu.sw;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;

import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.Gesture.Type;
import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

// leapMotion listener - Listener is in leap motion library
public class CustomListeners extends Listener {
	
	//  generates native system input events where control of the mouse and keyboard is needed
	public Robot r;
	
	// declares gestures to be used
	public void onConnect(Controller c) {
		// scrolling up and down pages
		c.enableGesture(Gesture.Type.TYPE_CIRCLE);
		// can launch start menu
		c.enableGesture(Gesture.Type.TYPE_SWIPE);
		// emulates a mouse click
		c.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
	}
	
	// 
	public void onFrame(Controller c) {		

		// create robot each frame
		try {
			r = new Robot();
		} catch (AWTException e) {			
			e.printStackTrace();
		}
		
		// create frame
		Frame frame = c.frame();
		
		// represents box-shaped region within the field of view of the Leap Motion controller
		InteractionBox ib = frame.interactionBox();
		
		// iterate over a finger list
		for (Finger f : frame.fingers()) {			
			// check for index finger to follow around screen
			if (f.type() == Finger.Type.TYPE_INDEX) {
				// sets finger position on screen
				Vector fingerPosition = f.stabilizedTipPosition();
				
				// get coordinates based on the interactionbox
				Vector boxFingerPosition = ib.normalizePoint(fingerPosition);
				
				// gets the dimension of the screen and matches up with boxFingerPosition
				Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				
				// move cursor position, cast from float to int
				r.mouseMove((int) (screen.width * boxFingerPosition.getX()),
						(int) (screen.height - boxFingerPosition.getY() * screen.height));
			}
		}

		// iterate over the different gestures
		for (Gesture g : frame.gestures()) {			
			// circle clockwise scrolls down, counter clockwise scrolls up 
			if (g.type() == Type.TYPE_CIRCLE) {
				CircleGesture circle = new CircleGesture(g);
				// determine if clockwise or counter clockwise
				if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI / 4) {
					r.mouseWheel(1);
					try {
						Thread.sleep(50);					
					} catch (Exception e) {
						e.printStackTrace();
						}
				} else {
					r.mouseWheel(-1);
					try {
						Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			} else if (g.type() == Type.TYPE_SCREEN_TAP) {
				r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);				
			} else if (g.type() == Type.TYPE_SWIPE && g.state() == State.STATE_START) {
				r.keyPress(KeyEvent.VK_WINDOWS);
				r.keyRelease(KeyEvent.VK_WINDOWS);
			}			
		}
	}
}


