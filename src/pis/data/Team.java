package pis.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.EAGER;

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
	@OneToMany(mappedBy = "team", fetch = EAGER)
	private List<TeamStudent> members;
	
	public Team() {
		members = new ArrayList<TeamStudent>();
	}

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

	public List<TeamStudent> getMembers() {
		return members;
	}
	
	public void setMembers(List<TeamStudent> members) {
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
