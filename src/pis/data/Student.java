package pis.data;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Person {
	@ManyToMany(mappedBy = "students")
	private List<Subject> enrolled;

	public List<Subject> getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(List<Subject> enrolled) {
		this.enrolled = enrolled;
	}
}
