package pis.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class BaseManager<T> {
	@PersistenceContext
	protected EntityManager em;
	
	public void save(T obj) {
		em.merge(obj);
	}
	
	public void remove(T obj) {
		em.remove(em.merge(obj));
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(String tableName) {
		return em.createQuery("SELECT a FROM " + tableName + " a").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(String tableName, String field, Object value) {
		try {
			return em.createQuery("SELECT a FROM " + tableName + " a WHERE a." + field + " = :val")
					.setParameter("val", value)
					.getResultList();
		}
		catch (NoResultException ex) {
			return new ArrayList<T>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public T findOne(String tableName, String field, Object value) {
		try {
			return (T)em.createQuery("SELECT a FROM " + tableName + " a WHERE a." + field + " = :val")
					.setParameter("val", value)
					.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
}