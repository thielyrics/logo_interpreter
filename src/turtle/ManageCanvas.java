package turtle;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;


public class ManageCanvas {
		private static int MAX_SPEED = 10;
		private boolean showTurtle;
		private boolean showTrail;
		private Turtle turtle;
		private ArrayList<Line2D> trail;
		private int speed;
		private TurtlePane pane;
		
	public ManageCanvas(TurtlePane tp) {
		showTurtle = true;
		showTrail = true;
		turtle = new Turtle();
		trail = new ArrayList<Line2D>();
		speed = 5;
		pane = tp;
	}
	
	public void draw(Graphics g) { 
		if(showTurtle) {
			turtle.draw(g);
		}
		drawTrail(g);
	}
	
	public void showTurtle() { 
		showTurtle = true; 
		pane.changeButton("turtle", showTurtle);
	}
	public void hideTurtle() { 
		showTurtle = false; 
		pane.changeButton("turtle", showTurtle);
	}
	public boolean turtleShowing() { return showTurtle; }
	
	public void penDown() { 
		showTrail = true; 
		pane.changeButton("pen", showTrail);
	}
	public void penUp() { 
		showTrail = false; 
		pane.changeButton("pen", showTrail);
	}
	public boolean penShowing() { return showTrail; }
	
	public void sendHome() { 
		turtle.sendHome(); 
	}
	public void clear() { 
		trail.clear();
		sendHome();
	}
	public void changeSpeed(int s) { speed = s; }
	public void changeHeading(double d) { turtle.changeHeading(d); }
	
	public void forward(int i, int sign) { 
		if(speed == MAX_SPEED) { 
			makeInstantMove(i);
		}
		else { 
			for(int j = 0; j < i/speed; j++) { 
				makeMove(sign);
			}
		}
	}
	
	public void makeMove(int sign) {
		double x, y;
		int x1, x2, y1, y2;
		
		x1 = (int)turtle.getLocation().getX();
		y1 = (int)turtle.getLocation().getY();
		
		x = sign*speed*turtle.getHeight()/10 * Math.cos(turtle.getHeading());
		y = sign*speed*turtle.getHeight()/10 * Math.sin(turtle.getHeading());
		
		turtle.translate((int)x, (int)y);
		
		x2 = (int)turtle.getLocation().getX();
		y2 = (int)turtle.getLocation().getY();
		
		if(showTrail) {
			trail.add(new Line2D.Double(x1, y1, x2, y2));
		} 
	}
	
	private void makeInstantMove(int dist) {
		double x, y;
		Point p1 = turtle.getLocation();
		x = dist * Math.cos(turtle.getHeading());
		y = dist * Math.sin(turtle.getHeading());
		
		turtle.translate((int)x, (int)y);
		Point p2 = turtle.getLocation();
		if(showTrail) {
			trail.add(new Line2D.Double(p1, p2));
		} 
	}
	
	public boolean hasTrail() { return !trail.isEmpty(); }
	private void drawTrail(Graphics g) { 
		for(int i = 0; i < trail.size(); i++) {
			Line2D line = trail.get(i);
			g.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
		}
	}
	
}
