package ca.pridsterh.main;

import javafx.application.Application;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
//import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private String getResource(String loc) {
		return this.getClass().getResource(loc).toExternalForm();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		int canvasHeight = 1024, canvasWidth = 1536;
		
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext graphics = canvas.getGraphicsContext2D();
		Pane root = new Pane();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("v0.1");
		stage.show();
		stage.getIcons().add(new Image(getResource("/images/icon.png")));
		
		//Image errorImage = new Image(getResource("/images/error.png"));
		Image blackMist = new Image(getResource("/images/background/Black_Mist.png"));
		Image whiteMist = new Image(getResource("/images/background/White_Mist.png"));
		Image deathMessage = new Image(getResource("/images/death.png"));
		Image win = new Image(getResource("/images/win.png"));
		
	    new Thread() {
	        public void run() {
	        	while (stage.isShowing()) {
	        		KeyHandler god = new KeyHandler();
	        		Level openLevel = new Level(graphics);
	        		Player player = new Player(500, 0, graphics, openLevel);
	        		
	        		
	        		scene.setOnKeyPressed(e -> god.redirectKey(e.getCode().toString(), "PRESS"));
	        		scene.setOnKeyReleased(e -> god.redirectKey(e.getCode().toString(), "RELEASE"));
	        			        		
	        		Menu menu = new Menu(graphics);
	        		god.setObject(menu);
	        		while (menu.open && stage.isShowing()) {
	        			graphics.clearRect(0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());

	        			menu.print();
	        			try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							System.out.println("A problem arose with the thread...");
						}
	        		}
	        		god.setObject(player);	    
	        		while(!player.isDead() && stage.isShowing() && openLevel.inProgress) {
	        			graphics.clearRect(0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
			        	
			        	player.move();
			        	openLevel.moveAll();
			            		            
			        	int xShift = (int)(player.getPosition().x - canvasWidth/2 + player.getSize().x/2);
			        	int xRight = (int)(player.getPosition().x + canvasWidth/2 + player.getSize().x/2);
			            			        	
			            openLevel.drawAll(xShift, xRight);
			            player.draw(xShift);
			            
			            try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							System.out.println("A problem arose with the thread...");
						}
			            
			            if (player.isDead()) {
			            	openLevel.drawAll(xShift, xRight);
			            	player.draw(xShift);
			            	for (int i = 0; i < 30; i++) {
			            		graphics.drawImage(blackMist, 0, 0);
			            		graphics.drawImage(deathMessage, canvasWidth/2 - deathMessage.getWidth()/2, canvasHeight/2 - deathMessage.getHeight()/2);
			            		try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
									System.out.println("A problem arose with the thread...");
								}
			            	}
			            }
			            
			            if (!openLevel.inProgress) {
			            	openLevel.drawAll(xShift, xRight);
			            	player.draw(xShift);
			            	for (int i = 0; i < 50; i++) {
			            		graphics.drawImage(whiteMist, 0, 0);
			            		graphics.drawImage(win, canvasWidth/2 - win.getWidth()/2, canvasHeight/2 - win.getHeight()/2);
			            		try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
									System.out.println("A problem arose with the thread...");
								}
			            	}
			            }
	        		}
	        		/** MENU HERE PLEASE */
	        		try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						System.out.println("A problem arose with the thread...");
					}
	        	}
	           
	        }
	    }.start();
	}
}