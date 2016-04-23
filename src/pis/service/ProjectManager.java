package pis.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import pis.data.Project;

@Stateless
public class ProjectManager {
	@PersistenceContext
	private EntityManager em;
	
	public void save(Project project) {
		em.merge(project);
	}
	
	public Project findById(int id) {
		try {
			return (Project)em.createQuery("SELECT p FROM Project p WHERE p.id = :id")
				.setParameter("id", id)
				.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
}
