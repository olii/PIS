package pis.back;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.data.Student;
import pis.data.Subject;
import pis.data.Teacher;
import pis.data.Team;
import pis.service.PersonManager;
import pis.service.TeamManager;

@ManagedBean
@ViewScoped
public class TeamBean {
	@EJB
	private TeamManager teamMgr;
	@EJB
	private PersonManager personMgr;
	private Team team;
	private List<Student> members;
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Student> getMembers() {
		return members;
	}

	public void setMembers(List<Student> members) {
		this.members = members;
	}
	
	@PostConstruct
	public void init() {
		Map<String, String> getParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		int teamId = Integer.parseInt(getParams.get("id"));
		init(teamId);
	}
	
	public void init(int teamId) {
		Team team = teamMgr.findById(teamId);
		if (team == null) {
			return;
		}
		
		this.team = team;
		this.members = team.getMembers();
	}
	
	public String getTitle() {
		return team.getProject().getSubject().getName() + " - "
				+ team.getProject().getName() + " - " + team.getName();
	}
	
	public boolean canManage(Person account) {
		if (account.isAdmin()) {
			return true;
		}
		
		if (account.isTeacher()) {
			Teacher teacher = (Teacher)account;
			for (Subject subject : teacher.getTeachedSubjects()) {
				if (subject.getId() == team.getProject().getSubject().getId()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void removeMember(Student account) {
		team.getMembers().removeIf(s -> s.getId() == account.getId());
		account.getTeams().removeIf(t -> t.getId() == team.getId());
		
		teamMgr.save(team);
		personMgr.save(account);
		
		init(team.getId());
	}
}
