package com.empresa.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.api.validation.ValidacaoException;
import com.empresa.domain.dto.FuncionarioDTO;
import com.empresa.domain.exception.EntidadeNaoEcontradaException;
import com.empresa.domain.exception.NegocioException;
import com.empresa.domain.model.Funcionario;
import com.empresa.domain.repository.FuncionarioRepository;
import com.empresa.domain.service.CadastroFuncionarioService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Funcionarios")
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@Autowired
	private SmartValidator validator;
	
	@GetMapping
	@ApiOperation("Lista todos os funcionarios resumidos")
	public List<FuncionarioDTO> listar(){
		return funcionarioRepository.listar();
	}
	
	@GetMapping("/{funcionarioId}")
	@ApiOperation("Consulta um funcionario completo")
	public Funcionario buscarPorId(@PathVariable Long funcionarioId) {
		
		return cadastroFuncionarioService.buscarPorId(funcionarioId);
	}
	
	@PostMapping
	@ApiOperation("Salva um funcionario")
	public Funcionario salvar(@RequestBody @Valid Funcionario funcionario){
		try {
			return cadastroFuncionarioService.salvar(funcionario);
		} catch (EntidadeNaoEcontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{funcionarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("Exclui um funcionario")
	public void remover(@PathVariable Long funcionarioId) {
		cadastroFuncionarioService.excluir(funcionarioId);
	}
	
	@PutMapping("/{funcionarioId}")
	@ApiOperation("Atualia um funcionario completo")
	public Funcionario atualizar(@PathVariable Long funcionarioId, @RequestBody @Valid Funcionario funcionario) {
		
		return cadastroFuncionarioService.atualizar(funcionarioId, funcionario);
	}
	
	@PatchMapping("/{funcionarioId}")
	@ApiOperation("Atualia um funcionario parcial")
	public Funcionario atualizarParcial(@PathVariable Long funcionarioId,
			@RequestBody Map<String, Object> dados, HttpServletRequest request){
		
		Funcionario funcionario = cadastroFuncionarioService.buscarPorId(funcionarioId);
		
		merge(dados, funcionario, request);
		validate(funcionario, "funcionario");
		
		return atualizar(funcionarioId, funcionario);
		
	}

	private void validate(Funcionario funcionario, String objectName) {
		
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(funcionario, objectName);
		
		validator.validate(funcionario, bindingResult);
		
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
		
	}

	private void merge(Map<String, Object> dados, Funcionario funcionario, HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Funcionario funcionarioDados = objectMapper.convertValue(dados, Funcionario.class);
			
			dados.forEach((nome, valor) -> {
				Field field = ReflectionUtils.findField(Funcionario.class, nome);
				field.setAccessible(true);
				
				Object dado = ReflectionUtils.getField(field, funcionarioDados);
				
				ReflectionUtils.setField(field, funcionario, dado);
			});
			
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}

}
