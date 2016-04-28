package pis.back;

import java.util.Map;

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
public class AccountBean {
	@EJB
	private PersonManager personMgr;
	private Person account;
	private String mail;

	public Person getAccount() {
		return account;
	}

	public void setAccount(Person account) {
		this.account = account;
	}
	
	@PostConstruct
	public void init() {
		Map<String, String> getParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		int accountId = Integer.parseInt(getParams.get("id"));
		account = personMgr.findById(accountId);
		mail = account.getMail();
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

	public String saveAccount() {
		if (isValidEmailAddress(mail)){
			account.setMail(mail);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email address", ""));
			return "";
		}
		
		personMgr.save(account);
		return "account-saved";
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
