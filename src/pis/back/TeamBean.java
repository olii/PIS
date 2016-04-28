package pis.back;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pis.data.Person;
import pis.data.Subject;
import pis.data.Teacher;
import pis.data.Team;
import pis.data.TeamStudent;
import pis.service.PersonManager;
import pis.service.TeamManager;
import pis.service.TeamStudentManager;

@ManagedBean
@ViewScoped
public class TeamBean {
	@EJB
	private TeamManager teamMgr;
	@EJB
	private PersonManager personMgr;
	@EJB
	private TeamStudentManager teamStudentMgr;
	private Team team;
	private List<TeamStudent> members;
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<TeamStudent> getMembers() {
		return members;
	}

	public void setMembers(List<TeamStudent> members) {
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
	
	public void removeMember(TeamStudent teamMember) {
		teamMember.getTeam().getMembers().removeIf(s -> s.getStudent().getId() == teamMember.getStudent().getId());
		teamMember.getStudent().getTeams().removeIf(t -> t.getTeam().getId() == team.getId());

		teamMgr.save(team);
		personMgr.save(teamMember.getStudent());
		teamStudentMgr.remove(teamMember);
		
		init(team.getId());
	}
}
