package ca.pridsterh.main;

//import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Entity {
	protected Vec2D position;
	protected Vec2D size;
	protected Vec2D velocity;
	protected Vec2D acceleration;
	protected Vec2D vCap;
	
	protected double friction;
	protected double gravity;
	protected GraphicsContext graphics;
	private final Image defaultImage = new Image(this.getResource("/images/error.png"));
	public boolean falling;
	protected boolean alive;
	public boolean isDead() { return !alive; }
	
	boolean solid = false;
	
	protected Level homeLevel;
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}

	public Vec2D getPosition() {return position;}
	public Vec2D getSize() {return size;}
	public Vec2D getVelocity() {return velocity;}
	public Vec2D getAcceleration() {return acceleration;}
	public Vec2D getVCap() {return vCap;}
	
	public double getFriction() {return friction;}
	public double getGravity() {return gravity;}
	
	public Entity(int newX, int newY, GraphicsContext newGraphics, Level newLevel) {
		graphics = newGraphics;
		
		this.position = new Vec2D(newX, newY);
		this.velocity = new Vec2D(0, 0);
		this.acceleration = new Vec2D(0, 0);
		this.size = new Vec2D(defaultImage.getWidth(), defaultImage.getHeight());
		this.vCap = new Vec2D(0, 0);
		
		homeLevel = newLevel;
		falling = false;
		alive = true;
	}
	
	public void move() {
		velocity.add(acceleration);
		
		if(velocity.x > vCap.x) {velocity.x = vCap.x;}
		if(velocity.x < -vCap.x) {velocity.x = -vCap.x;}
		if(velocity.y > vCap.y) {velocity.y = vCap.y;}
		if(velocity.y < -vCap.y) {velocity.y = -vCap.y;}
		
		//change the friction code
		if (acceleration.x == 0) {
			if (velocity.x > 0) velocity.x -= friction;
			if (velocity.x < 0) velocity.x += friction;
		}
		//-------

		position.add(velocity);
	}
	
	protected Image getImage() {
		return defaultImage;
	}
	
	public void draw(double xShift) {
		graphics.drawImage(getImage(), position.x - xShift, position.y);
	}
	
	public void collideWith(Entity entity) {
		return;
	}
}