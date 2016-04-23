package pis.back;

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
import pis.data.Team;
import pis.service.PersonManager;
import pis.service.ProjectManager;
import pis.service.TeamManager;

@ManagedBean
@ViewScoped
public class ProjectBean {
	@EJB
	private ProjectManager projectMgr;
	@EJB
	private TeamManager teamMgr;
	@EJB
	private PersonManager personMgr;
	private Project project;
	private List<Team> teams;
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	@PostConstruct
	public void init() {
		Map<String, String> getParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		int projectId = Integer.parseInt(getParams.get("id"));
		init(projectId);
	}
	
	public void init(int projectId) {
		Project project = projectMgr.findById(projectId);
		if (project == null) {
			return;
		}

		this.project = project;
		this.teams = project.getTeams();
	}
	
	public String getTitle() {
		return project.getSubject().getName() + " - " + project.getName(); 
	}
	
	public boolean teamContainsMember(Team team, Person account) {
		for (Person member : team.getMembers()) {
			if (member.getId() == account.getId())
				return true;
		}
		return false;
	}
	
	public boolean teamDoesNotContainMember(Team team, Person account) {
		return !teamContainsMember(team, account);
	}
	
	public void leaveTeam(int teamId, Student account) {
		Team team = teamMgr.findById(teamId);
		team.getMembers().removeIf(member -> member.getId() == account.getId());
		account.getTeams().removeIf(t -> t.getId() == team.getId());

		teamMgr.save(team);
		personMgr.save(account);
		
		init(project.getId());
	}
	
	public void joinTeam(int teamId, Student account) {
		Team team = teamMgr.findById(teamId);
		team.getMembers().add(account);
		account.getTeams().add(team);

		teamMgr.save(team);
		personMgr.save(account);

		init(project.getId());
	}
}
