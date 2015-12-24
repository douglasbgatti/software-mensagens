package br.com.celtab.openims.app;

import java.util.List;

import br.com.celtab.openims.amqp.ChannelPublish;
import br.com.celtab.openims.amqp.ConnectFactory;
import br.com.celtab.openims.entity.Groups;
import br.com.celtab.openims.entity.Message;



public class AppProducer {
	private static ConnectFactory factory = null;
	
	
	public void publish(Message message){
		this.factory = new ConnectFactory();
		ChannelPublish channelPublish = new ChannelPublish(factory.getChannel());
		channelPublish.declareExchanges(message.getDestination());
		String routingKey = "#";
		channelPublish.sendMessage(message, message.getDestination(), routingKey);
		
		factory.closeConnection();
	}
	
	public static void main(String[] args){
//		
//		ConnectFactory factory = new ConnectFactory();
//		Exchange exchangeGroups = new Exchange();
//		ChannelPublish channelPublish = new ChannelPublish(factory.getChannel(), exchangeGroups.getDummyExchange());
//		
//		
//		channelPublish.sendMessage(exchangeGroups.getDummyExchange(), new String("THIS IS MESSAGE 1"), "#");
//		channelPublish.sendMessage(exchangeGroups.getDummyExchange(), new String("THIS IS MESSAGE 2"), "#");
//		channelPublish.sendMessage(exchangeGroups.getDummyExchange(), 
//				new DisplayMessage("Douglas Gatti", "Mensagem Importante", "important", "Esta Mensagem é de extrema importancia", NotificationType.NOTICE), "#");
//		
//		channelPublish.sendMessage(exchangeGroups.getDummyExchange(), 
//				new DisplayMessage("Suporte", "informacao", "", "Esta Mensagem é uma mensagem de INFORMACAO", NotificationType.INFORMATION), "#");
//		
//		channelPublish.sendMessage(exchangeGroups.getDummyExchange(), 
//				new DisplayMessage("Suporte", "ERRO", "", "Esta Mensagem é uma mensagem de ERRO", NotificationType.ERROR), "#");
//		
//		channelPublish.sendMessage(exchangeGroups.getDummyExchange(), 
//				new DisplayMessage("Suporte", "SUCESS", "", "Esta Mensagem é uma mensagem de SUCESSO", NotificationType.SUCCESS), "#");
//		
//		channelPublish.sendMessage(exchangeGroups.getDummyExchange(), 
//				new DisplayMessage("Suporte", "WARNING", "", "Esta Mensagem é uma mensagem de WARNING", NotificationType.WARNING), "#");
		
		
		
	}

}
