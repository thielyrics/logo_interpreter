package turtle;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

@SuppressWarnings("serial")
public class TurtlePane extends JFrame {	
	private static JPanel canvas;
	private static ManageCanvas manager;
	private ToolBar toolbar;
	
	public TurtlePane() {
		super("Turtle Graphics");
		
		manager = new ManageCanvas(this);
		canvas = new JPanel() {
				public void paintComponent(Graphics g) { 
					super.paintComponent(g);
					TurtlePane.this.paintGame(g);
				}
		};
		canvas.setBackground(Color.WHITE);
		setSize(500,500);
		toolbar = new ToolBar(this);
		
		JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		jsp.setBottomComponent(toolbar);
		jsp.setTopComponent(canvas);
		jsp.setDividerLocation(420);
		add(jsp);
		
		setVisible(true);
		setLocation(500, 0);
	}
		
	public void paintGame(Graphics g) { 
		manager.draw(g);
	}
	
	public void changeButton(String button, Boolean b) { 
		if(button.equals("turtle")) { toolbar.updateTurtle(b); } 
		else if(button.equals("pen")) { toolbar.updatePen(b); }
	}
/*	
	public static void main(String[] args) {
		TurtlePane pane = new TurtlePane();
		pane.setVisible(true);
	}
*/	
	public ManageCanvas getManager() { return manager; }
}
