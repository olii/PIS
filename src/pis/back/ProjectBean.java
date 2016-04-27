package pis.back;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.data.Project;
import pis.data.Student;
import pis.data.Subject;
import pis.data.Teacher;
import pis.data.Team;
import pis.data.TeamStudent;
import pis.service.PersonManager;
import pis.service.ProjectManager;
import pis.service.TeamManager;
import pis.service.TeamStudentManager;

@ManagedBean
@ViewScoped
public class ProjectBean {
	@EJB
	private ProjectManager projectMgr;
	@EJB
	private TeamManager teamMgr;
	@EJB
	private PersonManager personMgr;
	@EJB
	private TeamStudentManager teamStudentMgr;
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
		System.out.println(this.teams.size());
	}
	
	public String getTitle() {
		return project.getSubject().getName() + " - " + project.getName(); 
	}
	
	public boolean teamContainsMember(Team team, Person account) {
		for (TeamStudent teamMember : team.getMembers()) {
			if (teamMember.getStudent().getId() == account.getId())
				return true;
		}
		return false;
	}
	
	public boolean teamDoesNotContainMember(Team team, Person account) {
		return !teamContainsMember(team, account);
	}
	
	public void leaveTeam(int teamId, Student account) {
		TeamStudent teamMember = teamStudentMgr.findByIds(teamId, account.getId());
		Team team = teamMgr.findById(teamId);

		team.getMembers().removeIf(member -> member.getStudent().getId() == account.getId());
		account.getTeams().removeIf(t -> t.getTeam().getId() == team.getId());

		teamStudentMgr.remove(teamMember);
		teamMgr.save(team);
		personMgr.save(account);
		
		init(project.getId());
	}
	
	public void joinTeam(int teamId, Student account) {
		if (alreadyHasTeam(account)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "You already have a team in this project", ""));
			return;
		}
		
		Team team = teamMgr.findById(teamId);
		
		TeamStudent teamMember = new TeamStudent();
		teamMember.setTeam(team);
		teamMember.setStudent(account);
		
		team.getMembers().add(teamMember);
		account.getTeams().add(teamMember);

		teamMgr.save(team);
		personMgr.save(account);

		init(project.getId());
	}
	
	public String newTeam(Person account) {
		if (alreadyHasTeam(account)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "You already have a team in this project", ""));
			return "error";
		}

		return "/student/newteam.xhtml?project=" + project.getId() + "&faces-redirect=true";
	}
	
	public boolean alreadyHasTeam(Person account) {
		if (!(account instanceof Student))
			return false;
		
		for (TeamStudent teamMember : ((Student)account).getTeams()) {
			if (teamMember.getTeam().getProject().getId() == project.getId())
				return true;
		}
		
		return false;
	}
	
	public boolean canManage(Person account) {
		if (account.isAdmin()) {
			return true;
		}
		
		if (account.isTeacher()) {
			Teacher teacher = (Teacher)account;
			for (Subject subject : teacher.getTeachedSubjects()) {
				if (subject.getId() == project.getSubject().getId()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void removeTeam(int teamId) {
		Team team = teamMgr.findById(teamId);
		List<Person> accounts = personMgr.findAll();
		
		for (Person account : accounts) {
			if (!account.isStudent())
				continue;
			
			Student student = (Student)account;
			student.getTeams().removeIf(t -> t.getTeam().getId() == team.getId());
			personMgr.save(student);
		}
		
		project.getTeams().removeIf(t -> t.getId() == team.getId());
		projectMgr.save(project);
		teamMgr.remove(team);
		
		init(project.getId());
	}
}
