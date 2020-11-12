package com.empresa.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Departamento {

	
	@Id
	@Column(name = "departamento_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "departamento_name", nullable = false, length = 50)
	private String name;
	
	@NotNull
	@OneToOne
	@JsonIgnoreProperties({"age","document","cargo","birthday","historicos"})
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario chefe;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"age","document","historicos"})
	@JoinTable(name = "funcionario_departamento", 
			joinColumns = @JoinColumn(name="departamento_id"),
			inverseJoinColumns = @JoinColumn(name="funcionario_id"))
	private List<Funcionario> funcionarios;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the chefe
	 */
	public Funcionario getChefe() {
		return chefe;
	}

	/**
	 * @param chefe the chefe to set
	 */
	public void setChefe(Funcionario chefe) {
		this.chefe = chefe;
	}

	/**
	 * @return the funcionarios
	 */
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	/**
	 * @param funcionarios the funcionarios to set
	 */
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Departamento)) {
			return false;
		}
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
