package pis.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import pis.data.Subject;

@Stateless
public class SubjectManager {
	@PersistenceContext
	private EntityManager em;
	
	public void save(Subject subject) {
		em.merge(subject);
	}
	
	public Subject findById(int id) {
		try {
			return (Subject)em.createQuery("SELECT s FROM Subject s WHERE s.id = :id")
				.setParameter("id", id)
				.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Subject> getAll() {
		return em.createQuery("SELECT s FROM Subject s").getResultList();
	}
}
