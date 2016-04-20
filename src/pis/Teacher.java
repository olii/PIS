package pis;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends Person {
	@ManyToMany(fetch = EAGER, cascade = ALL)
	@JoinTable(name = "teacher_subject")
	private ArrayList<Subject> teachedSubjects;

	public ArrayList<Subject> getTeachedSubjects() {
		return this.teachedSubjects;
	}

	public void setEnrolled(ArrayList<Subject> teachedSubjects) {
		this.teachedSubjects = teachedSubjects;
	}
}
