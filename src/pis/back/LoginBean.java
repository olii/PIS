package pis.back;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pis.data.Person;
import pis.manager.PersonManager;

@ManagedBean
@SessionScoped
public class LoginBean {
	private boolean loggedIn;
	private String login;
	private String password;
	@EJB
	private PersonManager personMgr;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		if (loggedIn) {
			return "error";
		}
		
		Person person = personMgr.findByLogin(this.login);
		if (person == null || !this.password.equals(person.getPassword())) {
			return "error";
		}
		
		loggedIn = true;
		return "ok";
	}
	
	public String logout() {
		if (!loggedIn) {
			return "error";
		}
		
		loggedIn = false;
		return "ok";
	}
}
