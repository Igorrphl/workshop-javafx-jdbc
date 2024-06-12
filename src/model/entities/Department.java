package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String description;
	private String responsibilities;
	private Integer quantEmployees;
	
	public Department() {
	}

	public Department(Integer id, String name, String description, String responsibilities, Integer quantEmployees) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.responsibilities = responsibilities;
		this.quantEmployees = quantEmployees;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public Integer getQuantEmployees() {
		return quantEmployees;
	}

	public void setQuantEmployees(Integer quantEmployees) {
		this.quantEmployees = quantEmployees;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", description=" + description + ", responsibilities="
				+ responsibilities + ", quantEmployees=" + quantEmployees + "]";
	}

	
}