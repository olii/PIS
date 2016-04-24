package pis.data;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import static javax.persistence.FetchType.EAGER;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Person {
	@ManyToMany(mappedBy = "students", fetch = EAGER)
	private List<Subject> enrolled;
	@ManyToMany(mappedBy = "members", fetch = EAGER)
	private List<Team> teams;

	public List<Subject> getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(List<Subject> enrolled) {
		this.enrolled = enrolled;
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	@Override
	public boolean isStudent() {
		return true;
	}
}
