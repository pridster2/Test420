package ca.pridsterh.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class BackgroundImage {
	private double distanceInverse;
	private Image image;
	
	private int imageCounter = 0;
	private int imageCounterMax = 0;
	
	private ArrayList<Image> images = new ArrayList<>();
	
	public BackgroundImage(Image image, double distanceInverse) {
		this.image = image;
		this.distanceInverse = distanceInverse;
	}
	
	public BackgroundImage(String filepath, double distanceInverse) {
		this.distanceInverse = distanceInverse;
		this.imagesFromFile(filepath);
		//this.imagesFromFile2(filepath);
		this.imageCounterMax = images.size() * 5;
	}
	
	public double getDistanceInverse() {
		return this.distanceInverse;
	}
	
	public Image getImage() {
		imageCounter++;
		if (imageCounter >= imageCounterMax) imageCounter = 0;
		if (images.size() == 0) {
			return image;
		} else {
			return images.get(imageCounter/5);
		}
	}
	
	private void imagesFromFile(String filePath) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			
			String line;
			while ((line = in.readLine()) != null && line.length() != 0) {
				this.images.add(new Image(this.getResource(line)));
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error when reading file for background images.");
		} catch (Exception e) {
			System.out.println("Other error.");
		}
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}
}