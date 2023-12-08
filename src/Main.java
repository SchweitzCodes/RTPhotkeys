import java.awt.AWTException;
import java.awt.Robot;
import java.awt.MouseInfo;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.awt.event.InputEvent;
/**
 * 
 * @author Matt Schweitzer
 *
 */
public class Main implements NativeKeyListener {
	// Vars
	int mouseX1 = 0; // FIND ACTUAL NUMBERS AT WORK AND HARDCODE
	int mouseY1 = 0;
	int mouseX2 = 0;
	int mouseY2 = 0;
	private boolean ctrl;
	private boolean S;
	
	public static void main(String[] args) {
	    try {
	        GlobalScreen.registerNativeHook();
	    }
	    catch (NativeHookException ex) {
	        System.err.println("There was a problem registering the native hook.");
	        System.err.println(ex.getMessage());
	        System.exit(1);
	    }
	    GlobalScreen.addNativeKeyListener(new Main());		
	}
	
	// When key pressed
	public void nativeKeyPressed(NativeKeyEvent e) {
	    // if F4 pressed: return all process
	    if (e.getKeyCode() == NativeKeyEvent.VC_F4){
	    	try {
				click(mouseX1, mouseY1); // Click select all
				Thread.sleep(100);
				System.out.print("seccond click");
				click(mouseX2, mouseY2); // Click return
			} catch (AWTException | InterruptedException e1) {
		        System.err.println("There was a problem attempting to click the mouse");
		        System.err.println(e1.getMessage());
		        System.exit(1);
			}
	    }
	    
	    // multiple inputs
	    if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
	        this.ctrl = true;
	    }
	    if (e.getKeyCode() == NativeKeyEvent.VC_S) {
	        this.S = true;
	    }

	    // Set "select all" button location
	    if (e.getKeyCode() == NativeKeyEvent.VC_A && this.ctrl  && this.S ) {
	    	System.out.println("Select loc set");
	    	mouseX1 = MouseInfo.getPointerInfo().getLocation().x;
	    	mouseY1 = MouseInfo.getPointerInfo().getLocation().y;
	    }
	    
	    // Set "return" button location
	    if (e.getKeyCode() == NativeKeyEvent.VC_R && this.ctrl  && this.S ) {
	    	System.out.println("Return loc set");
	    	mouseX2 = MouseInfo.getPointerInfo().getLocation().x;
	    	mouseY2 = MouseInfo.getPointerInfo().getLocation().y;
	    }
	}
	
	public void nativeKeyReleased(NativeKeyEvent e) {
		// multiple inputs
	    if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
	        this.ctrl = false;
	    }
	    if (e.getKeyCode() == NativeKeyEvent.VC_S) {
	        this.S = false;
	    }
	}

	// click at x,y
	public static void click(int x, int y) throws AWTException{
	    Robot bot = new Robot();
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
}