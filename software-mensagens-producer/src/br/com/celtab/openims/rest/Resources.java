package br.com.celtab.openims.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.celtab.openims.app.AppProducer;
import br.com.celtab.openims.dao.ConsumerDAO;
import br.com.celtab.openims.dao.GenericDAOImpl;
import br.com.celtab.openims.entity.Consumer;
import br.com.celtab.openims.entity.Groups;
import br.com.celtab.openims.entity.Message;



@Path("/resources")
public class Resources {
	private GenericDAOImpl<Groups, Long> groupsDAO = new GenericDAOImpl<Groups,Long>(Groups.class);
	private GenericDAOImpl<Message, Long> messageDAO = new GenericDAOImpl<Message,Long>(Message.class);
	private ConsumerDAO consumerDAO = new ConsumerDAO();
	

	public Resources() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Path("/getMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@QueryParam("id") Long id) {
		System.out.println("GETMESSAGE...." + id);
		
		Message message = new Message();
		messageDAO.startEntityManager();
		message = messageDAO.get(id);
		messageDAO.commitTransaction();
		return message;
	}

	@POST
	@Path("/sendMessage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message sendMessage(Message message) {
		System.out.println("SENDMESSAGE()....");
		System.out.println(message.toString());
		message.setId(null);
		
		
		messageDAO.startEntityManager();
		groupsDAO.startEntityManager();

//		for (int i = 0; i < message.getDestination().size(); i++) {
//			message.getDestination().set(i, groupsDAO.get(message.getDestination().get(i).getId())); //referencia os objetos de grupos na memoria JPA 
//																									// se nao o hibernate nao sabe se existe e tenta persistir denovo com o mesmo ID ja existente
//		}
		
//		for (Iterator iterator = message.getDestination().iterator(); iterator.hasNext();) {
//			Groups group = (Groups) iterator.next();
//			group = groupsDAO.get(group.getId());
//			System.out.println(group);
//			
//		}
		
		message = messageDAO.save(message);
		
		groupsDAO.commitTransaction();
		messageDAO.commitTransaction();
		
		AppProducer app = new AppProducer();
		app.publish(message);

		return message;
	}
	

	
	@GET
	@Path("/getAllGroups")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Groups> getAllGroups(){
		groupsDAO.startEntityManager();
		List<Groups> list = groupsDAO.findAll();
//		this.groupsDAO.commitTransaction();
		
		return list;
		
	}
	
	@GET
	@Path("/getAllMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessages(){
		messageDAO.startEntityManager();
		List<Message> list = messageDAO.findAll();
		
		return list;
	}
	
	
	@GET
	@Path("/getRecentMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getRecentMessages(){
		messageDAO.startEntityManager();
		List<Message> list = messageDAO.findByNamedQuery("latestMessages", 10);	
			
		return list;
	}
	
	@POST
	@Path("/addNewGroup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Groups> addNewGroup(Groups group){		
		groupsDAO.startEntityManager();
		if(group.getId() == 0){ ///se for um group novo
			Groups newGroup = new Groups(group.getTitle());
			newGroup = groupsDAO.save(newGroup);
		}
		else{ //se for um subgrupo
			Groups g = groupsDAO.get(group.getId());
			Groups newGroup = new Groups(group.getTitle());
			newGroup = groupsDAO.save(newGroup);			
			g.addNodes(newGroup);
		}
		groupsDAO.commitTransaction();
		
		
		return getAllGroups();
	}
	
	@POST
	@Path("/addNewConsumer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Consumer addNewConsumer(Consumer consumer){
		consumer = consumerDAO.saveConsumer(consumer);	
		 return consumer;
	}
	
	@GET
	@Path("/checkUniqueConsumer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkUniqueConsumer(Consumer consumer){
		boolean unique = true;
		
		consumerDAO.startEntityManager();
		if(! consumerDAO.getConsumer(consumer).equals(null))
			unique = false;
		
		return unique;
	}
	
	@GET
	@Path("/getAllConsumers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Consumer> getAllConsumers(){
		consumerDAO.startEntityManager();
		List<Consumer> list = consumerDAO.findAll();
		
		return list;
	}
	
	
	@GET
	@Path("/getConsumer")
	@Produces(MediaType.APPLICATION_JSON)
	public Consumer getConsumer(@QueryParam("id") Long id){
		consumerDAO.startEntityManager();
		Consumer consumer = consumerDAO.get(id);
		
		return consumer;
	}
	
	
	@OPTIONS
	@Path("{path : .*}")
	public Response options() {
	    return Response.ok("")
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .build();
	}
	

}
