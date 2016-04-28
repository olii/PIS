package pis.data;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import static javax.persistence.FetchType.EAGER;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends Person {
	@ManyToMany(mappedBy = "teachers", fetch = EAGER)
	private List<Subject> teachedSubjects;

	public List<Subject> getTeachedSubjects() {
		return this.teachedSubjects;
	}

	public void setTeachedSubjects(List<Subject> teachedSubjects) {
		this.teachedSubjects = teachedSubjects;
	}
	
	@Override
	public boolean isTeacher() {
		return true;
	}
}
