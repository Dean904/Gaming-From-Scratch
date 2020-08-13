package display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;


public class Display {

	private JFrame frame;	// creates frame
	private Canvas canvas;	// allows us to draw graphics to a canvas
	
	// 3 core elements of a JFrame
	private String title;
	private int width, height;
	
	// Constructor
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	}
	private void createDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//OPTIONAL
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack(); //resizes frame to fit canvas
		
	}
	
	// Getters
	public Canvas getCanvas() {
		return canvas;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	
}
