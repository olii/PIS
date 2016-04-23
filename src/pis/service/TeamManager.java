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
	
	public void save(Team team) {
		em.merge(team);
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
}
