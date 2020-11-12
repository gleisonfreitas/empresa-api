package com.empresa.domain.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.empresa.api.validation.Grupos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Funcionario {

	@Id
	@Column(name = "funcionario_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "funcionario_name", nullable = false, length = 50)
	private String name;
	
	@Max(99)
	@Min(18)
	@NotNull
	@Column(name = "funcionario_age", nullable = false, length = 2)
	private Integer age;
	
	@NotNull
	@Column(name = "funcionario_birthday", nullable = false)
	private LocalDate birthday;
	
	@NotNull
	@Column(name = "funcionario_document", nullable = false, length = 50)
	private String document;
	
	@Valid
	@NotNull
	@OneToOne
	@JoinColumn(name = "cargo_id")
	@ConvertGroup(from = Default.class, to = Grupos.CargoId.class)
	private Cargo cargo;
	
	@JsonIgnoreProperties({"id"})
	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
	private List<HistoricoFuncionario> historicos;
	
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
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the birthday
	 */
	public LocalDate getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}

	/**
	 * @return the cargo
	 */
	public Cargo getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	/**
	 * @return the historicos
	 */
	public List<HistoricoFuncionario> getHistoricos() {
		return historicos;
	}

	/**
	 * @param historicos the historicos to set
	 */
	public void setHistoricos(List<HistoricoFuncionario> historicos) {
		this.historicos = historicos;
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
		if (!(obj instanceof Funcionario)) {
			return false;
		}
		Funcionario other = (Funcionario) obj;
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
