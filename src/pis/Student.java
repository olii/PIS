package pis;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.CascadeType.ALL;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Person {
	@ManyToMany(fetch = EAGER, cascade = ALL)
	@JoinTable(name = "student_subject")
	private ArrayList<Subject> enrolled;

	public ArrayList<Subject> getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(ArrayList<Subject> enrolled) {
		this.enrolled = enrolled;
	}
}
