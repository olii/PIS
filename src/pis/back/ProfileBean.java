package pis.back;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.service.PersonManager;

@ManagedBean
@ViewScoped
public class ProfileBean {
	@EJB
	private PersonManager personMgr;
	private Person account;
	private String newPassword;
	
	public Person getAccount() {
		return account;
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
	}

	public String saveProfile() {
		if (!newPassword.equals("")) {
			account.setPassword(newPassword);
		}
		
		personMgr.save(account);
		return "profile-saved";
	}
}
