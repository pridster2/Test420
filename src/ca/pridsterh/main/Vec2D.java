package ca.pridsterh.main;

public class Vec2D {
	public double x;
	public double y;
	
	public Vec2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(Vec2D vector2) {
		this.x += vector2.x;
		this.y += vector2.y;
	}
}