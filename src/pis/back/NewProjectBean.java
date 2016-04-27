package pis.back;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.data.Project;
import pis.data.Subject;
import pis.service.ProjectManager;
import pis.service.SubjectManager;

@ManagedBean
@ViewScoped
public class NewProjectBean {
	@EJB
	private ProjectManager projectMng;
	@EJB
	private SubjectManager subjectMng;
	
	private String name;
	private int capacity;
	private int teamSize;
	private Date deadline;	
	
	public String getTitle() {
		return "New Project";
	}
		
	public String create(Person account, Subject subject) {	
		System.out.println(account);
		System.out.println(subject);
		
		if (name.equals("")){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter a Project name", ""));
			return "errror";
		}
		if (capacity == 0){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Capacity is 0", ""));
			return "errror";
		}
		if (capacity > 5000){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Capacity is greater  than 5000", ""));
			return "errror";
		}
		if (teamSize == 0){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Team size cannot be 0", ""));
			return "errror";
		}
		if (deadline == null){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Set the Deadline to be correct", ""));
			return "errror";
		}
		Date d = new Date();
		if (deadline.before(d)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Set the Deadline to be correct", ""));
			return "errror";
		}
		
		// test if there is a project with a same name in this subject
		List<Project> projects = subject.getProjects();
		for (Project p: projects){
			if (p.getName().equals(name)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Project with given name aleary exists in this subject", ""));
				return "errror";
			}
		}
		
		Project p = new Project();
		p.setCapacity(capacity);
		p.setDeadline(deadline);
		p.setName(name);
		p.setSubject(subject);
		
		p = projectMng.save(p);
		projects.add(p);
		subject.setProjects(projects);
		subjectMng.save(subject);
		
		return "subject.xhtml?id=" + subject.getId() + "&faces-redirect=true";
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}
}
