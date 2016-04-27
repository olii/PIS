package pis.back;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Project;
import pis.data.Student;
import pis.data.Team;
import pis.data.TeamStudent;
import pis.service.PersonManager;
import pis.service.ProjectManager;
import pis.service.TeamManager;
import pis.service.TeamStudentManager;

@ManagedBean
@ViewScoped
public class NewTeamBean {
	@EJB
	private TeamManager teamMgr;
	@EJB
	private ProjectManager projectMgr;
	@EJB
	private PersonManager personMgr;
	@EJB
	private TeamStudentManager teamStudentMgr;
	private Project project;
	private String teamName;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@PostConstruct
	public void init() {
		Map<String, String> getParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		int projectId = Integer.parseInt(getParams.get("project"));
		Project project = projectMgr.findById(projectId);
		if (project == null) {
			return;
		}
		
		this.project = project;
	}
	
	public String getTitle() {
		return project.getSubject().getName() + " - " + project.getName() + " - New Team";
	}
	
	public String createTeam(Student account) {
		Team existingTeam = teamMgr.findByNameAndProject(teamName, project.getId());
		if (existingTeam != null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Team with the given name already exists", ""));
			return "errror";
		}
		
		Team team = new Team();
		team.setName(teamName);
		team.setProject(project);
		team.setCapacity(project.getTeamSize());
		team = teamMgr.save(team);
		
		TeamStudent teamMember = new TeamStudent();
		teamMember.setTeam(team);
		teamMember.setStudent(account);
		teamMember = teamStudentMgr.save(teamMember);

		team.getMembers().add(teamMember);
		team = teamMgr.save(team);
		
		project.getTeams().add(team);
		account.getTeams().add(teamMember);
		projectMgr.save(project);
		personMgr.save(account);
		return "/common/project.xhtml?id=" + project.getId() + "&faces-redirect=true";
	}
}
