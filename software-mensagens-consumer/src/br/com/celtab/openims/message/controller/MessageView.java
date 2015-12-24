package br.com.celtab.openims.message.controller;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import com.sun.javafx.stage.StageHelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tray.message.models.DisplayMessage;

public class MessageView extends Application implements Observer{
	private Stage primaryStage;
	private AnchorPane rootLayout;
	private DisplayMessage message;
	private Scene scene;
	
	private FXMLLoader loader;
	private MessageController controller;

	
	public MessageView() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;		
		
//		initRootLayout();		
	}
	
	
	public void initRootLayout(){
		try{
			loader = new FXMLLoader();
			loader.setLocation(MessageView.class.getResource("Message.fxml"));
			
			rootLayout = (AnchorPane) loader.load();			
			
			if(scene == null){
				scene = new Scene(rootLayout);
				System.out.println("SCENE == null");
			}
//			if(primaryStage.equals(null))
//				primaryStage = new Stage();
			
			System.out.println("PRIMARY STAGE:" + primaryStage.toString());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Message from RabbitMQ");
			
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();			
			primaryStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 450);
//			primaryStage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 300);
			primaryStage.setY(0);
			
			controller = loader.getController();			
			controller.setMainApp(this);
			
//			if(!message.equals(null)){
//				System.out.println("teste");
//				System.out.println(getMessage().toString());			
//				
//			}
			primaryStage.show();
			
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	
	            	Platform.runLater(new Runnable() {
						@Override
						public void run() {
							System.out.println("EXITING JAVAFX MESSAGE");
//							Platform.setImplicitExit(true);
//							SystrayApp.setMessageViewerNull();
//							Platform.exit();							
							stop();
														
						}
	            		
	            	});
	            	
	            	
	            	
	            	
	            }
	        }); 
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop(){
		Platform.setImplicitExit(true);
		Platform.exit();
//		((MessageController) loader.getController()).haltApplication();
	}
	
	public void setPrimaryStage(){
		
		
	}
	
	public void setDisplayMessage(DisplayMessage message){
		setMessage(message);
//		controller.setMainApp(this);
//		
//		primaryStage.show();
		initRootLayout();
	}
	
	

	public Stage getPrimaryStage() {
		return primaryStage;
	}


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	public AnchorPane getRootLayout() {
		return rootLayout;
	}


	public void setRootLayout(AnchorPane rootLayout) {
		this.rootLayout = rootLayout;
	}
	public DisplayMessage getMessage() {
		return this.message;
	}
	public void setMessage(DisplayMessage message) {
		this.message = message;
	}
	
	
	
	
	public void launchApp(){
		Application.launch(MessageView.class);
		
	}


	@Override
	public void update(Observable o, Object arg) {	
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				System.out.println("OBSERVER!");
				start(new Stage());
				
			}
			
		});
		
	}
	
	



//	public static void main(String[] args) {
////		launch(args);
//		
//		Message msg = new Message("Douglas Gatti", 1, 1, "Uma msg de teste" );
//		MainMessage mainMessage = new MainMessage();
//		mainMessage.setMessage(msg);
//		System.out.println(msg.toString());
//		
//		mainMessage.launchApp();
//
//
//	}
}
