package pis.back;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import pis.data.Person;
import pis.service.PersonManager;

@ManagedBean
@ViewScoped
public class ProfileBean {
	@EJB
	private PersonManager personMgr;
	private Person account;
	private String newPassword;
	
	private String email;
	
	public Person getAccount() {
		return account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAccount(Person account) {
		this.account = account;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@PostConstruct
	public void init() {
		AuthBean authBean = (AuthBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authBean");
		account = authBean.getAccount();
		newPassword = "";
		email = account.getMail();
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
	
	public String saveProfile() {
		if (!newPassword.equals("")) {
			account.setPassword(newPassword);
		}
		
		if (isValidEmailAddress(email)){
			account.setMail(email);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email address", ""));
			return "";
		}
		
		
		
		personMgr.save(account);
		return "profile-saved";
	}
}
