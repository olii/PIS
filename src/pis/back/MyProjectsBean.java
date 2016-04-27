package pis.back;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.data.Project;
import pis.data.Student;
import pis.data.Subject;
import pis.data.Team;
import pis.data.TeamStudent;

@ManagedBean
@ViewScoped
public class MyProjectsBean {
	private List<Project> projects;

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	@PostConstruct
	public void init() {
		AuthBean authBean = (AuthBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authBean");
		
		projects = new ArrayList<Project>();
		Person account = authBean.getAccount();
		if (!account.isStudent()) {
			return;
		}

		Student student = (Student)account;
		for (Subject subject : student.getEnrolled()) {
			projects.addAll(subject.getProjects());
		}
	}
	
	public boolean hasTeam(Project project, Student account) {
		for (Team team : project.getTeams()) {
			for (TeamStudent teamMember : team.getMembers()) {
				if (teamMember.getStudent().getId() == account.getId())
					return true;
			}
		}
		
		return false;
	}
	
	public String getTeamName(Project project, Student account) {
		for (Team team : project.getTeams()) {
			for (TeamStudent teamMember : team.getMembers()) {
				if (teamMember.getStudent().getId() == account.getId())
					return team.getName();
			}
		}
		
		return "Not Registered";
	}
	
	public int getTeamId(Project project, Student account) {
		for (Team team : project.getTeams()) {
			for (TeamStudent teamMember : team.getMembers()) {
				if (teamMember.getStudent().getId() == account.getId())
					return team.getId();
			}
		}
		
		return -1;
	}
}
