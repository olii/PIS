package pis.back;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pis.data.Subject;
import pis.service.SubjectManager;

@ManagedBean
@ViewScoped
public class SubjectBean {
	@EJB
	private SubjectManager subjectMgr;
	
	public Subject getSubject(int id) {
		return subjectMgr.findById(id);
	}
}
