package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import GameEngine.*;
import ObjectPackage.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainApp extends Application
{
	final int SCENEX = 1200;
	final int SCENEY = 600;
	double mouseSceneX, mouseSceneY, mouseTranslateX, mouseTranslateY;
	KeyCode pressedKey = KeyCode.UNDEFINED;
	boolean isKeyPressed = false;
	boolean isMousePressed = false;
	ImageView choosenImage = null;
	Blocks choosenBlock = null;
	//boolean takenflag = false;
	int scale;
	
	static Stage mainStage;
	static Scene mainScene, gameScene, howToPlayScene, creditsScene, levelMenuScene, endScene;
	
	LevelManager levelManager;
	Level currentLevel;
	int numberOfCars;
	int numberOfSlot;
	ImageView[] ImageList;
	Blocks[] blockList;
	int redX;
	int redY;
	/*
	ImageView CarA2View;
	ImageView CarB1View;
	ImageView CarC1View;
	ImageView CarC2View;
	ImageView CarD1View;
	ImageView CarD2View;
	*/
	ImageView RedCar;
	
	private EventHandler<MouseEvent> backToMain = new EventHandler<MouseEvent>()
	{
		public void handle(MouseEvent e)
		{
			mainStage.setScene(mainScene);
		}
	};
	
	public void start(Stage defaultStageIgnored) throws Exception
	{
		System.out.println("Game is started.");
		mainStage = new Stage();
		
		levelManager = new LevelManager();
		
		GridPane menuPane = printMenu(SCENEX, SCENEY);
		
		Group mainRoot = new Group(menuPane);
		
		
		Line centerlineX = new Line(0, SCENEY / 2, SCENEX, SCENEY / 2);
		Line centerlineY = new Line(SCENEX / 2, 0, SCENEX / 2, SCENEY);
		mainRoot.getChildren().add(centerlineX);
		mainRoot.getChildren().add(centerlineY);
		
		
		mainScene = new Scene(mainRoot, SCENEX, SCENEY);
		
		Group howToPlayRoot = new Group(printHowToPlay(SCENEX, SCENEY));
		
		howToPlayScene = new Scene(howToPlayRoot, SCENEX, SCENEY);
		
		Group creditsRoot = new Group(printCredits(SCENEX, SCENEY));
		
		creditsScene = new Scene(creditsRoot, SCENEX, SCENEY);
		
		Group levelMenuRoot = new Group(printLevelMenu(SCENEX, SCENEY));
		
		levelMenuScene = new Scene(levelMenuRoot, SCENEX, SCENEY);
		
		mainStage.setTitle("RoadBlocker:Alpha");
		
		mainStage.setResizable(false);
		
		mainStage.setScene(mainScene);
		
		mainStage.show();
	}
	public Pane printTable(double x, double y, int sceneX, int sceneY, int scale) throws FileNotFoundException
	{
		Pane table = new Pane();
		double xOriginal = x;
		double yOriginal = y;
		BlockType[][] gameTiles = currentLevel.getTable().getTableStatus();
		currentLevel.getTable().printTable();
		
		while(y > 0)
		{
			while(x > 0)
			{
				if(gameTiles[((int)y) - 1][((int)x) - 1] == BlockType.RED)
				{
					redX = ((int)x) - 1;
					redY = ((int)y) - 1;
					Image image = new Image(new FileInputStream("resources//redCar.jpg"));
					RedCar = new ImageView(image);
					
					RedCar.setX(((sceneX / 2) - ((xOriginal / 2 + 1) * (32 / scale))) + x * (32 / scale));
					RedCar.setY(((sceneY / 2) - ((yOriginal / 2 + 1) * (32 / scale))) + y * (32 / scale));
				
					RedCar.setFitHeight(32 / scale);
					RedCar.setFitWidth(32 / scale);
				
					RedCar.setPreserveRatio(true);
				
					table.getChildren().add(RedCar);
				}
				else if(gameTiles[((int)y) - 1][((int)x) - 1] == BlockType.BLOCK)
				{
					Image image = new Image(new FileInputStream("resources//building.jpg"));
					
					ImageView imageView = new ImageView(image);
				
					imageView.setX(((sceneX / 2) - ((xOriginal / 2 + 1) * (32 / scale))) + x * (32 / scale));
					imageView.setY(((sceneY / 2) - ((yOriginal / 2 + 1) * (32 / scale))) + y * (32 / scale));
				
					imageView.setFitHeight(32 / scale);
					imageView.setFitWidth(32 / scale);
				
					imageView.setPreserveRatio(true);
				
					table.getChildren().add(imageView);
				}
				else
				{
					Image image = new Image(new FileInputStream("resources//block01.jpg"));
				
					ImageView imageView = new ImageView(image);
				
					imageView.setX(((sceneX / 2) - ((xOriginal / 2 + 1) * (32 / scale))) + x * (32 / scale));
					imageView.setY(((sceneY / 2) - ((yOriginal / 2 + 1) * (32 / scale))) + y * (32 / scale));
				
					imageView.setFitHeight(32 / scale);
					imageView.setFitWidth(32 / scale);
				
					imageView.setPreserveRatio(true);
				
					table.getChildren().add(imageView);
				}
				x--;
			}
			x = xOriginal;
			y--;
		}
		
		/*
		Image CarA2 = new Image(new FileInputStream("resources//CarA2.png"));
		Image CarB1 = new Image(new FileInputStream("resources//CarB1.png"));
		Image CarC1 = new Image(new FileInputStream("resources//CarC1.png"));
		Image CarC2 = new Image(new FileInputStream("resources//CarC2.png"));
		Image CarD1 = new Image(new FileInputStream("resources//CarD1.png"));
		Image CarD2 = new Image(new FileInputStream("resources//CarD2.png"));
		ImageView CarA2View = new ImageView(CarA2);
		ImageView CarB1View = new ImageView(CarB1);
		ImageView CarC1View = new ImageView(CarC1);
		ImageView CarC2View = new ImageView(CarC2);
		ImageView CarD1View = new ImageView(CarD1);
		ImageView CarD2View = new ImageView(CarD2);
		CarA2View.setX(((sceneX / 2)- ((xOriginal / 2) * (32 / scale))) - 136);
		CarA2View.setY((sceneY / 2)- ((yOriginal / 2) * (32 / scale)));
		CarB1View.setX(((sceneX / 2)- ((xOriginal / 2) * (32 / scale))) - 136);
		CarB1View.setY((sceneY / 2)- ((yOriginal / 2) * (32 / scale)) + 136);
		CarC1View.setX(((sceneX / 2)- ((xOriginal / 2) * (32 / scale))) - 136);
		CarC1View.setY((sceneY / 2)- ((yOriginal / 2) * (32 / scale)) + 272);
		CarC2View.setX(((sceneX / 2)- ((xOriginal / 2) * (32 / scale))));
		CarC2View.setY((sceneY / 2)- ((yOriginal / 2) * (32 / scale)) + 272);
		CarD1View.setX(((sceneX / 2)- ((xOriginal / 2) * (32 / scale))) + 136);
		CarD1View.setY((sceneY / 2)- ((yOriginal / 2) * (32 / scale)) + 272);
		CarD2View.setX(((sceneX / 2)- ((xOriginal / 2) * (32 / scale))) + 272);
		CarD2View.setY((sceneY / 2)- ((yOriginal / 2) * (32 / scale)) + 272);
		CarA2View.setFitHeight(96 / scale);
		CarA2View.setFitWidth(96 / scale);
		CarB1View.setFitHeight(96 / scale);
		CarB1View.setFitWidth(96 / scale);
		CarC1View.setFitHeight(96 / scale);
		CarC1View.setFitWidth(96 / scale);
		CarC2View.setFitHeight(96 / scale);
		CarC2View.setFitWidth(96 / scale);
		CarD1View.setFitHeight(64 / scale);
		CarD1View.setFitWidth(64 / scale);
		CarD2View.setFitHeight(64 / scale);
		CarD2View.setFitWidth(64 / scale);
		CarA2View.setPreserveRatio(true);
		CarA2View.setCursor(Cursor.HAND);
		CarB1View.setPreserveRatio(true);
		CarB1View.setCursor(Cursor.HAND);
		CarC1View.setPreserveRatio(true);
		CarC1View.setCursor(Cursor.HAND);
		CarC2View.setPreserveRatio(true);
		CarC2View.setCursor(Cursor.HAND);
		CarD1View.setPreserveRatio(true);
		CarD1View.setCursor(Cursor.HAND);
		CarD2View.setPreserveRatio(true);
		CarD2View.setCursor(Cursor.HAND);
		*/
		
		EventHandler<MouseEvent> mousePressedOnCarHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				System.out.println("Mouse x: " + e.getSceneX() + " y: " + e.getSceneY());
				mouseSceneX = e.getSceneX();
				mouseSceneY = e.getSceneY();
				mouseTranslateX = ((ImageView)(e.getSource())).getTranslateX();
				mouseTranslateY = ((ImageView)(e.getSource())).getTranslateY();
				((ImageView)(e.getSource())).toFront();
				choosenImage = (ImageView)(e.getSource());
				int temp = 0;
				while(temp < numberOfCars)
				{
					if(choosenImage.equals(ImageList[temp]))
					{
						choosenBlock = blockList[temp];
						if((mouseSceneX >= ((SCENEX / 2) - ((xOriginal / 2) * (32 / scale)))) && (mouseSceneX <= ((SCENEX / 2) + ((xOriginal / 2) * (32 / scale)))) && (mouseSceneY <= ((SCENEY / 2) + ((yOriginal / 2) * (32 / scale)))) && (mouseSceneY >= ((SCENEY / 2) - ((yOriginal / 2) * (32 / scale)))))
						{
							MainEngine.TakeBlock(currentLevel.getTable(), choosenBlock);
						}
					}
					temp++;
				}
				isMousePressed = true;
			}
		};
		EventHandler<MouseEvent> mouseDraggedCarHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				double offsetX = e.getSceneX() - mouseSceneX;
				double offsetY = e.getSceneY() - mouseSceneY;
				double newTranslateX = mouseTranslateX + offsetX;
				double newTranslateY = mouseTranslateY + offsetY;
				System.out.println("Translate X: " + newTranslateX + " Y: " + newTranslateY);
				((ImageView)(e.getSource())).setTranslateX(newTranslateX);
				((ImageView)(e.getSource())).setTranslateY(newTranslateY);
			}
		};
		EventHandler<MouseEvent> mouseReleasedOnCarHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				//System.out.println((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) + "x" + (-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))));
				//if((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) >= -((32 / scale) / 2) &&
				//		(-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) <= (((xOriginal) * (32 /scale)) + ((32 / scale) / 2)) &&
				//		(-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) >= -((32 / scale) / 2) &&
				//		(-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) <= (((yOriginal) * (32 /scale)) + ((32 / scale) / 2)))
				//{
				//	System.out.println((int)(-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) % (scale / 2)) + "x" + (int)(-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) % (32 / scale));
					//if(MainEngine.PlaceBlock((int)(-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) % (scale / 2)), (int)(-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) % (32 / scale) ,currentLevel.getTable(), choosenBlock))
			//		{
						if((((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) < (32 / scale) / 2) &&
								(((sceneX / 2) - (((xOriginal / 2 + 1) * (32 / scale)) - (xOriginal * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) > -(32 / scale) / 2) &&
								(((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())) < (32 / scale) / 2) &&
								(((sceneY / 2) - (((yOriginal / 2 + 1) * (32 / scale)) - (yOriginal * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())) > -(32 / scale) / 2))
						{
							System.out.println(((int)Math.round((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale))) + "x" + ((int)Math.round((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale))));
							if(MainEngine.PlaceBlock(((int)Math.round((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale))), ((int)Math.round((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale))), currentLevel.getTable(), choosenBlock))
							{
								System.out.println("INSIDE!");
							if((((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) % (32 / scale) != 0) ||
									(((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())) % (32 / scale) != 0))
							{
								System.out.println("X: " + ((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) + " Y: " + ((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())));
								if((((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) > 0))
								{
									System.out.println("GET1");
									choosenImage.setTranslateX((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - choosenImage.getX());
								}
								else
								{
									System.out.println("GET2");
									//System.out.println(((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) % (32 / scale)) + " THIS");
									if(((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) % (32 / scale)) < (32 / 2 * scale))
										choosenImage.setTranslateX(choosenImage.getTranslateX() - ((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) % (32 / scale)));
									else
										choosenImage.setTranslateX(choosenImage.getTranslateX() + ((32 / scale) - ((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) % (32 / scale))));
								}
								if((((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())) > 0))
								{
									System.out.println("GET3");
									choosenImage.setTranslateY((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - choosenImage.getY());
								}
								else
								{
									System.out.println("GET4");
									//System.out.println(((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) % (32 / scale)) + "THIS");
									if(((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) % (32 / scale)) < (32 / 2 * scale))
										choosenImage.setTranslateY(choosenImage.getTranslateY() - ((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) % (32 / scale)));
									else
										choosenImage.setTranslateY(choosenImage.getTranslateY() + ((32 / scale) - ((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) % (32 / scale))));
								}
							}
							}
							else
							{
								choosenImage.setTranslateX(0);
								choosenImage.setTranslateY(0);
							}
						}
						else
						{
							choosenImage.setTranslateX(0);
							choosenImage.setTranslateY(0);
						}
					//}
					//else
					//{
					//	choosenImage.setTranslateX(0);
					//	choosenImage.setTranslateY(0);
					//}
				//}
				//else
				//{
				//	choosenImage.setTranslateX(0);
				//	choosenImage.setTranslateY(0);
				//}
				if(!MainEngine.isFree(redX, redY, currentLevel.getTable()))
				{
					Group endRoot = new Group(printEndMenu(SCENEX, SCENEY));
					endScene = new Scene(endRoot, SCENEX, SCENEY);
					mainStage.setScene(endScene);
				}
				choosenImage = null;
				isMousePressed = false;
				choosenBlock = null;
				currentLevel.getTable().printTable();
				//takenflag = false;
			}
		};
		/*
		CarA2View.setOnMousePressed(mousePressedOnCarHandler);
		CarA2View.setOnMouseDragged(mouseDraggedCarHandler);
		CarA2View.setOnMouseReleased(mouseReleasedOnCarHandler);
		CarB1View.setOnMousePressed(mousePressedOnCarHandler);
		CarB1View.setOnMouseDragged(mouseDraggedCarHandler);
		CarB1View.setOnMouseReleased(mouseReleasedOnCarHandler);
		CarC1View.setOnMousePressed(mousePressedOnCarHandler);
		CarC1View.setOnMouseDragged(mouseDraggedCarHandler);
		CarC1View.setOnMouseReleased(mouseReleasedOnCarHandler);
		CarC2View.setOnMousePressed(mousePressedOnCarHandler);
		CarC2View.setOnMouseDragged(mouseDraggedCarHandler);
		CarC2View.setOnMouseReleased(mouseReleasedOnCarHandler);
		CarD1View.setOnMousePressed(mousePressedOnCarHandler);
		CarD1View.setOnMouseDragged(mouseDraggedCarHandler);
		CarD1View.setOnMouseReleased(mouseReleasedOnCarHandler);
		CarD2View.setOnMousePressed(mousePressedOnCarHandler);
		CarD2View.setOnMouseDragged(mouseDraggedCarHandler);
		CarD2View.setOnMouseReleased(mouseReleasedOnCarHandler);
		
		table.getChildren().add(CarA2View);
		table.getChildren().add(CarB1View);
		table.getChildren().add(CarC1View);
		table.getChildren().add(CarC2View);
		table.getChildren().add(CarD1View);
		table.getChildren().add(CarD2View);
		*/
		
		int temp = 0;
		Blocks[] listOfCars = currentLevel.getBlocks();
		numberOfCars = listOfCars.length;
		ImageList = new ImageView[listOfCars.length];
		Blocks previous = new Blocks();
		numberOfSlot = 0;
		Image curImg;
		
		while(temp < numberOfCars)
		{
			curImg = new Image(new FileInputStream(listOfCars[temp].getImage()));
			ImageList[temp] = new ImageView(curImg);
			if(!(previous.getClass().equals(listOfCars[temp].getClass()) || temp == numberOfCars))
			{
				numberOfSlot++;
			}
			switch(scale)
			{
			case 1:
				if(numberOfSlot < 4)
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (3 - numberOfSlot)) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				else if(numberOfSlot < 9)
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))) + (((numberOfSlot - 3) * 5) * (32 / scale)));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				else
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) - 25) * (32 / scale))));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (numberOfSlot - 9)) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				break;
			case 2:
				if(numberOfSlot < 8)
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (7 - numberOfSlot)) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				else if(numberOfSlot < 21)
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))) + (((numberOfSlot - 7) * 5) * (32 / scale)));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				else
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) - 25) * (32 / scale))));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (numberOfSlot - 21)) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				break;
			case 4:
				if(numberOfSlot < 16)
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (3 - numberOfSlot)) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				else if(numberOfSlot < 43)
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))) + (((numberOfSlot - 3) * 5) * (32 / scale)));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				else
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) - 25) * (32 / scale))));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (numberOfSlot - 9)) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				break;
			default:
				if(numberOfSlot < 31)
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (3 - numberOfSlot)) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				else if(numberOfSlot < 89)
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))) + (((numberOfSlot - 3) * 5) * (32 / scale)));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				else
				{
					ImageList[temp].setX(((sceneX / 2) - (((xOriginal / 2) - 25) * (32 / scale))));
					ImageList[temp].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (numberOfSlot - 9)) * (32 / scale)));
					ImageList[temp].setFitHeight(96 / scale);
					ImageList[temp].setFitWidth(96 / scale);
					ImageList[temp].setPreserveRatio(true);
					ImageList[temp].setCursor(Cursor.HAND);
					ImageList[temp].setOnMousePressed(mousePressedOnCarHandler);
					ImageList[temp].setOnMouseDragged(mouseDraggedCarHandler);
					ImageList[temp].setOnMouseReleased(mouseReleasedOnCarHandler);
					table.getChildren().add(ImageList[temp]);
				}
				break;
			}
			previous = listOfCars[temp];
			temp++;
		}
		
		Line centerlineX = new Line(0, sceneY / 2, sceneX, sceneY / 2);
		Line centerlineY = new Line(sceneX / 2, 0, sceneX / 2, sceneY);
		table.getChildren().add(centerlineX);
		table.getChildren().add(centerlineY);
		
		/*
		EventHandler<MouseEvent> backToMain = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mainStage.setScene(mainScene);
			}
		};
		*/
		EventHandler<MouseEvent> backToLevelMenu = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mainStage.setScene(levelMenuScene);
			}
		};
		Button button1 = new Button("Back to Level Menu");
		button1.setMinSize(100, 50);
		button1.setLayoutX(sceneX - 200);
		button1.setLayoutY(100);
		table.getChildren().add(button1);
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, backToLevelMenu);
		
		return table;
	}
	public GridPane printMenu(int sceneX, int sceneY)
	{
		GridPane menu = new GridPane();
		
		Button button1 = new Button("Play the Game");
		Button button2 = new Button("How to Play");
		Button button3 = new Button("Highscores");
		Button button4 = new Button("Options");
		Button button5 = new Button("Credits");
		Button button6 = new Button("Exit the Game");
		
		button1.setMinSize(200, 50);
		button2.setMinSize(200, 50);
		button3.setMinSize(200, 50);
		button4.setMinSize(200, 50);
		button5.setMinSize(200, 50);
		button6.setMinSize(200, 50);
		
		/*
		EventHandler<MouseEvent> playTheGameHandler = new EventHandler<MouseEvent>()
		{
			Pane tablePane;
			public void handle(MouseEvent e)
			{
				try {
					tablePane = printTable(6, 6, SCENEX, SCENEY, 1);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				Group gameRoot = new Group(tablePane);
				gameScene = new Scene(gameRoot, SCENEX, SCENEY);
				gameScene.setFill(Color.WHITE);
				mainStage.setScene(gameScene);
				gameScene.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
					public void handle(KeyEvent keyPressed)
					{
						if(isKeyPressed == false)
						{
							KeyCode key = keyPressed.getCode();
							System.out.println("Key is " + key);
							pressedKey = key;
							if(isMousePressed == true && pressedKey == KeyCode.E && choosenImage != null)
							{
								System.out.println("Rotate 90.");
								choosenImage.setRotate(choosenImage.getRotate() + 90);
							}
							if(isMousePressed == true && pressedKey == KeyCode.Q && choosenImage != null)
							{
								System.out.println("Rotate -90.");
								choosenImage.setRotate(choosenImage.getRotate() - 90);
							}
							isKeyPressed = true;
						}
					}
				});
				gameScene.setOnKeyReleased(new EventHandler<KeyEvent>()
				{
					public void handle(KeyEvent keyReleased)
					{
						System.out.println("Key released.");
						pressedKey = KeyCode.UNDEFINED;
						isKeyPressed = false;
					}
				});
			}
		};
		*/
		EventHandler<MouseEvent> levelMenuHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mainStage.setScene(levelMenuScene);
			}
		};
		EventHandler<MouseEvent> howToPlayHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mainStage.setScene(howToPlayScene);
			}
		};
		EventHandler<MouseEvent> creditsHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mainStage.setScene(creditsScene);
			}
		};
		EventHandler<MouseEvent> exitHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				Platform.exit();
			}
		};
		//button1.addEventFilter(MouseEvent.MOUSE_CLICKED, playTheGameHandler);
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, levelMenuHandler);
		button2.addEventFilter(MouseEvent.MOUSE_CLICKED, howToPlayHandler);
		button5.addEventFilter(MouseEvent.MOUSE_CLICKED, creditsHandler);
		button6.addEventFilter(MouseEvent.MOUSE_CLICKED, exitHandler);
		
		menu.relocate((sceneX / 2) - 100, (sceneY / 2) - 200);
		
		menu.setMinSize(200, 400);
		
		menu.setVgap(40);
		menu.setHgap(40);
		
		menu.add(button1, 0, 0);
		menu.add(button2, 0, 1);
		menu.add(button3, 0, 2);
		menu.add(button4, 0, 3);
		menu.add(button5, 0, 4);
		menu.add(button6, 0, 5);
		
		menu.setAlignment(Pos.CENTER);
		
		return menu;
	}
	public static GridPane printHowToPlay(int sceneX, int sceneY)
	{
		GridPane table = new GridPane();
		
		Text text1 = new Text();
		text1.setFont(Font.font("verdana",FontWeight.BOLD, FontPosture.REGULAR, 24));
		text1.setText("How to Play the Game\n");
		
		Text text2 = new Text();
		text2.setFont(Font.font("verdana",FontWeight.NORMAL, FontPosture.REGULAR, 12));
		text2.setText("To move a block, player has to click and drag the block with the mouse. ‘Q’ and ‘E’ keys are used to rotate the block left and right respectively.\n\n" + 
					  "The user manipulates and places police cars on the table as obstacles. The red car cannot be moved by the user.\n\n" +
					  "The buildings can be seen as boundaries, police cars or the red car cannot go over them.\n\n");
		
		EventHandler<MouseEvent> backToMain = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mainStage.setScene(mainScene);
			}
		};
		Button button1 = new Button("Back to Main Menu");
		button1.setMinSize(100, 50);
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, backToMain);
		
		table.add(text1, 0, 0);
		table.add(text2, 0, 1);
		table.add(button1, 0, 2);
		
		return table;
	}
	public static GridPane printCredits(int sceneX, int sceneY)
	{
		GridPane table = new GridPane();
		
		Text text1 = new Text();
		text1.setFont(Font.font("verdana",FontWeight.BOLD, FontPosture.REGULAR, 24));
		text1.setText("Credits\n");
		
		Text text2 = new Text();
		text2.setFont(Font.font("verdana",FontWeight.BOLD, FontPosture.REGULAR, 18));
		text2.setText("Designers\n");
		
		Text text3 = new Text();
		text3.setFont(Font.font("verdana",FontWeight.NORMAL, FontPosture.REGULAR, 12));
		text3.setText("Mert Özerdem\nOnur Mermer\nCemil Şişman\nBurak Alaydın\nDoruk Altan\n");
		
		Text text4 = new Text();
		text4.setFont(Font.font("verdana",FontWeight.BOLD, FontPosture.REGULAR, 18));
		text4.setText("Project Coordinators\n");
		
		Text text5 = new Text();
		text5.setFont(Font.font("verdana",FontWeight.NORMAL, FontPosture.REGULAR, 12));
		text5.setText("ErayTüzün\nGüldenOlgun");
		
		text1.setTextAlignment(TextAlignment.CENTER);
		text2.setTextAlignment(TextAlignment.CENTER);
		text3.setTextAlignment(TextAlignment.CENTER);
		text4.setTextAlignment(TextAlignment.CENTER);
		text5.setTextAlignment(TextAlignment.CENTER);
		
		EventHandler<MouseEvent> backToMain = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mainStage.setScene(mainScene);
			}
		};
		Button button1 = new Button("Back to Main Menu");
		button1.setMinSize(100, 50);
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, backToMain);
		
		table.add(text1, 0, 0);
		table.add(text2, 0, 1);
		table.add(text3, 0, 2);
		table.add(text4, 0, 3);
		table.add(text5, 0, 4);
		table.add(button1, 0, 5);
		
		table.relocate((sceneX / 2) - 100, (sceneY / 2) - 200);
		
		table.setMinSize(200, 400);
		
		table.setVgap(10);
		table.setHgap(10);
		
		table.setAlignment(Pos.CENTER);
		
		return table;
	}
	public Pane printLevelMenu(int sceneX, int sceneY)
	{
		Pane levelMenu = new Pane();
		
		Button button1 = new Button("Level 1");
		Button button0 = new Button("Back to Main Menu");
		
		Line centerlineX = new Line(0, sceneY / 2, sceneX, sceneY / 2);
		Line centerlineY = new Line(sceneX / 2, 0, sceneX / 2, sceneY);
		levelMenu.getChildren().add(centerlineX);
		levelMenu.getChildren().add(centerlineY);
		
		EventHandler<MouseEvent> playTheGameHandler = new EventHandler<MouseEvent>()
		{
			Pane tablePane;
			public void handle(MouseEvent e)
			{
				try {
					currentLevel = LevelManager.loadLevel("A01");
					blockList = currentLevel.getBlocks();
					if(currentLevel.getTable().getTableSizeX() < 8 && currentLevel.getTable().getTableSizeY() < 8)
						scale = 1;
					else if(currentLevel.getTable().getTableSizeX() < 26 && currentLevel.getTable().getTableSizeY() < 26)
						scale = 2;
					else
						scale = 4;
					tablePane = printTable(currentLevel.getTable().getTableSizeX(), currentLevel.getTable().getTableSizeY(), SCENEX, SCENEY, scale);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Group gameRoot = new Group(tablePane);
				gameScene = new Scene(gameRoot, SCENEX, SCENEY);
				gameScene.setFill(Color.WHITE);
				mainStage.setScene(gameScene);
				gameScene.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
					public void handle(KeyEvent keyPressed)
					{
						if(isKeyPressed == false)
						{
							KeyCode key = keyPressed.getCode();
							System.out.println("Key is " + key);
							pressedKey = key;
							if(isMousePressed == true && pressedKey == KeyCode.E && choosenImage != null)
							{
								System.out.println("Rotate 90.");
								choosenImage.setRotate(choosenImage.getRotate() + 90);
								MainEngine.Turn90Right(choosenBlock);
							}
							if(isMousePressed == true && pressedKey == KeyCode.Q && choosenImage != null)
							{
								System.out.println("Rotate -90.");
								choosenImage.setRotate(choosenImage.getRotate() - 90);
								MainEngine.Turn90Left(choosenBlock);
							}
							isKeyPressed = true;
						}
					}
				});
				gameScene.setOnKeyReleased(new EventHandler<KeyEvent>()
				{
					public void handle(KeyEvent keyReleased)
					{
						System.out.println("Key released.");
						pressedKey = KeyCode.UNDEFINED;
						isKeyPressed = false;
					}
				});
			}
		};
		
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, playTheGameHandler);
		button0.addEventFilter(MouseEvent.MOUSE_CLICKED, backToMain);
		
		button1.setMinSize(200, 50);
		button1.setLayoutX(100);
		button1.setLayoutY(50);
		
		button0.setMinSize(100, 50);
		button0.setLayoutX((sceneX / 2) - 50);
		button0.setLayoutY(sceneY - 75);
		
		levelMenu.getChildren().add(button1);
		levelMenu.getChildren().add(button0);
		
		return levelMenu;
	}
	
	public Pane printEndMenu(int sceneX, int sceneY)
	{
		Pane endMenu = new Pane();
		
		Button button0 = new Button("Back to Main Menu");
		
		button0.setMinSize(100, 50);
		button0.setLayoutX((sceneX / 2) - 50);
		button0.setLayoutY(sceneY - 75);
		
		button0.addEventFilter(MouseEvent.MOUSE_CLICKED, backToMain);
		
		endMenu.getChildren().add(button0);
		
		return endMenu;
	}
	
	public static void main(String args[])
	{
		launch(args);
	}
	
}