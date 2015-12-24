package br.com.celtab.openims.message.controller;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.stage.Stage;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


public class TrayNotificationApp extends Application implements Observer {
	
	private static Stage primaryStage;

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		setTrayNotification("Teste", "MENSAGEM DE TESTE", NotificationType.INFORMATION);
		
	}
	
	public void launchApp(){
		Application.launch(TrayNotificationApp.class);
	}
	
	public void setTrayNotification(String title, String msg, NotificationType type){
		
		TrayNotification tray = new TrayNotification(title, msg, type);
		tray.showAndWait();		
		System.out.println("setting tray notification...");
	}

	@Override
	public void update(Observable o, Object arg) {
		setTrayNotification("Teste2", "MENSAGEM 2" , NotificationType.ERROR);
		
	}

}
