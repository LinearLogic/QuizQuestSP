package ss.linearlogic.quizquest;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class InputHandler extends JFrame implements KeyListener {
	
	public static void run() {
		new InputHandler();
	}
	
	public InputHandler() {
		this.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}