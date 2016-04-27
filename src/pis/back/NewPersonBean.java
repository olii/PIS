package pis.back;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import pis.data.Person;
import pis.data.Project;
import pis.data.Student;
import pis.data.Teacher;
import pis.data.Admin;
import pis.service.PersonManager;

@ManagedBean
@ViewScoped
public class NewPersonBean {
	@EJB
	private PersonManager personMgr;
	
	private String login;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String type="student";
	
	public String getTitle() {
		return "New User";
	}
	
	public boolean isValidEmailAddress(String email) {
	   boolean result = true;
	   try {
	      InternetAddress emailAddr = new InternetAddress(email);
	      emailAddr.validate();
	   } catch (AddressException ex) {
	      result = false;
	   }
	   return result;
	}
	
	public boolean isAlphaNumeric(String s){
	    String pattern= "^[a-zA-Z0-9]*$";
	        if(s.matches(pattern)){
	            return true;
	        }
	        return false;   
	}
	
	public String create(Person account) {
		if (this.login.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter a login name", ""));
			return "error";
		}else if (name.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter a user name", ""));
			return "error";
		}else if (surname.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter a user surname", ""));
			return "error";
		} else if (email.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter a user mail", ""));
			return "error";
		}else if (password.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter a password", ""));
			return "error";
		}
		
		if (!isValidEmailAddress(email)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter a valid email address", ""));
			return "error";
		}
		
		if (!isAlphaNumeric(login)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter a valid login", ""));
			return "error";
		}
		
		Person person = personMgr.findByLogin(this.login);
		if (person != null ) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "User with this login already exist.", ""));
			return "error";
		}
		
		
		Person p;
		if ( type.equals("admin") ){
			p = new Admin();
		} else if ( type.equals("teacher") ){
			p = new Teacher();
		} else if ( type.equals("student") ){
			p = new Student();
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Type", ""));
			return "error";
		}
		
		p.setLogin(login);
		p.setMail(email);
		p.setName(name);
		p.setSurname(surname);
		p.setPassword(password);
		
		personMgr.save(p);
		//return "";
		
		return "accounts?faces-redirect=true";
		
	}
	
	
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
