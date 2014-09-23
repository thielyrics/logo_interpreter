package turtle;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar {
	final static String path = "C:\\Users\\Kaleigh\\My Documents\\Programming Practicum\\IDE\\";

	private int speed;
	private TurtlePane pane;
	private JSlider slider;
	private static ManageCanvas manager;
	private JButton left, right, up, down;
	private JButton home, pen, clear, turtle;
	
	
	public ToolBar(TurtlePane p) {
		super();
		
		speed = 1;
		pane = p;
		manager = pane.getManager();
		addButtons();
		
		slider = new JSlider(1, 10, 5);
		slider.add(new JLabel("Speed"));
		slider.setBorder(BorderFactory.createTitledBorder("Speed"));
		slider.setSnapToTicks(true);
		add(slider);
		setFloatable(false);
		slider.addChangeListener(new ChangeListener() { 
			public void stateChanged(ChangeEvent e) { 
				manager.changeSpeed(slider.getValue());
			}
		});
		
	}
	
	private JButton makeTurnButton(ImageIcon icon, String tooltip, double d) {
		JButton button = makeButton(icon, tooltip);

		final double turn = d;
    	button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.changeHeading(turn);
				pane.repaint();
			}
    	});
		
		return button;
	}
	
	private JButton makeGoButton(ImageIcon icon, String tooltip, int i) {
		JButton button = makeButton(icon, tooltip);

		final int sign = i;
    	button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.makeMove(sign*speed);
				pane.repaint();
			}
    	});
		
		return button;
	}
	
	private JButton makeButton(ImageIcon icon, String tooltip) {
		JButton button = new JButton(icon);
		button.setToolTipText(tooltip);
		return button;
	}
	
	private void addButtons() {
		left = makeTurnButton(new ImageIcon(path + "left.png"), "Move left.", -1*10*speed*(Math.PI/128));
		right = makeTurnButton(new ImageIcon(path + "right.png"), "Move right.", 10*speed*(Math.PI/128));
		up = makeGoButton(new ImageIcon(path + "up.png"), "Move up.", 1);
		down = makeGoButton(new ImageIcon(path + "down.png"), "Move down.", -1);
		
		makeTurtleButton();
		makePenButton();
		makeClearButton();
		makeHomeButton();
		
		add(left);
		add(down);
		add(up);
		add(right);
		add(home);
		add(pen);
		add(clear);
		add(turtle);
	}
	
	private void makeTurtleButton() { 
		turtle = makeButton(new ImageIcon(path + "no-turtle.png"), "Hide turtle.");
		turtle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(manager.turtleShowing()) { manager.hideTurtle(); }
				else { manager.showTurtle(); } 
				pane.repaint();
			}
		});
	}
	
	public void updateTurtle(Boolean showing) { 
		if(!showing) { 
			turtle.setIcon(new ImageIcon(path + "turtle.png"));
			turtle.setToolTipText("Show turtle.");
		} else {
			turtle.setIcon(new ImageIcon(path + "no-turtle.png"));
			turtle.setToolTipText("Hide turtle.");
		}
	}
	
	private void makePenButton() { 
		pen = makeButton(new ImageIcon(path + "no-pen.png"), "Hide trail.");
		pen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(manager.penShowing()) { manager.penUp(); }
				else { manager.penDown(); }
				pane.repaint();
			}
		});
	}
	
	public void updatePen(Boolean showing) {
		if(!showing) { 
			pen.setIcon(new ImageIcon(path + "pen.png"));
			pen.setToolTipText("Show trail.");
		} else {
			pen.setIcon(new ImageIcon(path + "no-pen.png"));
			pen.setToolTipText("Hide trail.");
		}
	}
	
	private void makeClearButton() {
		clear = makeButton(new ImageIcon(path + "clear.png"), "Clear canvas.");
		clear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.clear();
				manager.sendHome();
				pane.repaint();
			}
		});
	}
	
	private void makeHomeButton() {
		home = makeButton(new ImageIcon(path + "home.png"), "Move turtle home.");
		home.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.sendHome();
				pane.repaint();
			}
		});
	}
}
