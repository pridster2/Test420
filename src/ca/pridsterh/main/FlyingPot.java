package ca.pridsterh.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FlyingPot extends Entity{
	private int wanderDistance;
	private int imageCounter = 0;
	private int spawnY;
	private int spawnX;
	
	private Vec2D absAccel = new Vec2D(0, 0);
	
	private boolean falling;
	
	private final Image flying1 = new Image(getResource("/images/flyingpot/flying_1.png"));
	private final Image flying2 = new Image(getResource("/images/flyingpot/flying_2.png"));
	private final Image dead1 = new Image(getResource("/images/flyingpot/dead_1.png"));
	
	public FlyingPot(int newX, int newY, GraphicsContext newGraphics, Level newLevel, int wanderDistance) {
		super(newX, newY, newGraphics, newLevel);
		this.wanderDistance = wanderDistance;
		this.spawnY = newY;
		this.absAccel.y = 0.1;
		this.acceleration.y = -this.absAccel.y;
		this.vCap.y = 3;
		this.falling = false;
		this.gravity = 0.5;
		this.vCap.x = 3;
		this.absAccel.x = 0.5;
		this.acceleration.x = this.absAccel.x;
		this.spawnX = newX;
	}

	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	protected Image getImage() {
		imageCounter++;
		if (imageCounter >= 100)
			imageCounter = 0;
		
		if (this.falling)
			return dead1;
		
		if (imageCounter < 50)
			return flying1;
		else
			return flying2;
	}
	
	public void move() {
		size.x = getImage().getWidth();
		size.y = getImage().getHeight();
		
		if (Math.abs(position.y - spawnY) >= wanderDistance) {
			if (position.y - spawnY >= wanderDistance) {
				this.acceleration.y = -this.absAccel.y;
			} else
				this.acceleration.y = this.absAccel.y;
		}
		
		if (Math.abs(position.x - spawnX) >= wanderDistance) {
			if (position.x - spawnX >= wanderDistance) {
				this.acceleration.x = -this.absAccel.x;
			} else
				this.acceleration.x = this.absAccel.x;
		}
		
		if (this.falling) {
			this.acceleration.y = this.gravity;
			this.acceleration.x = 0;
			this.friction = 0.01;
		}
		
		super.move();
		
		if (position.y > graphics.getCanvas().getHeight()) {
			this.alive = false;
		}
	}
	
	public void collideWith(Entity entity) {
		if (entity instanceof Player) {
			((Player) entity).damage(34);
			this.falling = true;
			this.velocity.y = -10;
			this.vCap.y = 20;
		}
		return;
	}
}