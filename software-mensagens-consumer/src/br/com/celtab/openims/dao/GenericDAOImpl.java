package br.com.celtab.openims.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;



public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAOInterface<T, ID> {
	   /** logback Logger for class GenericDAOJPA */
//    private static Logger logger = LoggerFactory.getLogger(GenericDAOInterface.class.getName());

	private final Class<T> persistentClass;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager(){
		return this.entityManager;
	}

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) 
			getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public GenericDAOImpl(final Class<T> persistentClass) {
		super();
		this.persistentClass = persistentClass;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#countAll()
	 */
	@Override
	public int countAll() {
		return countByCriteria();
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#countByExample(java.lang.Object)
	 */
	@Override
	public int countByExample(final T exampleInstance) {
		
		Session session = (Session) getEntityManager().getDelegate();
		Criteria crit = session.createCriteria(getEntityClass());
		crit.setProjection(Projections.rowCount());
		crit.add(Example.create(exampleInstance));

		return (Integer) crit.list().get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#findAll()
	 */
	@Override
	public List<T> findAll() {
		return findByCriteria();
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#findByExample(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByExample(final T exampleInstance) {
		Session session = (Session) getEntityManager().getDelegate();
		Criteria crit = session.createCriteria(getEntityClass());
		final List<T> result = crit.list();
		return result; 
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#findById(java.io.Serializable)
	 */
	@Override
	public T get(final ID id) {
		final T result = getEntityManager().find(persistentClass, id);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.ç#findByNamedQuery(java.lang.String, java.lang.Object[])
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(final String name, int maxResults, Object... params) {
		
		Query query = getEntityManager().createNamedQuery(name);
		query.setMaxResults(maxResults);

		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}

		final List<T> result = (List<T>) query.getResultList();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#findByNamedQueryAndNamedParams(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQueryAndNamedParams(final String name, final Map<String, ? extends Object> params) {
		
		Query query = getEntityManager().createNamedQuery(name);

		for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		final List<T> result = (List<T>) query.getResultList();
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#save(java.lang.Object)
	 */
	@Override
	@Transactional
	public T save(T entity) {

        T savedEntity;

        // Approximate the Hibernate saveOrUpdate functionality
        if (getEntityManager().contains(entity) ) {
            savedEntity = getEntityManager().merge(entity);
        } else {
            getEntityManager().persist(entity);
            savedEntity = entity;
        }
		return savedEntity;
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#delete(java.lang.Object)
	 */
	@Override
	public void remove(T entity) {
		getEntityManager().remove(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.repository.GenericDAO#remove(java.io.Serializable)
	 */
	@Override
	public void remove(ID id) {
		getEntityManager().remove(this.get(id));
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.firebyte.dao.GenericDAO#getEntityClass()
	 */
	@Override
	public Class<T> getEntityClass() {
		return persistentClass;
	}

	/**
	 * Set the JPA entity manager to use.
	 *
	 * @param entityManager
	 */
	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

//	public EntityManager getEntityManager() {
//		return entityManager;
//	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	protected List<T> findByCriteria(final Criterion... criterion) {
		return findByCriteria(-1, -1, criterion);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(final int firstResult, final int maxResults, final Criterion... criterion) {
		
		Session session = (Session) getEntityManager().getDelegate();
		Criteria crit = session.createCriteria(getEntityClass());

		for (final Criterion c : criterion) {
			crit.add(c);
		}

		if (firstResult > 0) {
			crit.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			crit.setMaxResults(maxResults);
		}

		final List<T> result = crit.list();
		return result;
	}

	/**
	 * Get counts by Criteria
	 * 
	 * @param criterion
	 * @return int
	 */
	protected int countByCriteria(Criterion... criterion) {
		
		Session session = (Session) getEntityManager().getDelegate();
		
		Criteria crit = session.createCriteria(getEntityClass());
		crit.setProjection(Projections.rowCount());

		for (final Criterion c : criterion) {
			crit.add(c);
		}

		return (Integer) crit.list().get(0);
	}
	
	
	@Override
	public void startEntityManager() {
		this.entityManager = Persistence.createEntityManagerFactory("MessagePersistenceUnit").createEntityManager();
		this.entityManager.getTransaction().begin();	
		
	}

	@Override
	public void commitTransaction() {
		try{
			getEntityManager().getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			entityManager.close();
		}
				
	}
	
	
	
	public static void main(String [] args){
//		GenericDAOImpl<Message, Long> messageDao = new GenericDAOImpl<Message, Long>(Message.class);
//		GenericDAOImpl<Groups, Long> groupDao = new GenericDAOImpl<Groups, Long>(Groups.class);
//		EntityManager entityManager = Persistence.createEntityManagerFactory("OpenIMSPersistenceUnit")
//				.createEntityManager();
//		entityManager.getTransaction().begin();
//		
////		groupDao.setEntityManager(entityManager);
////		groupDao.save(new Groups("Groups1"));
////		groupDao.save(new Groups("Groups"));
//		
//		
//		messageDao.setEntityManager(entityManager);
////		
////		
////		List<Groups> groups = new ArrayList<>();
////		groups.add(new Groups("Group1"));
////		groups.add(new Groups("Group2"));
////		
////		
//		Message message = new Message();
//		message.setType("Important");
//		message.setTitle("teste");
//		message.setMessageBody("THIS IS A MESSAGE!");
////		message.setGroups(groups);
//		
//		message = messageDao.save(message);
		

		
	
		
	}


	
}
