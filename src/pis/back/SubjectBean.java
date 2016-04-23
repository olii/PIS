package pis.back;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Project;
import pis.data.Subject;
import pis.service.SubjectManager;

@ManagedBean
@ViewScoped
public class SubjectBean {
	@EJB
	private SubjectManager subjectMgr;
	private Subject subject;
	private List<Project> projects;
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	@PostConstruct
	public void init() {
		Map<String, String> getParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		int subjectId = Integer.parseInt(getParams.get("id"));
		Subject subject = subjectMgr.findById(subjectId);
		if (subject == null) {
			return;
		}

		this.subject = subject;
		this.projects = subject.getProjects();
	}
	
	public String getTitle() {
		return subject.getName();
	}
}
