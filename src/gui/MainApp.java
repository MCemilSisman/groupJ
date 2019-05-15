package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import GameEngine.*;
import ObjectPackage.*;
import SoundPackage.SoundManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application
{
	final int SCENEX = 1200;
	final int SCENEY = 600;
	final int SCALER1 = 11;
	final int SCALER2 = 26;
	double mouseSceneX, mouseSceneY, mouseTranslateX, mouseTranslateY;
	KeyCode pressedKey = KeyCode.UNDEFINED;
	boolean isKeyPressed = false;
	boolean isMousePressed = false;
	ImageView choosenImage = null;
	Blocks choosenBlock = null;
	int scale;
	
	static Stage mainStage;
	static Scene mainScene, gameScene, howToPlayScene, creditsScene, levelMenuScene, endScene, editorScene;
	
	//static Timer timer;
	Timeline timer;
	
	int currentPage = 0;
	int numberOfLevel;
	LevelManager levelManager;
	Level currentLevel;
	int currentLevelNumber;
	static int numberOfCars;
	static int numberOfSlot;
	static ImageView[] ImageList;
	Blocks[] blockList;
	int redX;
	int redY;
	boolean redIsOn;
	ImageView RedCar;
	int time = 0;
	
	HBox box1;
	
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
		
		/*
		Line centerlineX = new Line(0, SCENEY / 2, SCENEX, SCENEY / 2);
		Line centerlineY = new Line(SCENEX / 2, 0, SCENEX / 2, SCENEY);
		mainRoot.getChildren().add(centerlineX);
		mainRoot.getChildren().add(centerlineY);
		*/
		
		mainScene = new Scene(mainRoot, SCENEX, SCENEY);
		
		Group howToPlayRoot = new Group(printHowToPlay(SCENEX, SCENEY));
		
		howToPlayScene = new Scene(howToPlayRoot, SCENEX, SCENEY);
		
		Group creditsRoot = new Group(printCredits(SCENEX, SCENEY));
		
		creditsScene = new Scene(creditsRoot, SCENEX, SCENEY);
		
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
		time = 0;
		
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
		
		EventHandler<MouseEvent> mousePressedOnCarHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				SoundManager clickSound = new SoundManager();
				try {
					clickSound.playSound(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				System.out.println(Math.round(((double)-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale)));
						if(Math.round((double)-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale) >= -2 &&
								Math.round((double)-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale) < xOriginal &&
								Math.round(((double)-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale)) >= -2 &&
										Math.round(((double)-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale)) < yOriginal)
						{
							System.out.println(((int)Math.round((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale))) + "x" + ((int)Math.round((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale))));
							if(MainEngine.PlaceBlock(((int)Math.round((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale))), ((int)Math.round((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale))), currentLevel.getTable(), choosenBlock))
							{
								System.out.println("INSIDE!");
							if((((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) % (32 / scale) != 0) ||
									(((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())) % (32 / scale) != 0))
							{
								choosenImage.setTranslateX(((Math.round(((choosenImage.getX() + choosenImage.getTranslateX()) - ((sceneX / 2) - ((xOriginal / 2) * (32 / scale)))) / (32 / scale))) * (32 / scale)) + ((sceneX / 2) - ((xOriginal / 2) * (32 / scale))) - choosenImage.getX());
								choosenImage.setTranslateY(((Math.round(((choosenImage.getY() + choosenImage.getTranslateY()) - ((sceneY / 2) - ((yOriginal / 2) * (32 / scale)))) / (32 / scale))) * (32 / scale)) + ((sceneY / 2) - ((yOriginal / 2) * (32 / scale))) - choosenImage.getY());
								System.out.println("X: " + ((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) + " Y: " + ((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())));
							}
							}
							else
							{
								SoundManager collisionSound = new SoundManager();
								try {
									collisionSound.playSound(2);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								choosenImage.setTranslateX(0);
								choosenImage.setTranslateY(0);
							}
						}
						else
						{
							choosenImage.setTranslateX(0);
							choosenImage.setTranslateY(0);
						}
				if(!MainEngine.isFree(redX, redY, currentLevel.getTable()))
				{
					//time = MainEngine.returnSeconds(MainEngine.getInitialTime(), MainEngine.getCurrentTime());
					timer.stop();
					Group endRoot = new Group(printEndMenu(SCENEX, SCENEY));
					endScene = new Scene(endRoot, SCENEX, SCENEY);
					mainStage.setScene(endScene);
				}
				choosenImage = null;
				isMousePressed = false;
				choosenBlock = null;
				currentLevel.getTable().printTable();
			}
		};
		
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
		/*
		Line centerlineX = new Line(0, sceneY / 2, sceneX, sceneY / 2);
		Line centerlineY = new Line(sceneX / 2, 0, sceneX / 2, sceneY);
		table.getChildren().add(centerlineX);
		table.getChildren().add(centerlineY);
		*/
		EventHandler<MouseEvent> backToLevelMenu = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				timer.stop();
				mainStage.setScene(levelMenuScene);
			}
		};
		Button button1 = new Button("Back to Level Menu");
		button1.setMinSize(100, 50);
		button1.setLayoutX(sceneX - 200);
		button1.setLayoutY(100);
		table.getChildren().add(button1);
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, backToLevelMenu);
		Label timeBoard = new Label(String.format("%02d:%02d:%02d", time / 3600, time / 60, time % 60));
		timeBoard.setLayoutX(sceneX - 200);
		timeBoard.setLayoutY(50);
		timeBoard.setFont(new Font("Cambria", 32));
		table.getChildren().add(timeBoard);
		
		//MainEngine.InitLevelTimer(MainEngine.getInitialTime());
		/*
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run()
			{
				System.out.println(time);
				time++;
				timeBoard.setText(((String.valueOf(time / 3600)) + ":" + (String.valueOf(time / 60)) + ":" + (String.valueOf(time % 60))));
			}
		}, 1000, 1000);
		*/
		timer = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
			time++;
			timeBoard.setText(String.format("%02d:%02d:%02d", time / 3600, time / 60, time % 60));
	    }));
	    timer.setCycleCount(Animation.INDEFINITE);
	    timer.play();
		
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
		Button button7 = new Button("Editor");
		
		button1.setMinSize(200, 50);
		button2.setMinSize(200, 50);
		button3.setMinSize(200, 50);
		button4.setMinSize(200, 50);
		button5.setMinSize(200, 50);
		button6.setMinSize(200, 50);
		button7.setMinSize(200, 50);
		
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
		
		Label label1 = new Label("Table Size(row x column): ");
		Label label2 = new Label(" x ");
		TextField textfield1 = new TextField();
		TextField textfield2 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(label1, textfield1, label2, textfield2);
		box1.setSpacing(2);
		textfield1.setText("6");
		textfield2.setText("6");
		
		textfield1.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newVal)
			{
				if(!newVal.equals(""))
					if(!isInt(newVal))
						textfield1.setText(old);
					else
						if(!(Integer.valueOf(newVal) < SCALER2 && Integer.valueOf(newVal) > 0))
							textfield1.setText(old);
			}
			
		});
		textfield2.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newVal)
			{
				if(!newVal.equals(""))
					if(!isInt(newVal))
						textfield2.setText(old);
					else
						if(!(Integer.valueOf(newVal) < SCALER2 && Integer.valueOf(newVal) > 0))
							textfield2.setText(old);
			}
			
		});
		EventHandler<MouseEvent> levelMenuHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				Group levelMenuRoot = null;
				try {
					levelMenuRoot = new Group(printLevelMenu(SCENEX, SCENEY));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				levelMenuScene = new Scene(levelMenuRoot, SCENEX, SCENEY);
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
		EventHandler<MouseEvent> editorHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				Group editorRoot = null;
				try {
					editorRoot = new Group(printEditor(Integer.valueOf(textfield1.getText()), Integer.valueOf(textfield2.getText()), SCENEX, SCENEY));
				} catch (NumberFormatException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				editorScene = new Scene(editorRoot, SCENEX, SCENEY);
				mainStage.setScene(editorScene);
			}
		};
		//button1.addEventFilter(MouseEvent.MOUSE_CLICKED, playTheGameHandler);
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, levelMenuHandler);
		button2.addEventFilter(MouseEvent.MOUSE_CLICKED, howToPlayHandler);
		button5.addEventFilter(MouseEvent.MOUSE_CLICKED, creditsHandler);
		button6.addEventFilter(MouseEvent.MOUSE_CLICKED, exitHandler);
		button7.addEventFilter(MouseEvent.MOUSE_CLICKED, editorHandler);
		
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
		menu.add(button7, 1, 2);
		menu.add(box1, 1, 1);
		
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
	public Pane printLevelMenu(int sceneX, int sceneY) throws IOException
	{
		Pane[] levelMenu;
		
		numberOfLevel = LevelManager.getLevelNumber();
		int pageNumber = ((numberOfLevel - 1) / 10) + 1;
		levelMenu = new Pane[pageNumber];
		Button[] buttonGroup = new Button[numberOfLevel];
		//Button button1 = new Button("Level 1");
		Button button0 = new Button("Back to Main Menu");
		Button button2 = new Button("Previous");
		Button button3 = new Button("Next");
		
		/*
		Line centerlineX = new Line(0, sceneY / 2, sceneX, sceneY / 2);
		Line centerlineY = new Line(sceneX / 2, 0, sceneX / 2, sceneY);
		levelMenu.getChildren().add(centerlineX);
		levelMenu.getChildren().add(centerlineY);
		*/
		EventHandler<MouseEvent> playTheGameHandler = new EventHandler<MouseEvent>()
		{
			Pane tablePane;
			public void handle(MouseEvent e)
			{
				try {
					String temp = ((Button)(e.getSource())).getText().replace("Level ", "");
					int converted = Integer.valueOf(temp);
					currentLevelNumber = converted;
					converted--;
					temp = String.valueOf(converted);
					
					int hundredCount = 0;
					String code = "A";
					if(temp.length() > 2)
					{
						while(temp.length() > 2)
						{
							hundredCount++;
							converted = Integer.valueOf(temp);
							converted -= 100;
							temp = String.valueOf(converted);
						}
					}
					if(temp.length() < 2)
						temp = "0" + temp;
					System.out.println(temp);
					while(hundredCount > 0)
					{
						converted = (int)(code.charAt(0));
						converted++;
						code = String.valueOf((char)converted);
						hundredCount--;
					}
					code = code + temp;
					currentLevel = LevelManager.loadLevel(code);
					blockList = currentLevel.getBlocks();
					if(currentLevel.getTable().getTableSizeX() < SCALER1 && currentLevel.getTable().getTableSizeY() < SCALER1)
						scale = 1;
					else if(currentLevel.getTable().getTableSizeX() < SCALER2 && currentLevel.getTable().getTableSizeY() < SCALER2)
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
		
		for(int k = 0; k < pageNumber; k++)
		{
			levelMenu[k] = new Pane();
			for(int i = 0; i < 10 && ((k * 10) + i) < numberOfLevel; i++)
			{
				buttonGroup[(k * 10) + i] = new Button(("Level " + String.valueOf((k * 10) + i + 1)));
				buttonGroup[(k * 10) + i].addEventFilter(MouseEvent.MOUSE_CLICKED, playTheGameHandler);
				buttonGroup[(k * 10) + i].setMinSize(200, 50);
				if((i / 5) % 2 == 0)
				{
					buttonGroup[(k * 10) + i].setLayoutX(200);
				}
				else
				{
					buttonGroup[(k * 10) + i].setLayoutX(800);
				}
				buttonGroup[(k * 10) + i].setLayoutY(50 + ((i % 5) * 100));
				levelMenu[k].getChildren().add(buttonGroup[(k * 10) + i]);
			}
		}
		
		//button1.addEventFilter(MouseEvent.MOUSE_CLICKED, playTheGameHandler);
		
		
		EventHandler<MouseEvent> backToEnd = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				Group endRoot = new Group(printEndMenu(SCENEX, SCENEY));
				endScene = new Scene(endRoot, SCENEX, SCENEY);
				mainStage.setScene(endScene);
			}
		};
		EventHandler<MouseEvent> prevPage = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				if(currentPage == 0)
					currentPage = pageNumber - 1;
				else
					currentPage--;
				Group levelMenuRoot = null;
				try {
					levelMenuRoot = new Group(printLevelMenu(SCENEX, SCENEY));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				levelMenuScene = new Scene(levelMenuRoot, SCENEX, SCENEY);
				mainStage.setScene(levelMenuScene);
			}
		};
		EventHandler<MouseEvent> nextPage = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				if(currentPage == pageNumber - 1)
					currentPage = 0;
				else
					currentPage++;
				Group levelMenuRoot = null;
				try {
					levelMenuRoot = new Group(printLevelMenu(SCENEX, SCENEY));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				levelMenuScene = new Scene(levelMenuRoot, SCENEX, SCENEY);
				mainStage.setScene(levelMenuScene);
			}
		};
		
		//button1.setMinSize(200, 50);
		//button1.setLayoutX(100);
		//button1.setLayoutY(50);
		
		
		
		for(int i = 0; i < pageNumber; i++)
		{
			button0 = new Button("Back to Main Menu");
			button2 = new Button("Previous");
			button3 = new Button("Next");
			button0.setMinSize(100, 50);
			button0.setLayoutX((sceneX / 2) - 50);
			button0.setLayoutY(sceneY - 75);
			button2.setMinSize(100, 50);
			button2.setLayoutX((sceneX / 2) - 167);
			button2.setLayoutY(sceneY - 75);
			button3.setMinSize(100, 50);
			button3.setLayoutX((sceneX / 2) + 83);
			button3.setLayoutY(sceneY - 75);
			
			Button button1 = new Button("End Game");
			button0.addEventFilter(MouseEvent.MOUSE_CLICKED, backToMain);
			button1.addEventFilter(MouseEvent.MOUSE_CLICKED, backToEnd);
			button2.addEventFilter(MouseEvent.MOUSE_CLICKED, prevPage);
			button3.addEventFilter(MouseEvent.MOUSE_CLICKED, nextPage);
			button1.setMinSize(100, 50);
			button1.setLayoutX((sceneX / 2) - 50);
			button1.setLayoutY(sceneY - 175);
			levelMenu[i].getChildren().add(button1);
			levelMenu[i].getChildren().add(button0);
			levelMenu[i].getChildren().add(button2);
			levelMenu[i].getChildren().add(button3);
		}
		
		return levelMenu[currentPage];
	}
	
	public Pane printEndMenu(int sceneX, int sceneY)
	{
		Pane endMenu = new Pane();
		
		TextField textfield1 = new TextField("Enter the name");
		Button button1 = new Button("Enter");
		button1.setMinSize(100, 50);
		box1 = new HBox();
		box1 = new HBox();
		box1.getChildren().addAll(textfield1, button1);
		box1.setSpacing(2);
		box1.setLayoutX(SCENEX / 2 - 80);
		box1.setLayoutY(SCENEY / 2 - 35);

		Button button0 = new Button("Back to Main Menu");
		Button button2 = new Button("Play Next Level");
		
		TextArea scores = new TextArea();
		scores.setMaxSize(300, 400);
		scores.setLayoutX((SCENEX / 2) - (scores.getMaxWidth() / 2));
		scores.setLayoutY((SCENEY / 2) - (scores.getMaxHeight() / 2));
		EventHandler<MouseEvent> nameEntered = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				try {
					scores.setText(MainEngine.saveSortReturnScores(textfield1.getText(), time, currentLevelNumber));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				box1.setVisible(false);
				scores.setVisible(true);
				button0.setVisible(true);
				if(currentLevelNumber + 1 <= numberOfLevel)
					button2.setVisible(true);
			}
		};
		EventHandler<MouseEvent> nextLevelHandler = new EventHandler<MouseEvent>()
		{
			Pane tablePane;
			public void handle(MouseEvent e)
			{
				try {
					String temp = "Level " + String.valueOf(currentLevelNumber + 1);
					int converted = currentLevelNumber + 1;
					currentLevelNumber = converted;
					converted--;
					temp = String.valueOf(converted);
					
					int hundredCount = 0;
					String code = "A";
					if(temp.length() > 2)
					{
						while(temp.length() > 2)
						{
							hundredCount++;
							converted = Integer.valueOf(temp);
							converted -= 100;
							temp = String.valueOf(converted);
						}
					}
					if(temp.length() < 2)
						temp = "0" + temp;
					System.out.println(temp);
					while(hundredCount > 0)
					{
						converted = (int)(code.charAt(0));
						converted++;
						code = String.valueOf((char)converted);
						hundredCount--;
					}
					code = code + temp;
					currentLevel = LevelManager.loadLevel(code);
					blockList = currentLevel.getBlocks();
					if(currentLevel.getTable().getTableSizeX() < SCALER1 && currentLevel.getTable().getTableSizeY() < SCALER1)
						scale = 1;
					else if(currentLevel.getTable().getTableSizeX() < SCALER2 && currentLevel.getTable().getTableSizeY() < SCALER2)
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
		
		button2.setOnMouseClicked(nextLevelHandler);
		button1.setOnMouseClicked(nameEntered);
		
		button0.setMinSize(100, 50);
		button0.setLayoutX((sceneX / 2) - 150);
		button0.setLayoutY(sceneY - 175);
		button2.setMinSize(100, 50);
		button2.setLayoutX((sceneX / 2) + 50);
		button2.setLayoutY(sceneY - 175);
		
		button0.addEventFilter(MouseEvent.MOUSE_CLICKED, backToMain);
		
		endMenu.getChildren().add(box1);
		endMenu.getChildren().add(button0);
		endMenu.getChildren().add(button2);
		endMenu.getChildren().add(scores);
		scores.setVisible(false);
		button0.setVisible(false);
		button2.setVisible(false);
		
		return endMenu;
	}
	
	public Pane printEditor(int x, int y, int sceneX, int sceneY) throws FileNotFoundException
	{
		Pane editor = new Pane();
		redIsOn = false;
		
		if(x < SCALER1 && y < SCALER1)
			scale = 1;
		else
			scale = 2;
		
		currentLevel = new Level();
		currentLevel.createTable(x, y);
		currentLevel.getTable().printTable();
		numberOfCars = 0;
		numberOfSlot = 0;
		ImageList = null;
		
		double xOriginal = x;
		double yOriginal = y;
		
		EventHandler<MouseEvent> mousePressedOnTile = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				SoundManager clickSound = new SoundManager();
				try {
					clickSound.playSound(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println((int)(((e.getX() - ((sceneX / 2) - ((xOriginal / 2) * (32 / scale)))) / (32 / scale))) + " x " + (int)(((e.getY() - ((sceneY / 2) - ((yOriginal / 2) * (32 / scale)))) / (32 / scale))));
				if(((ImageView)(e.getSource())).getId().equals("empty"))
				{
					ImageView temp = ((ImageView)(e.getSource()));
					Image image = null;
					try {
						image = new Image(new FileInputStream("resources//building.jpg"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					temp.setId("block");
					temp.setImage(image);
					currentLevel.setTable((int)(((e.getX() - ((sceneX / 2) - ((xOriginal / 2) * (32 / scale)))) / (32 / scale))), (int)(((e.getY() - ((sceneY / 2) - ((yOriginal / 2) * (32 / scale)))) / (32 / scale))), BlockType.BLOCK);
				}
				else if(((ImageView)(e.getSource())).getId().equals("block"))
				{
					if(!redIsOn)
					{
						ImageView temp = ((ImageView)(e.getSource()));
						Image image = null;
						try {
							image = new Image(new FileInputStream("resources//redCar.jpg"));
						} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						temp.setId("red");
						temp.setImage(image);
						
						redX = (int)(((e.getX() - ((sceneX / 2) - ((xOriginal / 2) * (32 / scale)))) / (32 / scale)));
						redY = (int)(((e.getY() - ((sceneY / 2) - ((yOriginal / 2) * (32 / scale)))) / (32 / scale)));
						redIsOn = true;
						currentLevel.setTable((int)(((e.getX() - ((sceneX / 2) - ((xOriginal / 2) * (32 / scale)))) / (32 / scale))), (int)(((e.getY() - ((sceneY / 2) - ((yOriginal / 2) * (32 / scale)))) / (32 / scale))), BlockType.RED);
					}
					else
					{
						ImageView temp = ((ImageView)(e.getSource()));
						Image image = null;
						try {
							image = new Image(new FileInputStream("resources//block01.jpg"));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						temp.setId("empty");
						temp.setImage(image);
						currentLevel.setTable((int)(((e.getX() - ((sceneX / 2) - ((xOriginal / 2) * (32 / scale)))) / (32 / scale))), (int)(((e.getY() - ((sceneY / 2) - ((yOriginal / 2) * (32 / scale)))) / (32 / scale))), BlockType.EMPTY);
					}
				}
				else
				{
					ImageView temp = ((ImageView)(e.getSource()));
					Image image = null;
					try {
						image = new Image(new FileInputStream("resources//block01.jpg"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					temp.setId("empty");
					temp.setImage(image);
					redIsOn = false;
					currentLevel.setTable((int)(((e.getX() - ((sceneX / 2) - ((xOriginal / 2) * (32 / scale)))) / (32 / scale))), (int)(((e.getY() - ((sceneY / 2) - ((yOriginal / 2) * (32 / scale)))) / (32 / scale))), BlockType.EMPTY);
				}
				System.out.println();
				currentLevel.getTable().printTable();
			}
		};
		
		for(int k = 0; k < yOriginal; k++)
		{
			for(int l = 0; l < xOriginal; l++)
			{
				Image image = new Image(new FileInputStream("resources//block01.jpg"));
				
				ImageView imageView = new ImageView(image);
				imageView.setId("empty");
			
				imageView.setX(((sceneX / 2) - ((xOriginal / 2) * (32 / scale))) + l * (32 / scale));
				imageView.setY(((sceneY / 2) - ((yOriginal / 2) * (32 / scale))) + k * (32 / scale));
			
				imageView.setFitHeight(32 / scale);
				imageView.setFitWidth(32 / scale);
			

				imageView.setCursor(Cursor.HAND);
				imageView.setOnMousePressed(mousePressedOnTile);
				imageView.setPreserveRatio(true);
			
				editor.getChildren().add(imageView);
			}
		}
		
		
		EventHandler<MouseEvent> mousePressedOnCarHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				SoundManager clickSound = new SoundManager();
				try {
					clickSound.playSound(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(e.isPrimaryButtonDown())
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
							System.out.println("DOGRU: " + blockList[temp]);
							choosenBlock = blockList[temp];
							if((mouseSceneX >= ((SCENEX / 2) - ((xOriginal / 2) * (32 / scale)))) && (mouseSceneX <= ((SCENEX / 2) + ((xOriginal / 2) * (32 / scale)))) && (mouseSceneY <= ((SCENEY / 2) + ((yOriginal / 2) * (32 / scale)))) && (mouseSceneY >= ((SCENEY / 2) - ((yOriginal / 2) * (32 / scale)))))
							{
								System.out.println(blockList[temp]);
								MainEngine.TakeBlock(currentLevel.getTable(), choosenBlock);
							}
						}
						temp++;
					}
					isMousePressed = true;
				}
				else if(e.isSecondaryButtonDown())
				{
					System.out.println(ImageList.length + " UZUNLUGU" + currentLevel.getBlocks()[0]);
					boolean deletedFlag = false;
					for(int i = 0; i < ImageList.length && !deletedFlag; i++)
					{
						System.out.println(e.getSource());
						if(ImageList[i].equals(e.getSource()))
						{
							if(e.getSceneX() >= (SCENEX / 2) - (xOriginal * (32 / scale)) &&
							   e.getSceneX() <= (SCENEX / 2) + (xOriginal * (32 / scale)) &&
							   e.getSceneY() >= (SCENEY / 2) - (yOriginal * (32 / scale)) &&
							   e.getSceneY() <= (SCENEY / 2) + (yOriginal * (32 / scale)))
								MainEngine.TakeBlock(currentLevel.getTable(), currentLevel.getBlocks()[i]);
							ImageList[i].setVisible(false);
							removeFromImgList(currentLevel.getBlocks()[i], i);
							currentLevel.removeEditorBlock(currentLevel.getBlocks()[i]);
							deletedFlag = true;
							choosenImage = null;
							isMousePressed = false;
							choosenBlock = null;
						}
					}
				}
				
			}
		};
		EventHandler<MouseEvent> mouseDraggedCarHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				if(e.getButton() == MouseButton.PRIMARY)
				{
				double offsetX = e.getSceneX() - mouseSceneX;
				double offsetY = e.getSceneY() - mouseSceneY;
				double newTranslateX = mouseTranslateX + offsetX;
				double newTranslateY = mouseTranslateY + offsetY;
				System.out.println("Translate X: " + newTranslateX + " Y: " + newTranslateY);
				((ImageView)(e.getSource())).setTranslateX(newTranslateX);
				((ImageView)(e.getSource())).setTranslateY(newTranslateY);
				}
			}
		};
		EventHandler<MouseEvent> mouseReleasedOnCarHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				if(e.getButton() == MouseButton.PRIMARY)
				{
				System.out.println(Math.round(((double)-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale)));
						if(Math.round((double)-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale) >= -2 &&
								Math.round((double)-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale) < xOriginal &&
								Math.round(((double)-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale)) >= -2 &&
										Math.round(((double)-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale)) < yOriginal)
						{
							System.out.println(((int)Math.round((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale))) + "x" + ((int)Math.round((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale))));
							if(MainEngine.PlaceBlock(((int)Math.round((-((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX()))) / (32 / scale))), ((int)Math.round((-((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY()))) / (32 / scale))), currentLevel.getTable(), choosenBlock))
							{
								System.out.println("INSIDE!");
							if((((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) % (32 / scale) != 0) ||
									(((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())) % (32 / scale) != 0))
							{
								choosenImage.setTranslateX(((Math.round(((choosenImage.getX() + choosenImage.getTranslateX()) - ((sceneX / 2) - ((xOriginal / 2) * (32 / scale)))) / (32 / scale))) * (32 / scale)) + ((sceneX / 2) - ((xOriginal / 2) * (32 / scale))) - choosenImage.getX());
								choosenImage.setTranslateY(((Math.round(((choosenImage.getY() + choosenImage.getTranslateY()) - ((sceneY / 2) - ((yOriginal / 2) * (32 / scale)))) / (32 / scale))) * (32 / scale)) + ((sceneY / 2) - ((yOriginal / 2) * (32 / scale))) - choosenImage.getY());
								System.out.println("X: " + ((sceneX / 2) - (((xOriginal / 2) * (32 / scale))) - (choosenImage.getX() + choosenImage.getTranslateX())) + " Y: " + ((sceneY / 2) - (((yOriginal / 2) * (32 / scale))) - (choosenImage.getY() + choosenImage.getTranslateY())));
							}
							}
							else
							{
								SoundManager collisionSound = new SoundManager();
								try {
									collisionSound.playSound(2);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								choosenImage.setTranslateX(0);
								choosenImage.setTranslateY(0);
							}
						}
						else
						{
							choosenImage.setTranslateX(0);
							choosenImage.setTranslateY(0);
						}
				choosenImage = null;
				isMousePressed = false;
				choosenBlock = null;
				currentLevel.getTable().printTable();
				}
			}
		};
		
		EventHandler<MouseEvent> mousePressedList = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				SoundManager clickSound = new SoundManager();
				try {
					clickSound.playSound(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Mouse x: " + e.getSceneX() + " y: " + e.getSceneY());
				System.out.println(((ListView<ImageView>)(e.getSource())).getSelectionModel().getSelectedItem().getId());
				String type = ((ListView<ImageView>)(e.getSource())).getSelectionModel().getSelectedItem().getId();
				switch(type)
				{
				case "CarA":
					try {
						currentLevel.addEditorBlock(new CarA());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CarB":
					try {
						currentLevel.addEditorBlock(new CarB());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CarC":
					try {
						currentLevel.addEditorBlock(new CarC());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CarD":
					try {
						currentLevel.addEditorBlock(new CarD());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CarE":
					try {
						currentLevel.addEditorBlock(new CarE());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CarF":
					try {
						currentLevel.addEditorBlock(new CarF());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CarG":
					try {
						currentLevel.addEditorBlock(new CarG());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CarH":
					try {
						currentLevel.addEditorBlock(new CarH());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case "CarI":
					try {
						currentLevel.addEditorBlock(new CarI());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
				System.out.println("Uzunluk: " + currentLevel.getBlocks());
				blockList = currentLevel.getBlocks();
				try {
					addToImgList(currentLevel.getBlocks()[currentLevel.getBlocks().length - 1]);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ImageList[ImageList.length - 1].setId(currentLevel.getBlocks()[currentLevel.getBlocks().length - 1].getImage());
				System.out.println("Number Of Slots: " + numberOfSlot);
				boolean placeFound = false;
				int placePos = 0;
				String[] prevs = null;
				for(int i = 0; i < ImageList.length && !placeFound; i++)
				{
					System.out.println(ImageList[i].getId());
					if(ImageList[i].getId().equals(ImageList[ImageList.length - 1].getId()) && i != 0)
					{
						placePos = prevs.length + 1;
						placeFound = true;
						System.out.println("Yer bulundu!");
					}
					else if(ImageList[i].getId().equals(ImageList[ImageList.length - 1].getId()) && i == 0)
					{
						placePos = 1;
						placeFound = true;
					}
					else
					{
						boolean prevFound = false;
						if(prevs != null)
						{
							for(int k = 0; k < prevs.length && !prevFound; k++)
							{
								if(prevs[k].equals(ImageList[i].getId()))
									prevFound = true;
							}
							if(!prevFound)
							{
								String[] temp = new String[prevs.length + 1];
								for(int l = 0; l < prevs.length; l++)
									temp[l] = prevs[l];
								temp[prevs.length] = ImageList[i].getId();
								prevs = temp;
							}
						}
						else
						{
							prevs = new String[1];
							prevs[0] = ImageList[i].getId();
						}
					}
				}
				if(!placeFound)
					placePos = numberOfSlot;
				System.out.println("Place Pos: " + placePos);
				switch(scale)
				{
				case 1:
					if(placePos < 4)
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (3 - placePos)) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					else if(placePos < 9)
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))) + (((placePos - 3) * 5) * (32 / scale)));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					else
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) - 25) * (32 / scale))));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (placePos - 9)) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					break;
				case 2:
					if(placePos < 8)
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (7 - placePos)) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					else if(placePos < 21)
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))) + (((placePos - 7) * 5) * (32 / scale)));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					else
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) - 25) * (32 / scale))));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (placePos - 21)) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					break;
				case 4:
					if(placePos < 16)
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (3 - placePos)) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					else if(placePos < 43)
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))) + (((placePos - 3) * 5) * (32 / scale)));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					else
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) - 25) * (32 / scale))));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (placePos - 9)) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					break;
				default:
					if(placePos < 31)
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (3 - placePos)) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					else if(placePos < 89)
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) + 5) * (32 / scale))) + (((placePos - 3) * 5) * (32 / scale)));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					else
					{
						ImageList[ImageList.length - 1].setX(((sceneX / 2) - (((xOriginal / 2) - 25) * (32 / scale))));
						ImageList[ImageList.length - 1].setY((sceneY / 2) + (((yOriginal / 2) + 1) * (32 / scale)) - ((5 * (placePos - 9)) * (32 / scale)));
						ImageList[ImageList.length - 1].setFitHeight(96 / scale);
						ImageList[ImageList.length - 1].setFitWidth(96 / scale);
						ImageList[ImageList.length - 1].setPreserveRatio(true);
						ImageList[ImageList.length - 1].setCursor(Cursor.HAND);
						ImageList[ImageList.length - 1].setOnMousePressed(mousePressedOnCarHandler);
						ImageList[ImageList.length - 1].setOnMouseDragged(mouseDraggedCarHandler);
						ImageList[ImageList.length - 1].setOnMouseReleased(mouseReleasedOnCarHandler);
						editor.getChildren().add(ImageList[ImageList.length - 1]);
					}
					break;
				}
			}
		};
		editor.setOnKeyPressed(new EventHandler<KeyEvent>()
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
		editor.setOnKeyReleased(new EventHandler<KeyEvent>()
		{
			public void handle(KeyEvent keyReleased)
			{
				System.out.println("Key released.");
				pressedKey = KeyCode.UNDEFINED;
				isKeyPressed = false;
			}
		});
		
		ListView<ImageView> list = new ListView<ImageView>();
		list.setOnMouseClicked(mousePressedList);
		ImageView[] listOfBlocks = new ImageView[9];
		Image temp = null;
		temp = new Image(new FileInputStream("resources//CarA.png"));
		listOfBlocks[0] = new ImageView(temp);
		listOfBlocks[0].setFitHeight(96 / scale);
		listOfBlocks[0].setFitWidth(96 / scale);
		listOfBlocks[0].setPreserveRatio(true);
		listOfBlocks[0].setId("CarA");
		/*
		listOfBlocks[0].setCursor(Cursor.HAND);
		listOfBlocks[0].setOnMousePressed(mousePressedOnCarList);
		listOfBlocks[0].setOnMouseDragged(mouseDraggedCarList);
		listOfBlocks[0].setOnMouseReleased(mouseReleasedOnCarList);
		*/
		temp = new Image(new FileInputStream("resources//CarB.png"));
		listOfBlocks[1] = new ImageView(temp);
		listOfBlocks[1].setFitHeight(96 / scale);
		listOfBlocks[1].setFitWidth(96 / scale);
		listOfBlocks[1].setPreserveRatio(true);
		listOfBlocks[1].setId("CarB");
		temp = new Image(new FileInputStream("resources//CarC.png"));
		listOfBlocks[2] = new ImageView(temp);
		listOfBlocks[2].setFitHeight(96 / scale);
		listOfBlocks[2].setFitWidth(96 / scale);
		listOfBlocks[2].setPreserveRatio(true);
		listOfBlocks[2].setId("CarC");
		temp = new Image(new FileInputStream("resources//CarD.png"));
		listOfBlocks[3] = new ImageView(temp);
		listOfBlocks[3].setFitHeight(96 / scale);
		listOfBlocks[3].setFitWidth(96 / scale);
		listOfBlocks[3].setPreserveRatio(true);
		listOfBlocks[3].setId("CarD");
		temp = new Image(new FileInputStream("resources//CarE.png"));
		listOfBlocks[4] = new ImageView(temp);
		listOfBlocks[4].setFitHeight(96 / scale);
		listOfBlocks[4].setFitWidth(96 / scale);
		listOfBlocks[4].setPreserveRatio(true);
		listOfBlocks[4].setId("CarE");
		temp = new Image(new FileInputStream("resources//CarF.png"));
		listOfBlocks[5] = new ImageView(temp);
		listOfBlocks[5].setFitHeight(96 / scale);
		listOfBlocks[5].setFitWidth(96 / scale);
		listOfBlocks[5].setPreserveRatio(true);
		listOfBlocks[5].setId("CarF");
		temp = new Image(new FileInputStream("resources//CarG.png"));
		listOfBlocks[6] = new ImageView(temp);
		listOfBlocks[6].setFitHeight(96 / scale);
		listOfBlocks[6].setFitWidth(96 / scale);
		listOfBlocks[6].setPreserveRatio(true);
		listOfBlocks[6].setId("CarG");
		temp = new Image(new FileInputStream("resources//CarH.png"));
		listOfBlocks[7] = new ImageView(temp);
		listOfBlocks[7].setFitHeight(96 / scale);
		listOfBlocks[7].setFitWidth(96 / scale);
		listOfBlocks[7].setPreserveRatio(true);
		listOfBlocks[7].setId("CarH");
		temp = new Image(new FileInputStream("resources//CarI.png"));
		listOfBlocks[8] = new ImageView(temp);
		listOfBlocks[8].setFitHeight(96 / scale);
		listOfBlocks[8].setFitWidth(96 / scale);
		listOfBlocks[8].setPreserveRatio(true);
		listOfBlocks[8].setId("CarI");
		
		ObservableList<ImageView> images =FXCollections.observableArrayList (
				listOfBlocks);
		list.setItems(images);
		
		list.setLayoutX(50);
		list.setLayoutY(25);
		list.setMaxSize((96 / scale) + 50, (96 / scale) + 450);
		
		editor.getChildren().add(list);
		
		
		
		EventHandler<MouseEvent> saveToMain = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				System.out.println(redX + " x " + redY);
				if(!MainEngine.isFree(redX, redY, currentLevel.getTable()))
				{
					for(int i = 0; i < currentLevel.getBlocks().length; i++)
						if(currentLevel.getBlocks()[i].getCoordinateX() != -1 || currentLevel.getBlocks()[i].getCoordinateY() != -1)
							MainEngine.TakeBlock(currentLevel.getTable(), currentLevel.getBlocks()[i]);
					try {
						LevelManager.saveLevel(currentLevel);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mainStage.setScene(mainScene);
				}
			}
		};
		
		/*
		Line centerlineX = new Line(0, SCENEY / 2, SCENEX, SCENEY / 2);
		Line centerlineY = new Line(SCENEX / 2, 0, SCENEX / 2, SCENEY);
		editor.getChildren().add(centerlineX);
		editor.getChildren().add(centerlineY);
		*/
		
		Button button1 = new Button("Back to Main Menu");
		button1.setMinSize(100, 50);
		button1.setLayoutX(sceneX - 200);
		button1.setLayoutY(100);
		editor.getChildren().add(button1);
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, backToMain);
		
		Button button2 = new Button("Save Level");
		button2.setMinSize(100, 50);
		button2.setLayoutX(sceneX - 200);
		button2.setLayoutY(200);
		editor.getChildren().add(button2);
		button2.addEventFilter(MouseEvent.MOUSE_CLICKED, saveToMain);
		
		return editor;
	}
	
	public static void main(String args[])
	{
		launch(args);
	}
	
	public static boolean isInt(String in)
	{
		try
		{
			int i = Integer.parseInt(in);
		}
		catch(NumberFormatException | NullPointerException nfe)
		{
			return false;
		}
		
		if((Double.parseDouble(in) % 1) == 0)
			return true;
		else
			return false;
	}
	
	public static void addToImgList(Blocks added) throws FileNotFoundException
	{
		if(ImageList != null)
		{
			ImageView[] temp = new ImageView[numberOfCars + 1];
			for(int i = 0; i < numberOfCars; i++)
				temp[i] = ImageList[i];
			Image image = new Image(new FileInputStream(added.getImage()));
			temp[numberOfCars] = new ImageView(image);
			ImageList = temp;
		}
		else
		{
			ImageList = new ImageView[1];
			Image image = new Image(new FileInputStream(added.getImage()));
			ImageList[0] = new ImageView(image);
		}
		numberOfCars++;
		boolean sameFlag = false;
		for(int i = 0; i < numberOfCars; i++)
		{
			if(added.getImage().equals(ImageList[i].getId()))
				sameFlag = true;
		}
		if(!sameFlag)
			numberOfSlot++;
	}
	
	public static void removeFromImgList(Blocks removed, int pos)
	{
		System.out.println(numberOfCars);
		if(numberOfCars != 1)
		{
			boolean found = false;
			ImageView[] temp = new ImageView[numberOfCars - 1];
			for(int i = 0; i < numberOfCars; i++)
			{
				if((i != pos) && !found)
				{
					temp[i] = ImageList[i];
				}
				else if(found)
				{
					temp[i - 1] = ImageList[i];
				}
				else
					found = true;
			}
			ImageList = temp;
		}
		else
		{
			ImageList = null;
		}
		numberOfCars--;
		boolean sameType = false;
		for(int i = 0; i < numberOfCars; i++)
			if(removed.getImage().equals(ImageList[i].getId()))
				sameType = true;
		if(!sameType)
			numberOfSlot--;
	}
}