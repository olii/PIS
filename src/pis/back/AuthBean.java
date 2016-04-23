package pis.back;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.data.Student;
import pis.data.Teacher;
import pis.service.PersonManager;

@ManagedBean
@SessionScoped
public class AuthBean {
	private boolean loggedIn;
	private String login;
	private String password;
	private Person account;
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

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public Person getAccount() {
		return account;
	}
	
	public boolean isStudent() {
		return account instanceof Student;
	}
	
	public boolean isTeacher() {
		return account instanceof Teacher;
	}

	public String performLogin() {
		if (loggedIn) {
			return "error";
		}

		Person person = personMgr.findByLogin(this.login);
		if (person == null || !password.equals(person.getPassword())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", ""));
			return "error";
		}
		
		account = person;
		loggedIn = true;
		return "login";
	}
	
	public String performLogout() {
		if (!loggedIn) {
			return "error";
		}

		login = "";
		password = "";
		account = null;
		loggedIn = false;
		return "logout";
	}
}
