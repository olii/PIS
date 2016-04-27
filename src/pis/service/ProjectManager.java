package pis.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import pis.data.Project;


@Stateless
public class ProjectManager {
	@PersistenceContext
	private EntityManager em;
	
	public Project save(Project project) {
		return em.merge(project);
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
	
	@SuppressWarnings("unchecked")
	public List<Project> getAll() {
		return em.createQuery("SELECT p FROM Project p").getResultList();
	}
}
