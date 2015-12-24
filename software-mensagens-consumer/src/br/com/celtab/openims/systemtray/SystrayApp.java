package br.com.celtab.openims.systemtray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import br.com.celtab.openims.message.controller.MessageView;
import br.com.celtab.openims.message.controller.TrayNotificationApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.message.models.DisplayMessage;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.util.Observable;

public class SystrayApp extends Application{
	private static String imagePath = new String("images/message1.png");
	private static String gotMessageimagePath = new String("images/gotMessage1.png");
	private String systrayTooltip = new String("OpenIMS | 0 messages");
	private static TrayIcon trayIcon = null;
	private PopupMenu popupMenu = new PopupMenu();

	private static int messageListCounter;
	
	private static MessageView messageViewer = null;
	private static TrayNotificationApp app = null;
	
	private boolean started = false;
	
	  // application stage is stored so that it can be shown and hidden based on system tray icon operations.
	private Stage primaryStage;

	List<DisplayMessage> messageList = new ArrayList<DisplayMessage>();

	public SystrayApp() {	
//		init();
	}
	
	public static void launchApp(){
		Application.launch(SystrayApp.class);
	}
	
//	public static void main(String[] args){
//		launch(args);
//	}
	
	public boolean isStarted(){
		return started;
	}
	
	public void setStarted(boolean started){
		this.started = started;
	}
	
	public void initApplication(){
		System.out.println("INIT JAVAFX APP...");
		// instructs the javafx system not to exit implicitly when the last application window is shut.
		Platform.setImplicitExit(false); 
		
		setupSystemTray();
		minimizeToTray();
		setStarted(true);
		
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("INIT JAVAFX APP...");
		initApplication();
	}
	
	
	public void setupSystemTray() {
		this.popupMenu = new PopupMenu();

		MenuItem menuitemExit = new MenuItem("Exit");
		menuitemExit.addActionListener(exitListener);

		if (this.trayIcon == null)
			this.trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(imagePath), systrayTooltip, popupMenu);
		else
			this.trayIcon.setPopupMenu(popupMenu);

//		trayIcon.addActionListener(trayIconListener);
		trayIcon.addActionListener(showMessageActionListener);
		// popupMenu.add(menuMessages);
		popupMenu.add(menuitemExit);
	}

	ActionListener showMessageActionListener = new ActionListener() {		
		@Override
		public void actionPerformed(ActionEvent e) {
			showMessages();	
		}
	};
	
	public int countNewMessages() {
		int newMessages = 0;

		for (DisplayMessage displayMessage : messageList) {
			if (displayMessage.getVisualized() == false)
				newMessages++;
		}

		return newMessages;

	}

	public void showMessages() {
		Collections.sort(messageList, new Comparator<DisplayMessage>() {
			@Override
			public int compare(DisplayMessage msg1, DisplayMessage msg2) {
				return msg1.getVisualized().compareTo(msg2.getVisualized());
			}
		});

		if (countNewMessages() > 0) {
			
			DisplayMessage displayMessage = messageList.get(0);
//			trayIcon.displayMessage(displayMessage.getCaption(), displayMessage.getMessage(),displayMessage.getMessageType());
			
			new JFXPanel();
			
			Platform.runLater(new Runnable() {				
				@Override
				public void run() {
					TrayNotification trayNotification = new TrayNotification(displayMessage.getTitle(), displayMessage.getMessage(), displayMessage.getType());
					trayNotification.setAnimationType(AnimationType.SLIDE);
					trayNotification.showAndWait();
					
				}
			});
			
			if (displayMessage.getVisualized().equals(Boolean.FALSE)) {
				messageList.get(0).setVisualized(Boolean.TRUE);
				setTooltipMessages();
				if(countNewMessages() == 0){
					trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(imagePath));
				}
			}
		} else {
			trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(imagePath));
			trayIcon.displayMessage("No Messages:", "You have no new messages!", MessageType.INFO);
		}

	}
	
	public void setTrayNotification(String title, String msg, NotificationType type){	
		TrayNotification tray = new TrayNotification(title, msg, type);
		tray.showAndWait();		
		System.out.println("setting tray notification...");
	}

	public void printMessageList() {
		for (DisplayMessage msg : messageList) {
			System.out.println(msg);
		}
	}

	public void setTooltipMessages() {
		String tooltip = new String("OpenIMS | You have " + countNewMessages() + " new messages!");

		if (this.trayIcon == null)
			this.trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(imagePath), tooltip, popupMenu);
		else
			this.trayIcon.setToolTip(tooltip);
	}

	public void setMessage(DisplayMessage message) throws InterruptedException {
		trayIcon.setImage(Toolkit.getDefaultToolkit().getImage(gotMessageimagePath));
		messageList.add(message);
		setTooltipMessages();

		// setPopupMenu();

		//trayIcon.displayMessage(caption, message, messageType);
	}
	
	public void exitApplication(){
		Platform.exit();
		removeTrayIcon();
		System.exit(0);
	}

	private void minimizeToTray(){
		System.out.println("ADDING SYSTEM TRAY ICON!!!");
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		final SystemTray systray = SystemTray.getSystemTray();
		// this.trayIcon.setPopupMenu(this.popupMenu);

		try {
			systray.add(trayIcon);

		} catch (AWTException ex) {
			System.out.println(ex.toString() + " TrayIcon could not be added.");
		}
		
	}


	
	ActionListener exitListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			removeTrayIcon();
			System.exit(0);
		}
	};
	// ActionListener showListener = new ActionListener() {
	//
	// public void actionPerformed(ActionEvent e) {
	// System.out.println(e.getSource().toString());
	// MenuItem menuItem = (MenuItem) e.getSource();
	// System.out.println(menuItem.getName());
	//
	// DisplayMessage displayMessage = new DisplayMessage(); // =
	// getDisplayMessage();
	// trayIcon.displayMessage(displayMessage.getCaption(),
	// displayMessage.getMessage(),
	// displayMessage.getMessageType());
	//
	// }
	// };

	ActionListener trayIconListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			showMessages();
		}

	};

	public void removeTrayIcon() {
		try {
			SystemTray.getSystemTray().remove(this.trayIcon);
		} catch (Throwable x) {
			System.err.println("Disabling tray support.");
		}
	}

	public static String getImagePath() {
		return imagePath;
	}

	public static void setImagePath(String imagePath) {
		SystrayApp.imagePath = imagePath;
	}

	public static TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public static void setTrayIcon(TrayIcon trayIcon) {
		SystrayApp.trayIcon = trayIcon;
	}


}