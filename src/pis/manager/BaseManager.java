package pis.manager;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
		return em.createQuery("SELECT a FROM " + tableName + " a WHERE a." + field + " = :val")
				.setParameter("val", value)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public T findOne(String tableName, String field, Object value) {
		return (T)em.createQuery("SELECT a FROM " + tableName + " a WHERE a." + field + " = :val")
				.setParameter("val", value)
				.getSingleResult();
	}
}
