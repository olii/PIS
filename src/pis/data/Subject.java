package pis.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "subject")
public class Subject {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String name;	
	@OneToMany(fetch = EAGER, cascade = ALL, mappedBy = "subject", orphanRemoval = true)
	private ArrayList<Project> projects;

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
	
	public ArrayList<Project> getProjects() {
		return this.projects;
	}
	
	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
}
