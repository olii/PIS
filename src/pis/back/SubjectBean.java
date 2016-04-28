package pis.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.data.Project;
import pis.data.Student;
import pis.data.Subject;
import pis.data.Team;
import pis.data.TeamStudent;
import pis.service.PersonManager;
import pis.service.ProjectManager;
import pis.service.SubjectManager;
import pis.service.TeamManager;
import pis.service.TeamStudentManager;

@ManagedBean
@ViewScoped
public class SubjectBean {
	@EJB
	private SubjectManager subjectMgr;
	@EJB
	private ProjectManager projectMgr;
	@EJB
	private PersonManager personMgr;
	@EJB
	private TeamManager teamMgr;
	@EJB
	private TeamStudentManager teamStudentMgr;
	
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
	
	public void deleteProject(Project p){
		System.out.println("Delete project id = " + p.getId());
		for (Team team: p.getTeams()){
			List<TeamStudent> members = team.getMembers();
			
			for (TeamStudent teamstudent : members) {
				Student student = (Student)teamstudent.getStudent();
				student.getTeams().remove(teamstudent);
				teamStudentMgr.remove(teamstudent);
				personMgr.save(student);
			}
			List<TeamStudent> empty = new ArrayList<TeamStudent>();
			team.setMembers(empty);
			teamMgr.remove(team);
		}
		projects.remove(p);
		subject.getProjects().remove(p);
		projectMgr.remove(p);
		subjectMgr.save(subject);
	}
}
