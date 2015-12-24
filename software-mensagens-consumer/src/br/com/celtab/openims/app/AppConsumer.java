package br.com.celtab.openims.app;

import java.io.IOException;
import java.net.InetAddress;

import br.com.celtab.openims.amqp.ChannelConsume;
import br.com.celtab.openims.amqp.ConnectFactory;
import br.com.celtab.openims.dao.ConsumerDAO;
import br.com.celtab.openims.entity.Consumer;
import br.com.celtab.openims.groups.Exchange;

public class AppConsumer {

	public static void main(String[] args) throws IOException {
		ConnectFactory factory = new ConnectFactory();
		
		//sets up the consumer initializing the app getting the user logged on the OS
		Consumer consumer = new Consumer();
		consumer.setUsername(System.getProperty("user.name"));
		
		ConsumerDAO consumerDAO = new ConsumerDAO();
		consumer = consumerDAO.getConsumer(consumer);
		
		
		Exchange exchangeGroups = new Exchange();
		ChannelConsume channelConsume = new ChannelConsume(
				factory.getChannel(), consumer.getGroups());
		
		
//		System.out.println("Current User:" + System.getProperty("user.name"));	
//		InetAddress localMachine = InetAddress.getLocalHost();
//		System.out.println("Hostname: " + localMachine.getHostName());

		channelConsume.consume();

	}

}
