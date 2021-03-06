package com.empresa.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.domain.dto.DepartamentoDTO;
import com.empresa.domain.model.Departamento;
import com.empresa.domain.model.Funcionario;
import com.empresa.domain.repository.DepartamentoRepository;
import com.empresa.domain.service.CadastroDepartamentoService;
import com.empresa.domain.service.CadastroFuncionarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Departamentos")
@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	@Autowired
	private CadastroDepartamentoService cadastroDepartamentoService; 
	
	@Autowired
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@GetMapping
	@ApiOperation("Lista os departamentos resumidos")
	public List<DepartamentoDTO> listar() {
		return departamentoRepository.listar();
	}
	
	@GetMapping("/{departamentoId}")
	@ApiOperation("Consulta um departamento completo por id")
	public Departamento consultarPorId(@PathVariable Long departamentoId){
		return cadastroDepartamentoService.consultarPorId(departamentoId);
	}
	
	@GetMapping("/{departamentoId}/funcionarios")
	@ApiOperation("Consulta um departamento resumido com a lista de funcionários")
	public DepartamentoDTO consultarPorIdFuncionarios(@PathVariable Long departamentoId){
		return cadastroDepartamentoService.consultarResumido(departamentoId);
	}
	
	@PutMapping("/{departamentoId}")
	@ApiOperation("Atualiza um departamento completo")
	public Departamento atualizar(@PathVariable Long departamentoId, @RequestBody @Valid Departamento departamento) {
		return cadastroDepartamentoService.atualizar(departamentoId, departamento);
	}
	
	@PutMapping("/{departamentoId}/atualiza-chefe")
	@ApiOperation("Atualiza o chefe de um departamento")
	public Departamento definirChefe(@PathVariable Long departamentoId, 
			@Param("chefeId") Long chefeId) {
		
		Departamento departamento = cadastroDepartamentoService.consultarPorId(departamentoId);
		Funcionario chefe = cadastroFuncionarioService.buscarPorId(chefeId);
		departamento.setChefe(chefe);
		
		return atualizar(departamentoId, departamento);
	}
}
