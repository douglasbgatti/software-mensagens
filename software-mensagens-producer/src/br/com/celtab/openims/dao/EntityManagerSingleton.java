package br.com.celtab.openims.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {
	
	private static EntityManager em;
	
	public EntityManagerSingleton(){}
	
	public static EntityManager getInstance(){
		if(em == null){
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnit");
			em = emf.createEntityManager();
		}
		
		return em;
	}

}
