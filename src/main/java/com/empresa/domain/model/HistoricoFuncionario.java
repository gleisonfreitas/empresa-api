package com.empresa.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(Include.NON_NULL)
public class HistoricoFuncionario {

	
	@Id
	@Column(name = "historico_funcionario_id")
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn(name = "departamento_id", nullable = false)
	@JsonIgnoreProperties({"id","chefe","funcionarios"})
	private Departamento departamento;
	
	@Column(name = "data_inicio", nullable = false)
	private LocalDate dataInicio;
	
	@Column(name = "data_fim")
	private LocalDate dataFim;
	
	@PrePersist
	public void prePersite() {
		this.dataInicio = LocalDate.now();
	}

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
	 * @return the funcionario
	 */
	@JsonIgnore
	public Funcionario getFuncionario() {
		return funcionario;
	}

	/**
	 * @param funcionario the funcionario to set
	 */
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	/**
	 * @return the departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the dataInicio
	 */
	public LocalDate getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public LocalDate getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
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
		if (!(obj instanceof HistoricoFuncionario)) {
			return false;
		}
		HistoricoFuncionario other = (HistoricoFuncionario) obj;
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
