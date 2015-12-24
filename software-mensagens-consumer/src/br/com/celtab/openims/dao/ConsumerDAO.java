package br.com.celtab.openims.dao;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import br.com.celtab.openims.entity.Consumer;
import br.com.celtab.openims.entity.Groups;


public class ConsumerDAO extends GenericDAOImpl<Consumer, Long>{
	
	
	public ConsumerDAO(){
		super(Consumer.class);
	}
	
	public Consumer getConsumer(Consumer consumer){
		startEntityManager();
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Consumer> criteriaQuery = criteriaBuilder.createQuery(Consumer.class);
		Root<Consumer> cons = criteriaQuery.from(Consumer.class);
		
		criteriaQuery.select(cons);
		
		criteriaQuery.where(
				criteriaBuilder.equal(cons.get("username"), consumer.getUsername())
				);

//		criteriaQuery.where(
//		criteriaBuilder.equal(cons.get("username"), criteriaBuilder.parameter(String.class, "username"))
//		);
		
		Query query = getEntityManager().createQuery(criteriaQuery);
//		query.setParameter("username", consumer.getUsername());
		
		consumer = (Consumer) query.getSingleResult();
		
		System.out.println(consumer);
		
		return consumer;
	}
	
	
	public Consumer saveConsumer(Consumer consumer){
		
		startEntityManager();;
		consumer = save(consumer);
		commitTransaction();
		
		return consumer;
		
	}
	

	
	
	public static void main(String [] args){
		ConsumerDAO cDAO = new ConsumerDAO();
//		GenericDAOImpl<Groups, Long> groupsDAO = new GenericDAOImpl<Groups,Long>(Groups.class);
		
		Consumer consumer = new Consumer();
		consumer.setUsername("douglas");
		
		consumer = cDAO.getConsumer(consumer);
		
		
//		Groups g1 = new Groups();
//		g1.setTitle("Group_1");
//		
//		
//		Groups g2 = new Groups();
//		g2.setTitle("Group_2");
//		
//		groupsDAO.startEntityManager();
//		g1 = groupsDAO.save(g1);
//		g2 = groupsDAO.save(g2);
//		groupsDAO.commitTransaction();
//		
//		consumer.getGroups().add(g1);
//		consumer.getGroups().add(g2);
//		
//
//		consumer = cDAO.saveConsumer(consumer);
		
		
//		System.out.println(consumer);
		
		
	}

}
