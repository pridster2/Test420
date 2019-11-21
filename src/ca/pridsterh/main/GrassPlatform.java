package ca.pridsterh.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class GrassPlatform extends Entity{
	private final Image bottom_left = new Image(getResource("/images/terrain/grass/edges/bottom_left.png"));
	private final Image bottom_middle = new Image(getResource("/images/terrain/grass/edges/bottom_middle.png"));
	private final Image bottom_right = new Image(getResource("/images/terrain/grass/edges/bottom_right.png"));
	private final Image middle_left = new Image(getResource("/images/terrain/grass/edges/middle_left.png"));
	private final Image middle_middle = new Image(getResource("/images/terrain/grass/edges/middle_middle.png"));
	private final Image middle_right = new Image(getResource("/images/terrain/grass/edges/middle_right.png"));
	private final Image top_left = new Image(getResource("/images/terrain/grass/edges/top_left.png"));
	private final Image top_middle = new Image(getResource("/images/terrain/grass/edges/top_middle.png"));
	private final Image top_right = new Image(getResource("/images/terrain/grass/edges/top_right.png"));
	
	
	private Image[] decorations;
	private final int numberDecorations = 9;
	
	private ArrayList<Pair <Integer, Integer>> decorationsStored = new ArrayList<>();
	
	public GrassPlatform(int newX, int newY, GraphicsContext newGraphics, Level newLevel, int newW, int newH) {
		super(newX, newY, newGraphics, newLevel);
		this.size = new Vec2D(newW, newH);
		initializeDecorations();
		fixWidth();
		createDecorations();
		solid = true;
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
	
	public void initializeDecorations() {
		decorations = new Image[numberDecorations];
		decorations[0] = new Image(getResource("/images/terrain/grass/decoration/flower_1.png"));
		decorations[1] = new Image(getResource("/images/terrain/grass/decoration/flower_2.png"));
		decorations[2] = new Image(getResource("/images/terrain/grass/decoration/flower_3.png"));
		decorations[3] = new Image(getResource("/images/terrain/grass/decoration/grass_1.png"));
		decorations[4] = new Image(getResource("/images/terrain/grass/decoration/grass_2.png"));
		decorations[5] = new Image(getResource("/images/terrain/grass/decoration/rock_1.png"));
		decorations[6] = new Image(getResource("/images/terrain/grass/decoration/grass_1.png"));
		decorations[7] = new Image(getResource("/images/terrain/grass/decoration/grass_2.png"));
		decorations[8] = new Image(getResource("/images/terrain/grass/decoration/grass_1.png"));
	}
	
	public void fixWidth() {
		if (size.x % 4 != 0) {
			size.x += (4 - (size.x % 4));
		}
	}
	
	public void createDecorations() {
		int i = 0;
		int j = 0;
		while (i < size.x) {
			Random random = new Random((int)(-i + position.x*10 + position.y*100 + size.x*1000));
			i += Math.abs(random.nextInt()) % (int)(size.x / 64) + 1;
			i *= 4;
			j = Math.abs(random.nextInt()) % numberDecorations;
			
			if (i >= size.x) break;
			decorationsStored.add(new Pair<Integer, Integer>(i, j));
		}
	}
	
	public void draw(double xShift) {
//		//draw decorations
//		for (int i = 0; i < decorationsStored.size(); i++) {
//			int index = decorationsStored.get(i).getValue();
//			if (decorationsStored.get(i).getKey() + decorations[index].getWidth() > size.x) {
//				graphics.drawImage(decorations[index], position.x + size.x - decorations[index].getWidth() - xShift, position.y - decorations[index].getHeight());
//			} else {
//				graphics.drawImage(decorations[index], position.x + decorationsStored.get(i).getKey() - xShift, position.y - decorations[index].getHeight());
//			}
//		}
		
		//draw platform
		double xN = position.x - xShift;
		
		//draw middle pieces
		for (int i = 4; i + middle_middle.getWidth() < size.x; i += middle_middle.getWidth()) {
			for (int j = 0; j + middle_middle.getHeight() < size.y; j += middle_middle.getHeight()) {
				graphics.drawImage(middle_middle, xN + i, position.y + j);
			}
		}
		
		//draw edges
		for (int i = 4; i + top_middle.getWidth() < size.x; i+= top_middle.getWidth()) {
			graphics.drawImage(top_middle, xN + i, position.y);
		}
		
		for (int i = 8; i + bottom_middle.getWidth() + 4 < size.x; i+= bottom_middle.getWidth()) {
			graphics.drawImage(bottom_middle, xN + i, position.y + size.y - bottom_middle.getHeight());
		}
		
		for (int i = 0; i + middle_left.getHeight() + 4 < size.y; i+= middle_left.getHeight()) {
			graphics.drawImage(middle_left, xN, position.y + i);
		}
		
		for (int i = 0; i + middle_right.getHeight() + 4 < size.y; i+= middle_right.getHeight()) {
			graphics.drawImage(middle_right, xN + size.x - middle_right.getWidth(), position.y + i);
		}
		
		//draw corners
		graphics.drawImage(top_left, xN, position.y);
		graphics.drawImage(top_right, xN + size.x - top_right.getWidth(), position.y);
		graphics.drawImage(bottom_right, xN + size.x - bottom_right.getWidth(), position.y + size.y - bottom_right.getHeight());
		graphics.drawImage(bottom_left, xN, position.y + size.y - bottom_left.getHeight());
		
	}
}
