package pis.back;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pis.data.Person;
import pis.data.Student;
import pis.data.Subject;
import pis.data.Teacher;
import pis.service.SubjectManager;

@ManagedBean
@ViewScoped
public class SubjectListBean {	
	@EJB
	private SubjectManager subjectMgr;

	public List<Subject> getSubjects(Person person) {
		if (person instanceof Teacher) {
			return ((Teacher)person).getTeachedSubjects();
		}
		else if (person instanceof Student) {
			return ((Student)person).getEnrolled();
		}
		else {
			return subjectMgr.getAll();
		}
	}
}
