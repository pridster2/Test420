package ca.pridsterh.main.menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Button {
	final Image selected;
	final Image regular;
	final Image pressed;
	private int x, y;
	private GraphicsContext gc;
	private boolean isSelected = false;
	private boolean isPressed = false;

	//private boolean isCow = true;
	final Image cow = new Image(this.getResource("/images/menu/cow.jpg"));

	public Button(String sel, String reg, String pre, int x, int y, GraphicsContext gc) {
		this.x = x;
		this.y = y;
		this.gc = gc;
		selected = new Image(this.getResource(sel));
		regular = new Image(this.getResource(reg));
		pressed = new Image(this.getResource(pre));
	}
	
	public void draw(int mouseX, int mouseY) {
		this.isSelected = this.isWithin(mouseX, mouseY);
		gc.drawImage(this.getImage(), x, y);
	}
	
	public Image getImage() {
		if(isSelected && isPressed)
			return pressed;
		else if (isSelected)
			return selected;
		else 
			return regular;
	}
	
	public boolean isWithin(int mouseX, int mouseY) {
		isPressed = false;
		
		if((mouseX > this.x) && (this.x + this.getImage().getWidth() > mouseX) &&
				(mouseY > this.y) && (this.y + this.getImage().getHeight() > mouseY)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void onPress(int mX, int mY) {
		if(!this.isWithin(mX, mY))
			return;
		
		isPressed = true;
		
		//this.drawCow();
		this.draw(mX, mY);
	}
	
//	public void drawCow() {
//		if(isCow) {
//			gc.drawImage(cow, 0, 0);
//		} else {
//			gc.clearRect(0, 0, 1000, 700);
//		}
//		
//		isCow = !isCow;
//	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
}
