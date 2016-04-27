package pis.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import pis.data.TeamStudent;

@Stateless
public class TeamStudentManager {
	@PersistenceContext
	private EntityManager em;
	
	public TeamStudent save(TeamStudent teamStudent) {
		return em.merge(teamStudent);
	}
	
	public void remove(TeamStudent teamStudent) {
		em.remove(em.merge(teamStudent));
	}
	
	public TeamStudent findByIds(int teamId, int studentId) {
		try {
			return (TeamStudent)em.createQuery("SELECT ts FROM TeamStudent ts WHERE ts.team.id = :tid AND ts.student.id = :sid")
				.setParameter("tid", teamId)
				.setParameter("sid", studentId)
				.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
}
