package pis.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.EAGER;

import java.util.List;
import java.util.Date;

@Entity
@Table(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String name;
	private int capacity;
	@Temporal(TemporalType.DATE)
	private Date regBegin;
	@Temporal(TemporalType.DATE)
	private Date regEnd;
	@Temporal(TemporalType.DATE)
	private Date deadline;
	@OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "project", orphanRemoval = true)
	private List<Team> teams;
	@ManyToOne(fetch = EAGER)
	private Subject subject;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegistrationBegin() {
		return regBegin;
	}

	public void setRegistrationBegin(Date regBegin) {
		this.regBegin = regBegin;
	}

	public Date getRegistrationEnd() {
		return regEnd;
	}

	public void setRegistrationEnd(Date regEnd) {
		this.regEnd = regEnd;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
