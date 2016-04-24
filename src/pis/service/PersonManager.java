package pis.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import pis.data.Person;
import pis.data.Student;

@Stateless
public class PersonManager {
	@PersistenceContext
	private EntityManager em;
	
	public void save(Student student) {
		em.merge(student);
	}

	public Person findByLogin(String login) {
		try {
			return (Person)em.createQuery("SELECT p FROM Person p WHERE p.login = :val")
					.setParameter("val", login)
					.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		return (List<Person>)em.createQuery("SELECT p FROM Person p").getResultList();
	}
}
