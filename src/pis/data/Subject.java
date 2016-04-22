package pis.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
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
	private List<Project> projects;
	@ManyToMany(fetch = EAGER, cascade = ALL)
	@JoinTable(name = "subject_teacher")
	private List<Teacher> teachers;
	@ManyToMany(fetch = EAGER, cascade = ALL)
	@JoinTable(name = "subject_student")
	private List<Student> students;

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
	
	public List<Project> getProjects() {
		return this.projects;
	}
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public List<Teacher> getTeachers() {
		return this.teachers;
	}
	
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public List<Student> getStudents() {
		return this.students;
	}
	
	public void setStudents(List<Student> students) {
		this.students = students;
	}
}
