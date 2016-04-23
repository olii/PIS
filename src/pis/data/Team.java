package pis.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.JoinTable;

@Entity
@Table(name = "team")
public class Team {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String name;
	private int capacity;
	@ManyToOne(fetch = EAGER)
	private Project project;
	@ManyToMany(fetch = EAGER, cascade = ALL)
	@JoinTable(name = "team_student")
	private List<Student> members;

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

	public List<Student> getMembers() {
		return members;
	}
	
	public void setMembers(List<Student> members) {
		this.members = members;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
