package pis.back;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.service.PersonManager;

@ManagedBean
@ViewScoped
public class AccountBean {
	@EJB
	private PersonManager personMgr;
	private Person account;

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
	}
	
	public String saveAccount() {
		personMgr.save(account);
		return "account-saved";
	}
}
