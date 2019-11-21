package ca.pridsterh.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Menu implements UsesKeys {
	private final String keyUp = "UP";
	private final String keyDown = "DOWN";
	private final String keySpace = "SPACE";
	
	private boolean keyUpHeld = false;
	private boolean keyDownHeld = false;
	private boolean keySpaceHeld = false;
	
	private int selectedOption;
	private final int maxOptions = 5;
	
	private GraphicsContext graphics;
	
	public boolean open;
	
	private final Image buttonLight = new Image(this.getResource("/images/menu/button_light.png"));
	private final Image buttonDark = new Image(this.getResource("/images/menu/button_dark.png"));
	private final Image buttonHeld = new Image(this.getResource("/images/menu/button_held.png"));
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	public Menu(GraphicsContext newGraphics) {
		selectedOption = 0;
		this.graphics = newGraphics;
		this.open = true;
	}
	
	public void processKey(String input, String action) {
		if (action == "PRESS") {
			if (input == keyUp && !keyUpHeld) keyPressUp();
			if (input == keyDown && !keyDownHeld) keyPressDown();
			if (input.equals(keySpace) && !keySpaceHeld) keyPressSpace();
		} else if (action == "RELEASE") {
			if (input == keyUp) keyReleaseUp();
			if (input == keyDown) keyReleaseDown();
			if (input.equals(keySpace)) keyReleaseSpace();
		}
	}

	private void keyPressSpace() {
		keySpaceHeld = true;
	}
	
	private void keyReleaseSpace() {
		if (selectedOption == 2 && keySpaceHeld) {
			this.open = false;
		}
		keySpaceHeld = false;
	}

	private void keyReleaseDown() {
		keyDownHeld = false;
	}

	private void keyReleaseUp() {
		keyUpHeld = false;
	}

	private void keyPressDown() {
		keyDownHeld = true;
		selectedOption++;
		if (selectedOption >= maxOptions) {
			selectedOption = maxOptions - 1;
		}
		keySpaceHeld = false;
	}

	private void keyPressUp() {
		keyUpHeld = true;
		selectedOption--;
		if (selectedOption < 0) {
			selectedOption = 0;
		}
		keySpaceHeld = false;
	}
	
	public void print() {
		for (int i = 0; i < maxOptions; i++) {
			if (i == selectedOption) {
				if (this.keySpaceHeld) {
					graphics.drawImage(this.buttonHeld, 500, i * this.buttonHeld.getHeight() * 2 + 100);
				} else {
					graphics.drawImage(this.buttonLight, 500, i * this.buttonLight.getHeight() * 2 + 100);
				}
			} else {
				graphics.drawImage(this.buttonDark, 500, i * this.buttonDark.getHeight() * 2 + 100);
			}
		}
	}
}
