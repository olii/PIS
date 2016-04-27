package pis.data;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import static javax.persistence.FetchType.EAGER;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Person {
	@ManyToMany(mappedBy = "students", fetch = EAGER)
	private List<Subject> enrolled;
	@OneToMany(mappedBy = "student", fetch = EAGER)
	private List<TeamStudent> teams;

	public List<Subject> getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(List<Subject> enrolled) {
		this.enrolled = enrolled;
	}
	
	public List<TeamStudent> getTeams() {
		return teams;
	}
	
	public void setTeams(List<TeamStudent> teams) {
		this.teams = teams;
	}
	
	@Override
	public boolean isStudent() {
		return true;
	}
}
