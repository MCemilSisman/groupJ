package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MainApp extends Application
{
	final int SCENEX = 1200;
	final int SCENEY = 600;
	double mouseSceneX, mouseSceneY, mouseTranslateX, mouseTranslateY;
	
	Stage mainStage;
	Scene mainScene, gameScene;
	
	public void start(Stage defaultStageIgnored) throws Exception
	{
		mainStage = new Stage();
		
		GridPane menuPane = printMenu(SCENEX, SCENEY);
		
		Group mainRoot = new Group(menuPane);
		
		Line centerlineX = new Line(0, SCENEY / 2, SCENEX, SCENEY / 2);
		Line centerlineY = new Line(SCENEX / 2, 0, SCENEX / 2, SCENEY);
		mainRoot.getChildren().add(centerlineX);
		mainRoot.getChildren().add(centerlineY);
		
		mainScene = new Scene(mainRoot, SCENEX, SCENEY);
		
		Pane tablePane = printTable(6, 6, SCENEX, SCENEY, 1);
		
		Group gameRoot = new Group(tablePane);
		
		gameScene = new Scene(gameRoot, SCENEX, SCENEY);
		
		gameScene.setFill(Color.WHITE);
		
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
		
		while(x > 0)
		{
			while(y > 0)
			{
				Image image = new Image(new FileInputStream("resources//block01.jpg"));
				
				ImageView imageView = new ImageView(image);
				
				imageView.setX(((sceneX / 2) - ((xOriginal / 2 + 1) * (32 / scale))) + x * (32 / scale));
				imageView.setY(((sceneY / 2) - ((yOriginal / 2 + 1) * (32 / scale))) + y * (32 / scale));
				
				imageView.setFitHeight(32 / scale);
				imageView.setFitWidth(32 / scale);
				
				imageView.setPreserveRatio(true);
				
				table.getChildren().add(imageView);
				y--;
			}
			y = yOriginal;
			x--;
		}
		
		Image carA1 = new Image(new FileInputStream("resources//CarA1.png"));
		ImageView carA1View = new ImageView(carA1);
		carA1View.setX(((sceneX / 2)- ((xOriginal / 2) * (32 / scale))) - 136);
		carA1View.setY((sceneY / 2)- ((yOriginal / 2) * (32 / scale)));
		carA1View.setFitHeight(96 / scale);
		carA1View.setFitWidth(96 / scale);
		carA1View.setPreserveRatio(true);
		carA1View.setCursor(Cursor.HAND);
		
		EventHandler<MouseEvent> mousePressedOnCarHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mouseSceneX = e.getSceneX();
				mouseSceneY = e.getSceneY();
				mouseTranslateX = ((ImageView)(e.getSource())).getTranslateX();
				mouseTranslateY = ((ImageView)(e.getSource())).getTranslateY();
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
				
				((ImageView)(e.getSource())).setTranslateX(newTranslateX);
				((ImageView)(e.getSource())).setTranslateY(newTranslateY);
			}
		};
		carA1View.setOnMousePressed(mousePressedOnCarHandler);
		carA1View.setOnMouseDragged(mouseDraggedCarHandler);
		
		table.getChildren().add(carA1View);
		
		Line centerlineX = new Line(0, sceneY / 2, sceneX, sceneY / 2);
		Line centerlineY = new Line(sceneX / 2, 0, sceneX / 2, sceneY);
		table.getChildren().add(centerlineX);
		table.getChildren().add(centerlineY);
		
		return table;
	}
	public GridPane printMenu(int sceneX, int sceneY)
	{
		GridPane menu = new GridPane();
		
		Button button1 = new Button("Play the Game");
		Button button2 = new Button("How to Play");
		Button button3 = new Button("Highscores");
		Button button4 = new Button("Options");
		Button button5 = new Button("Exit the Game");
		
		button1.setMinSize(200, 50);
		button2.setMinSize(200, 50);
		button3.setMinSize(200, 50);
		button4.setMinSize(200, 50);
		button5.setMinSize(200, 50);
		
		EventHandler<MouseEvent> playTheGameHandler = new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e)
			{
				mainStage.setScene(gameScene);
			}
		};
		button1.addEventFilter(MouseEvent.MOUSE_CLICKED, playTheGameHandler);
		
		menu.relocate((sceneX / 2) - 100, (sceneY / 2) - 200);
		
		menu.setMinSize(200, 400);
		
		menu.setVgap(40);
		menu.setHgap(40);
		
		menu.add(button1, 0, 0);
		menu.add(button2, 0, 1);
		menu.add(button3, 0, 2);
		menu.add(button4, 0, 3);
		menu.add(button5, 0, 4);
		
		menu.setAlignment(Pos.CENTER);
		
		return menu;
	}
	public static void main(String args[])
	{
		launch(args);
	}
}