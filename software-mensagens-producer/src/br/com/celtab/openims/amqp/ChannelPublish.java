package br.com.celtab.openims.amqp;

import java.io.IOException;
import java.util.List;

import com.rabbitmq.client.Channel;

import br.com.celtab.openims.entity.Groups;
import br.com.celtab.openims.entity.Message;
import tray.message.models.DisplayMessage;
import tray.notification.NotificationType;



public class ChannelPublish {
	private List<Groups> groups;
	private Channel channel;

	public ChannelPublish(Channel channel) {
		this.channel = channel;
	}

	public void declareExchanges(List<Groups> groups) {
		boolean durable = true;
		try {
			for (Groups group: groups) {
				if (!group.equals(null) && !group.getTitle().equals("")){
					System.out.println(group);
					this.channel.exchangeDeclare(group.getTitle(),
							"topic", durable);

					System.out.println("Declaring exchange:" + group.getTitle());
				}

				if (!group.getNodes().equals(null)) {
					declareExchanges(group.getNodes()); // recursivo
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public void sendMessage(List<Exchange> exchangeGroups, String message,
//			String routingKey) {
//		int n = 0;
//		try {
//			for (Exchange exchange : exchangeGroups) {
//				if (!exchange.equals(null) && !exchange.getName().equals("")){
//					this.channel.basicPublish(exchange.getName(), routingKey,
//							null, message.getBytes());
//					n++;
//					System.out.println("Sending msg :" + n);
//				}
//
//				if (!exchange.getSubExchangeGroups().equals(null)) {
//					sendMessage(exchange.getSubExchangeGroups(), message,
//							routingKey); // recursivo se houver subgrupos
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	public void sendMessage(Message message, List<Groups> groups, String routingKey) {
		int n = 0;
		try {
			System.out.println("SENDING MESSAGE...");
			NotificationType type = null;
			switch (message.getType()) {
			case "Information":
				type = NotificationType.INFORMATION;
				break;
			case "Notice":
				type = NotificationType.NOTICE;
				break;
			case "Warning":
				type = NotificationType.WARNING;
				break;
			case "Success":
				type = NotificationType.SUCCESS;
				break;
			case "Error":
				type = NotificationType.ERROR;
				break;
			default:
				break;
			}
			
			DisplayMessage dispMessage = new DisplayMessage("Suporte", message.getTitle(), "", message.getMessage(), type);
			
			for (Groups group: groups) {
				if (!group.equals(null) && !group.getTitle().equals("")){
					if(!this.channel.isOpen()){
						this.channel = ConnectFactory.restartChannel();
					}
					this.channel.basicPublish(group.getTitle(), routingKey,
							null, dispMessage.serialize() );					
					
					n++;
					System.out.println("Sending msg to:" + group.getTitle());
				}

				if (!group.getNodes().equals(null) && (group.getNodes().size() > 0) ) {
					sendMessage(message, group.getNodes(), routingKey); // recursivo se houver subgrupos
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Groups> getExchangeGroups() {
		return groups;
	}

	public void setExchangeGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public Channel getChannel() {
		return channel;
	}

//	public void setChannel(Channel channel) {
//		this.channel = channel;
//	}

}
