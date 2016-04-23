package pis.back;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pis.data.Project;
import pis.service.ProjectManager;

@ManagedBean
@ViewScoped
public class ProjectBean {
	@EJB
	private ProjectManager projectMgr;
	
	public Project getProject(int id) {
		return projectMgr.findById(id);
	}
	
	public String getName(int id) {
		Project project = getProject(id);
		if (project == null) {
			return null;
		}
		
		return project.getSubject().getName() + " - " + project.getName(); 
	}
}