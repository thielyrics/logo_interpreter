package turtle;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;


public class Turtle {
	private static Point origin = new Point(240, 240);
	private Point location;
	private double heading;
	private int width, height; 
	private Polygon triangle;
	
	public Turtle(Point p) {
		location = p;
		heading = 0;
		width = height = 20;
		triangle = new Polygon(getX(), getY(), getX().length);
		
	}
	
	public Turtle() { 
		location = new Point((int)origin.getX(), (int)origin.getY());
		heading = 0;
		width = height = 20;
		triangle = new Polygon(getX(), getY(), getX().length);
	}
	
	public int getHeight() { return height; } 
	public Point getLocation() { return location; }
	public double getHeading() { return heading; }
	private void updateTurtle() { triangle = new Polygon(getX(), getY(), getX().length);  } 
	public void changeHeading(double d) { setHeading(heading + d); } 
	
	public void setLocation(Point p) { 
		location = p; 
		updateTurtle();
	}
	
	public void translate(int x, int y) { 
		triangle.translate(x, y);
		location.translate(x, y);
	}
	
	public void sendHome() {
		location = new Point((int)origin.getX(), (int)origin.getY());
		heading = 0;
		triangle = new Polygon(getX(), getY(), 3);
	}

	private void setHeading(double d) { 
		heading = d; 
		updateTurtle();
	}
	
	public void draw(Graphics g) {
		 g.fillPolygon(triangle);
	}
	
	private int[] getX() {
		int[] ret = new int[3];
		ret[0] = (int)location.getX() + (int)(height * Math.cos(heading));
		ret[1] = (int)location.getX() - (int)(width/2 * Math.cos(heading - Math.PI/2));
		ret[2] = (int)location.getX() + (int)(width/2 * Math.cos(heading - Math.PI/2));
		return ret;
	}
	
	private int[] getY() {
		int[] ret = new int[3];
		ret[0] = (int)location.getY() + (int)(height * Math.sin(heading));
		ret[1] = (int)location.getY() - (int)(width/2 * Math.sin(heading - Math.PI/2));
		ret[2] = (int)location.getY() + (int)(width/2 * Math.sin(heading - Math.PI/2));
		return ret;
	}
	
}
