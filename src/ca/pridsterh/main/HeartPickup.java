package ca.pridsterh.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HeartPickup extends Entity {
	private final Image heart1 = new Image(this.getResource("/images/heartpickup/heart1.png"));
	private final Image heart2 = new Image(this.getResource("/images/heartpickup/heart2.png"));
	private final Image heart3 = new Image(this.getResource("/images/heartpickup/heart3.png"));
	private final Image heart4 = new Image(this.getResource("/images/heartpickup/heart4.png"));
	private final Image heart5 = new Image(this.getResource("/images/heartpickup/heart5.png"));
	
	private int frameCounter = 0;
	
	private boolean exists = true;
	public HeartPickup(int newX, int newY, GraphicsContext newGraphics, Level newLevel) {
		super(newX, newY, newGraphics, newLevel);
	}
	public void move() {
		this.size.x = this.getImage().getWidth();
		this.size.y = this.getImage().getHeight();
	}
	public void collideWith(Entity entity) {
		if (entity instanceof Player) {
			((Player) entity).heal(34);
			this.alive = false;
		}
		return;
	}
	public void draw(double xShift) {
		if (!this.exists) return;
		super.draw(xShift);
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	protected Image getImage() {
		frameCounter++;
		if (frameCounter <= 30) {
			return heart1;
		} else if (frameCounter <= 60) {
			return heart2;
		} else if (frameCounter <= 90) {
			return heart3;
		} else if (frameCounter <= 120) {
			return heart4;
		} else if (frameCounter <= 150) {
			return heart5;
		} else if (frameCounter <= 180) {
			return heart4;
		} else if (frameCounter <= 210) {
			return heart3;
		} else if (frameCounter <= 240) {
			return heart2;
		} else if (frameCounter <= 270) {
			return heart1;
		} else {
			frameCounter = 1;
			return heart1;
		}
	}
}