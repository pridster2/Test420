package ca.pridsterh.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import java.io.File;


public class Snail extends Entity {
	
	private String colour;
	
	private int spawnX;
	private int wanderDistance;
	
	private int imageCounter;
	
	private final Image right1Blue = new Image(getResource("/images/snail/blue/right_1.png"));
	private final Image right1Red = new Image(getResource("/images/snail/red/right_1.png"));
	private final Image right1Yellow = new Image(getResource("/images/snail/yellow/right_1.png"));
	private final Image right1Green = new Image(getResource("/images/snail/green/right_1.png"));
	private final Image right2Blue = new Image(getResource("/images/snail/blue/right_2.png"));
	private final Image right2Red = new Image(getResource("/images/snail/red/right_2.png"));
	private final Image right2Yellow = new Image(getResource("/images/snail/yellow/right_2.png"));
	private final Image right2Green = new Image(getResource("/images/snail/green/right_2.png"));
	private final Image left1Blue = new Image(getResource("/images/snail/blue/left_1.png"));
	private final Image left1Red = new Image(getResource("/images/snail/red/left_1.png"));
	private final Image left1Yellow = new Image(getResource("/images/snail/yellow/left_1.png"));
	private final Image left1Green = new Image(getResource("/images/snail/green/left_1.png"));
	private final Image left2Blue = new Image(getResource("/images/snail/blue/left_2.png"));
	private final Image left2Red = new Image(getResource("/images/snail/red/left_2.png"));
	private final Image left2Yellow = new Image(getResource("/images/snail/yellow/left_2.png"));
	private final Image left2Green = new Image(getResource("/images/snail/green/left_2.png"));
	
	public Snail(int newX, int newY, GraphicsContext newGraphics, Level newLevel, String newColour, int newWanderDistance) {
		super(newX, newY, newGraphics, newLevel);
		
		colour = newColour;
		velocity.x = 1;
		vCap.x = 10;
		spawnX = newX;
		wanderDistance = newWanderDistance;
		solid = false;
		
		gravity = 0.2;
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	protected Image getImage() {
		
		imageCounter++;
		if (imageCounter >= 29) {
			imageCounter = 0;
		}
		
		if (colour == "BLUE") {
			if (velocity.x > 0) {
				if (imageCounter < 15) {
					return right1Blue;
				} else {
					return right2Blue;
				}
			} else {
				if (imageCounter < 15) {
					return left1Blue;
				} else {
					return left2Blue;
				}
			}
		} else if (colour == "RED") {
			if (velocity.x > 0) {
				if (imageCounter < 15) {
					return right1Red;
				} else {
					return right2Red;
				}
			} else {
				if (imageCounter < 15) {
					return left1Red;
				} else {
					return left2Red;
				}
			}
		} else if (colour == "YELLOW") {
			if (velocity.x > 0) {
				if (imageCounter < 15) {
					return right1Yellow;
				} else {
					return right2Yellow;
				}
			} else {
				if (imageCounter < 15) {
					return left1Yellow;
				} else {
					return left2Yellow;
				}
			}
		}
		else if (colour == "GREEN") {
			if (velocity.x > 0) {
				if (imageCounter < 15) {
					return right1Green;
				} else {
					return right2Green;
				}
			} else {
				if (imageCounter < 15) {
					return left1Green;
				} else {
					return left2Green;
				}
			}
		} else return right1Blue;
	}
	
	public void move() {
		size.x = getImage().getWidth();
		size.y = getImage().getHeight();
		
		if (Math.abs(position.x - spawnX) >= wanderDistance) {
			velocity.x *= -1;
		}
		super.move();
	}
	
	public void collideWith(Entity entity) {
		if (entity instanceof Player) {
			((Player) entity).damage(34);
		}
		return;
	}
}