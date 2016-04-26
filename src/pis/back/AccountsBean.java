package pis.back;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pis.data.Person;
import pis.service.PersonManager;

@ManagedBean
@ViewScoped
public class AccountsBean {
	@EJB
	private PersonManager personMgr;
	private List<Person> accounts;

	public List<Person> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Person> accounts) {
		this.accounts = accounts;
	}
	
	@PostConstruct
	public void init() {
		accounts = personMgr.findAll();
	}
}
