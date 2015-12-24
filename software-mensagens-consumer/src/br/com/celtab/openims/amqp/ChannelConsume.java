package br.com.celtab.openims.amqp;


import java.io.IOException;
import java.util.List;

import com.rabbitmq.client.*;

import br.com.celtab.openims.entity.Groups;
import br.com.celtab.openims.groups.Exchange;
import br.com.celtab.openims.systemtray.SystrayApp;
import tray.message.models.DisplayMessage;
import tray.notification.NotificationType;

public class ChannelConsume {
	private List<Exchange> exchangeGroups;
	private List<Groups> groups;
	private Channel channel;
	private String queue;
	
	private SystrayApp systrayApp;

	public ChannelConsume(Channel channel, List<Groups> groups) {
		this.channel = channel;
		this.groups = groups;

		declareGroups(this.groups);
		String routingKey = "#";
		queueBind(this.groups, routingKey);
		
//		systrayApp = new SystrayApp();
	}
	
	//declara os grupos em que o usuario faz parte ...caso ainda não exista no broker
	public void declareGroups(List<Groups> groups){
		try {
			boolean durable = true;
			for (Groups group: groups) {
				if (!group.equals(null) && !group.getTitle().equals("")) {
					System.out.println(group);
					this.channel.exchangeDeclare(group.getTitle(),
							"topic", durable);
				}
				if (!group.getNodes().equals(null)) {
					declareGroups(group.getNodes()); 					// recursivo se houver subgroups																		
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void queueBind(List<Groups> groups, String routingKey) {
		try {
			if (queue == null || queue.equals("")) {

				queue = channel.queueDeclare().getQueue();
				

			}
			for (Groups group: groups) {
				if (!group.equals(null) && !group.getTitle().equals("")) {
					this.channel.queueBind(queue, group.getTitle(),
							routingKey);
					System.out.println("[QUEUE BINDING] :" + queue
							+ group.getTitle());
				}

				if (!group.getNodes().equals(null)) {
					queueBind(group.getNodes(), routingKey); 				// recursivo
																			// se
																			// houver
																			// subgrupos
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void consume() throws IOException{
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queue, true, consumer);
		

		systrayApp = new SystrayApp();
		systrayApp.initApplication();

		System.out.println("SYSTEM TRAY APP STARTED? : " + systrayApp.isStarted());
		while (true) {
			try {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();

				DisplayMessage message = new DisplayMessage();
				try {
					message = DisplayMessage.deserialize(delivery.getBody());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				String msg = new String(delivery.getBody());
				String routingKey = delivery.getEnvelope().getRoutingKey();

				String msgReceived = new String("--[#] RECEIVED: '" + routingKey + "':'"
						+ message.toString() + "';");

				
				if(systrayApp.isStarted()){
					
					systrayApp.setMessage(message);			
				}
				
			} catch (ShutdownSignalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConsumerCancelledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Exchange> getExchangeGroups() {
		return exchangeGroups;
	}

	public void setExchangeGroups(List<Exchange> exchangeGroups) {
		this.exchangeGroups = exchangeGroups;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

}
