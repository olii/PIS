package pis.manager;

import java.util.List;

import javax.ejb.Stateless;

import pis.data.Person;

@Stateless
public class PersonManager extends BaseManager<Person> {
	public List<Person> findAll() {
		return findAll("Person");
	}
	
	public Person findByLogin(String login) {
		return findOne("Person", "login", login);
	}
}
