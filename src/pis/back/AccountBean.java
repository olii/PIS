package pis.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.primefaces.model.DualListModel;

import pis.data.Person;
import pis.data.Student;
import pis.data.Subject;
import pis.data.Teacher;
import pis.data.TeamStudent;
import pis.service.PersonManager;
import pis.service.SubjectManager;
import pis.service.TeamManager;
import pis.service.TeamStudentManager;

@ManagedBean
@ViewScoped
public class AccountBean {
	@EJB
	private PersonManager personMgr;
	@EJB
	private SubjectManager subjectMgr;
	@EJB
	private TeamManager teamMgr;
	@EJB
	private TeamStudentManager teamStudentMgr;
	private Person account;
	private String mail;
	private DualListModel<Subject> subjects;

	public Person getAccount() {
		return account;
	}

	public void setAccount(Person account) {
		this.account = account;
	}

	public DualListModel<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(DualListModel<Subject> subjects) {
		this.subjects = subjects;
	}

	@PostConstruct
	public void init() {
		Map<String, String> getParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		int accountId = Integer.parseInt(getParams.get("id"));
		account = personMgr.findById(accountId);
		mail = account.getMail();

		List<Subject> origSubjects = subjectMgr.findAll();
		List<Subject> chosenSubjects = new ArrayList<Subject>();
		
		if (account.isTeacher()) {
			chosenSubjects = ((Teacher)account).getTeachedSubjects();
		}
		else if (account.isStudent()) {
			chosenSubjects = ((Student)account).getEnrolled();
		}
		
		for (Subject subject : chosenSubjects) {
			origSubjects.removeIf(s -> s.getId() == subject.getId());
		}
		
		subjects = new DualListModel<Subject>(origSubjects, chosenSubjects);
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

		if (hasSubjects()) {
			if (account.isTeacher())
				((Teacher)account).setTeachedSubjects(new ArrayList<Subject>());
			else
				((Student)account).setEnrolled(new ArrayList<Subject>());
			personMgr.save(account);
			
			List<Subject> allSubjects = subjectMgr.findAll();
			for (Subject subject : allSubjects) {
				if (account.isTeacher())
					subject.getTeachers().removeIf(t -> t.getId() == account.getId());
				else
					subject.getStudents().removeIf(s -> s.getId() == account.getId());
			
				subjectMgr.save(subject);
			}
			
			if (account.isStudent()) {
				Student student = (Student)account;
				for (Subject subject : subjects.getSource()) {
					List<TeamStudent> toRemove = new ArrayList<TeamStudent>();
					for (TeamStudent member : student.getTeams()) {
						if (member.getTeam().getProject().getSubject().getId() == subject.getId()) {
							member.getTeam().getMembers().remove(member);
							toRemove.add(member);
							teamMgr.save(member.getTeam());
						}
					}
					
					for (TeamStudent member : toRemove) {
						member.getStudent().getTeams().remove(member);
						personMgr.save(member.getStudent());
						teamStudentMgr.remove(member);
					}
				}
			}
			
			for (Subject subject : subjects.getTarget()) {
				if (account.isTeacher()) {
					((Teacher)account).getTeachedSubjects().add(subject);
					subject.getTeachers().add((Teacher)account);
				}
				else {
					((Student)account).getEnrolled().add(subject);
					subject.getStudents().add((Student)account);
				}
				
				subjectMgr.save(subject);
			}
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
	
	public boolean hasSubjects() {
		return account.isTeacher() || account.isStudent();
	}
}
