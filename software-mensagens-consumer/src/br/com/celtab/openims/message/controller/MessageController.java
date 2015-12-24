package br.com.celtab.openims.message.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class MessageController{
    @FXML
    private TextField txtSentBy;

    @FXML
    private TextField txtSeverity;

    @FXML
    private TextArea txtMessage;
    
    //referencia para a applicacao
    private MessageView mainMsg;
      

	public MessageController(){
	}
    
    @FXML
    private void initialize(){

    }
    
    public void setMainApp(MessageView mainMessage){
    	this.mainMsg = mainMessage; //seta o main view para este controller
    	
//    	this.txtSentBy.setText(mainMsg.getMessage().getSentBy());
//    	this.txtSeverity.setText(String.valueOf(mainMsg.getMessage().getMessageType()));
//    	this.txtMessage.setText(mainMsg.getMessage().getMessage());
       	this.txtSentBy.setText("Douglas");
    	this.txtSeverity.setText("IMPORTANT");
    	this.txtMessage.setText("MSG 1");
    }
    
      
    public void haltApplication(){
    	Platform.exit();
    }
  
}
