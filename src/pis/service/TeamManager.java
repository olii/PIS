package pis.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import pis.data.Team;

@Stateless
public class TeamManager {
	@PersistenceContext
	private EntityManager em;
	
	public Team save(Team team) {
		return em.merge(team);
	}
	
	public void remove(Team team) {
		em.remove(em.merge(team));
	}
	
	public Team findById(int id) {
		try {
			return (Team)em.createQuery("SELECT t FROM Team t WHERE t.id = :id")
					.setParameter("id", id)
					.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
	
	public Team findByNameAndProject(String name, int projectId) {
		try {
			return (Team)em.createQuery("SELECT t FROM Team t WHERE t.name = :name AND t.project.id = :id")
					.setParameter("name", name)
					.setParameter("id", projectId)
					.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
}
