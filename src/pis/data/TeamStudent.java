package pis.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static javax.persistence.FetchType.EAGER;

@Entity
@IdClass(TeamStudentId.class)
@Table(name = "team_student")
public class TeamStudent {
	@Id
	@ManyToOne(fetch = EAGER)
	private Team team;
	@Id
	@ManyToOne(fetch = EAGER)
	private Student student;
	private int points;
	
	public TeamStudent() {
		team = null;
		student = null;
		points = 0;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
