package pis.data;

import java.io.Serializable;

public class TeamStudentId implements Serializable {
	private static final long serialVersionUID = 1L;
	private int team;
	private int student;

	public int getTeam() {
		return team;
	}

	public void setTeamId(int team) {
		this.team = team;
	}

	public int getStudent() {
		return student;
	}

	public void setStudent(int student) {
		this.student = student;
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (!(rhs instanceof TeamStudentId)) {
			return false;
		}
		
		TeamStudentId trhs = (TeamStudentId)rhs;
		return (team == trhs.getTeam()) && (student == trhs.getStudent());
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
