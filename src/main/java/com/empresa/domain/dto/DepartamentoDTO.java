package com.empresa.domain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DepartamentoDTO {
	
	private Long id;
	
	private String nome;
	
	private String chefe;
	
	private List<FuncionarioDTO> funcionarios;
	
	public DepartamentoDTO(Long id, String nome, String chefe) {
		this.id = id;
		this.nome = nome;
		this.chefe = chefe;
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
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the chefe
	 */
	public String getChefe() {
		return chefe;
	}

	/**
	 * @param chefe the chefe to set
	 */
	public void setChefe(String chefe) {
		this.chefe = chefe;
	}

	/**
	 * @return the funcionarios
	 */
	public List<FuncionarioDTO> getFuncionarios() {
		return funcionarios;
	}

	/**
	 * @param funcionarios the funcionarios to set
	 */
	public void setFuncionarios(List<FuncionarioDTO> funcionarios) {
		this.funcionarios = funcionarios;
	}
}
