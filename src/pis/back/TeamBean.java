package pis.back;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pis.data.Project;
import pis.data.Subject;
import pis.data.Team;
import pis.service.TeamManager;

@ManagedBean
@ViewScoped
public class TeamBean {
	@EJB
	private TeamManager teamMgr;
	
	public String getName(int id) {
		Team team = teamMgr.findById(id);
		Project project = team.getProject();
		Subject subject = project.getSubject();
		return subject.getName() + " - " + project.getName() + " - " + team.getName();
	}
	
	public Team getTeam(int id) {
		return teamMgr.findById(id);
	}
}
