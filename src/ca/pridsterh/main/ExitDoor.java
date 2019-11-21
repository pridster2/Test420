package ca.pridsterh.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ExitDoor extends Entity{
	private final Image door = new Image(getResource("/images/door.png"));
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	public ExitDoor(int newX, int newY, GraphicsContext newGraphics, Level newLevel) {
		super(newX, newY, newGraphics, newLevel);
		this.solid = false;
		this.size.x = this.getImage().getWidth();
		this.size.y = this.getImage().getHeight();
	}
	
	public void use() {
		this.homeLevel.completeLevel();
	}
	
	protected Image getImage() {
		return door;
	}

}