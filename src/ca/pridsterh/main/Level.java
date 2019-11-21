package ca.pridsterh.main;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Level {
	private ArrayList<Entity> levelObjects = new ArrayList<>();
	private ArrayList<BackgroundImage> backgroundImages = new ArrayList<>();
	
	public boolean inProgress = true;
	
	GraphicsContext graphics;
	
	public Level(GraphicsContext newGraphics) {
		levelObjects.add(new GrassPlatform(0, 500, newGraphics, this, 640, 192));
		levelObjects.add(new GrassPlatform(1000, 500, newGraphics, this, 192, 192));
		levelObjects.add(new GrassPlatform(1800, 500, newGraphics, this, 960, 192));
		levelObjects.add(new GrassPlatform(2860, 500, newGraphics, this, 960, 192));
		levelObjects.add(new GrassPlatform(3420, 850, newGraphics, this, 480, 192));
		levelObjects.add(new GrassPlatform(4200, 850, newGraphics, this, 960, 192));
		
		levelObjects.add(new ExitDoor(5000, 722, newGraphics, this));
		
		levelObjects.add(new Snail(2280, 436, newGraphics, this, "YELLOW", 400));
		levelObjects.add(new Snail(3060, 436, newGraphics, this, "RED", 180));
		levelObjects.add(new Snail(4700, 786, newGraphics, this, "BLUE", 300));
		
		levelObjects.add(new FlyingPot(2500, 350, newGraphics, this, 300));
		levelObjects.add(new FlyingPot(800, 300, newGraphics, this, 100));
		levelObjects.add(new FlyingPot(3600, 750, newGraphics, this, 80));
		levelObjects.add(new FlyingPot(5000, 750, newGraphics, this, 100));
		levelObjects.add(new FlyingPot(4800, 750, newGraphics, this, 150));
		
		levelObjects.add(new HeartPickup(3500, 750, newGraphics, this));
		
		backgroundImages.add(new BackgroundImage(new Image(getResource("/images/background/sky/gradient_sky_2.png")), 0d));
		//backgroundImages.add(new BackgroundImage("resources/trash/test.txt", 0d));
		//backgroundImages.add(new BackgroundImage(this.getResource("/images/background/sky/stuff/"), 0d));
		backgroundImages.add(new BackgroundImage(new Image(getResource("/images/background/sky/sun.png")), 0d));
		backgroundImages.add(new BackgroundImage(new Image(getResource("/images/background/mountains/mountain_1.png")), (1/16d)));
		
		this.graphics = newGraphics;
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	public void moveAll() {
		for (int i = 0; i < levelObjects.size(); i++) {
			levelObjects.get(i).move();
			if (levelObjects.get(i).isDead()) {
				levelObjects.remove(i);
				i--;
			}
		}
	}
	
	public void drawAll(double xShift, double xRight) {
		//draw each background image		
		for (BackgroundImage bgImage: backgroundImages) {
			Image image = bgImage.getImage();
			double invDistance = bgImage.getDistanceInverse();
			int drawLocation = (int) -((xShift*invDistance)%image.getWidth());
			graphics.drawImage(image, drawLocation, 0);
			if (drawLocation > 0) {
				graphics.drawImage(image, drawLocation - image.getWidth(), 0);
			}
			if (drawLocation + image.getWidth() < xRight) {
				graphics.drawImage(image, drawLocation + image.getWidth(), 0);
			}
		}
		
		//draw each object from within the level
		for (Entity e: levelObjects) {
			if ((e.getPosition().x >= xShift && e.getPosition().x <= xRight) || (e.getPosition().x + e.getSize().x >= xShift && e.getPosition().x + e.getSize().x <= xRight) || (e.getPosition().x <= xShift && e.getPosition().x + e.getSize().x >= xRight)) {
				e.draw(xShift);
			}
		}
	}
	
	public ArrayList<Entity> findAtPositionList(Entity obj1) {
		ArrayList<Entity> entitiesFound = new ArrayList<>();
		for (int i = 0; i < levelObjects.size(); i++) {
			Entity obj2 = levelObjects.get(i);
			if (!((obj1.position.x > obj2.position.x + obj2.size.x || obj2.position.x > obj1.position.x + obj1.size.x) || (obj1.position.y > obj2.position.y + obj2.size.y || obj2.position.y > obj1.position.y + obj1.size.y))) {
				if (obj2 != obj1) {
					entitiesFound.add(obj2);
				}
			}
		}
		return entitiesFound;
	}
	
	public void completeLevel() {
		inProgress = false;
	}
	
	public void addToLevel(Entity entity) {
		levelObjects.add(entity);
	}
}