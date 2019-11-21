package ca.pridsterh.main;

//import java.io.File;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Player extends Entity implements UsesKeys {
	private final String keyUp = "UP";
	private final String keyDown = "DOWN";
	private final String keyLeft = "LEFT";
	private final String keyRight = "RIGHT";
	private final String keyEsc = "ESCAPE";
	
	private boolean keyUpHeld = false;
	private boolean keyDownHeld = false;
	private boolean keyLeftHeld = false;
	private boolean keyRightHeld = false;
	private boolean keyEscHeld = false;
	
	private final Image fallingLeft = new Image(this.getResource("/images/player/stickman/falling_left.png"));
	private final Image fallingRight = new Image(this.getResource("/images/player/stickman/falling_right.png"));
	private final Image jumpingLeft = new Image(this.getResource("/images/player/stickman/jumping_left.png"));
	private final Image jumpingRight = new Image(this.getResource("/images/player/stickman/jumping_right.png"));
	private final Image runningLeft1 = new Image(this.getResource("/images/player/stickman/running_left_1.png"));
	private final Image runningLeft2 = new Image(this.getResource("/images/player/stickman/running_left_2.png"));
	private final Image runningRight1 = new Image(this.getResource("/images/player/stickman/running_right_1.png"));
	private final Image runningRight2 = new Image(this.getResource("/images/player/stickman/running_right_2.png"));
	private final Image slidingLeft = new Image(this.getResource("/images/player/stickman/sliding_left.png"));
	private final Image slidingRight = new Image(this.getResource("/images/player/stickman/sliding_right.png"));
	private final Image standingLeft = new Image(this.getResource("/images/player/stickman/standing_left.png"));
	private final Image standingRight = new Image(this.getResource("/images/player/stickman/standing_right.png"));
	private final Image turningLeft = new Image(this.getResource("/images/player/stickman/turning_left.png"));
	private final Image turningRight = new Image(this.getResource("/images/player/stickman/turning_right.png"));
	
	private final Image hitBox = new Image(this.getResource("/images/player/stickman/hitbox.png"));
	
	private final Image rEdge = new Image(this.getResource("/images/player/overlay/r_edge.png"));
	private final Image lEdge = new Image(this.getResource("/images/player/overlay/l_edge.png"));
	private final Image sliver = new Image(this.getResource("/images/player/overlay/sliver.png"));
	private final Image sliverEmpty = new Image(this.getResource("/images/player/overlay/sliver_empty.png"));
	
	private int facing = 1;
	private int frameCounter = 0;
	private final int maxHealth = 100;
	private int health = maxHealth;
	
	int iFrames = 0;
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	private void playSound(MediaPlayer soundEffect, double volume) {
		soundEffect.setVolume(volume);
		soundEffect.seek(new Duration(0));
		soundEffect.play();
	}
	
	private final MediaPlayer clickSound = new MediaPlayer(new Media(this.getResource("/audio/Click.wav")));
	
	public Player(int newX, int newY, GraphicsContext newGraphics, Level newLevel) {
		super(newX, newY, newGraphics, newLevel);
		vCap = new Vec2D(15, 30);
		
		friction = 0.5;
		gravity = 1.5;
		falling = true;
		
		this.playSound(clickSound, 0);
	}
	
	protected Image getImage() {
		Image image;
		
		if (velocity.x < 0) facing = -1;
		if (velocity.x > 0) facing = 1;
		
		if (frameCounter >= 20) frameCounter = 0;
		frameCounter++;
		
		image = standingRight;
		
		if (facing == -1) {
			if (!falling) {
				if (velocity.x == 0 && velocity.y == 0 && !falling) image = standingLeft;
				
				if (velocity.x < 0 && frameCounter < 10) image = runningLeft1;
				if (velocity.x < 0 && frameCounter > 9) image = runningLeft2;
				if (velocity.x < 0 && !keyLeftHeld) image = slidingLeft;
				if (velocity.x < 0 && keyRightHeld) {
					image = turningLeft;
					facing = 1;
				}
			}
			else {
				image = fallingLeft;
				if (velocity.y < 0) image = jumpingLeft;
			}
			
			
		} else if (facing == 1) {
			if (!falling) {
				if (velocity.x == 0 && velocity.y == 0) image = standingRight;
				
				if (velocity.x > 0 && frameCounter < 10) image = runningRight1;
				if (velocity.x > 0 && frameCounter > 9) image = runningRight2;
				if (velocity.x > 0 && !keyRightHeld) image = slidingRight;
				if (velocity.x > 0 && keyLeftHeld) {
					image = turningRight;
					facing = -1;
				}
			}
			else {
				image = fallingRight;
				if (velocity.y < 0) image = jumpingRight;
			}
		}
		return image;
	}
	
	public void processKey(String input, String action) {
		if (action == "PRESS") {
			if (input == keyUp && !keyUpHeld) keyPressUp();
			if (input == keyDown && !keyDownHeld) keyPressDown();
			if (input == keyLeft && !keyLeftHeld) keyPressLeft();
			if (input == keyRight && !keyRightHeld) keyPressRight();
			if (input == keyEsc && !keyEscHeld) keyPressEsc();
		}
		else if (action == "RELEASE") {
			if (input == keyUp) keyReleaseUp();
			if (input == keyDown) keyReleaseDown();
			if (input == keyLeft) keyReleaseLeft();
			if (input == keyRight) keyReleaseRight();
			if (input == keyEsc) keyReleaseEsc();
		}
	}
	
	private void keyPressEsc() {
		keyEscHeld = true;
		this.homeLevel.completeLevel();
	}
	
	private void keyReleaseEsc() {
		keyEscHeld = false;
	}
	
	private void keyPressUp() {
		keyUpHeld = true;
		
		if (!this.falling) {
			velocity.y = -50;
			position.y -= 5;
			
			this.playSound(clickSound, 0.5);
			falling = true;
		}
	}
	
	private void keyPressDown() {
		keyDownHeld = true;
		
		ArrayList<Entity> collidingEntities = homeLevel.findAtPositionList(this);
		for (Entity e: collidingEntities) {
			this.collideWith(e);
		}
	}

	private void keyPressLeft() {
		keyLeftHeld = true;
		acceleration.x -= 1;
	}

	private void keyPressRight() {
		keyRightHeld = true;
		acceleration.x += 1;
	}
	
	private void keyReleaseUp() {
		keyUpHeld = false;
	}
	
	private void keyReleaseDown() {
		keyDownHeld = false;
	}
	
	private void keyReleaseLeft() {
		keyLeftHeld = false;
		acceleration.x += 1;
	}
	
	private void keyReleaseRight() {
		keyRightHeld = false;
		acceleration.x -= 1;
	}
	
	@Override
	public void move() {
		ArrayList<Entity> collidingEntities;
		
		if (iFrames > 0) iFrames--;
		
//		size.x = getImage().getWidth();
//		size.y = getImage().getHeight();
		size.x = hitBox.getWidth();
		size.y = hitBox.getHeight();
		
		position.y++;
		
		collidingEntities = homeLevel.findAtPositionList(this);
		
		falling = true;
		for (Entity e: collidingEntities) {
			if (e.solid) {
				falling = false;
				break;
			}
		}
		position.y--;
		
		
		if (this.falling) {
			acceleration.y = gravity;
		}
		else {
			acceleration.y = 0;
			velocity.y = 0;
		}
		
		super.move();

		if (position.y > graphics.getCanvas().getHeight()) {
			this.health = 0;
			this.alive = false;
		}
		
		collidingEntities = homeLevel.findAtPositionList(this);
		for (Entity e: collidingEntities) {
			e.collideWith(this);
			
			if (!e.solid) continue;
			
			Vec2D netV = new Vec2D(velocity.x - e.velocity.x, velocity.y - e.velocity.y);
			
			if (netV.x > 0 && this.position.x + this.size.x - e.position.x <= this.velocity.x) {
				this.position.x = e.position.x - this.size.x - 1;
				velocity.x = 0;
			}
			else if (netV.x < 0 && this.position.x - e.position.x - e.size.x >= this.velocity.x) {
				this.position.x = e.position.x + e.size.x + 1;
				velocity.x = 0;
			}
			//maybe not else in next line
			else if (netV.y > 0 && this.position.y + this.size.y - e.position.y <= this.velocity.y) {
				this.position.y = e.position.y - this.size.y;
				velocity.y = 0;
			}
			else if (netV.y < 0 && this.position.y - e.position.y - e.size.y >= this.velocity.y) {
				this.position.y = e.position.y + e.size.y;
				velocity.y = 0;
			}
		}
	}
	
	@Override
	public void draw(double xShift) {
		if (iFrames % 2 == 0 || iFrames % 3 == 0) {
			//graphics.drawImage(hitBox, position.x - xShift, position.y);
			super.draw(xShift - hitBox.getWidth()/2 + this.getImage().getWidth()/2);
		}
		
		Vec2D healthOrigin = new Vec2D(10, 10);
		
		graphics.drawImage(lEdge, healthOrigin.x, healthOrigin.y);
		healthOrigin.x += lEdge.getWidth();
		
		for (int i = 0; i < maxHealth; i++) {
			if (i < health) {
				graphics.drawImage(sliver, healthOrigin.x + (i * sliver.getWidth()), healthOrigin.y);
			} else {
				graphics.drawImage(sliverEmpty, healthOrigin.x + (i * sliver.getWidth()), healthOrigin.y);
			}
			
		}
		
		graphics.drawImage(rEdge, healthOrigin.x + sliver.getWidth() * maxHealth, healthOrigin.y);
	}
	
	public void heal(int healthAmount) {
		health += healthAmount;
		if (health > maxHealth) health = maxHealth;
	}
	
	public void damage(int damageAmount) {
		if (iFrames == 0) {
			health -= damageAmount;
			if (health <= 0) {
				health = 0;
				alive = false;
			}
			iFrames = 60;

			velocity.x = 0; velocity.y = -10; position.y -= 10;
		}
	}
	
	public void setFriction(double newFriction) {
		this.friction = newFriction;
	}
	
	public void collideWith(Entity entity) {
		if (entity instanceof ExitDoor) {
			((ExitDoor) entity).use();
		}
		return;
	}
}