package com.empresa.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.domain.dto.DepartamentoDTO;
import com.empresa.domain.dto.FuncionarioDTO;
import com.empresa.domain.exception.DepartamentoNaoEncontradoException;
import com.empresa.domain.model.Departamento;
import com.empresa.domain.model.Funcionario;
import com.empresa.domain.model.HistoricoFuncionario;
import com.empresa.domain.repository.DepartamentoRepository;
import com.empresa.domain.repository.HistoricoFuncionarioRepository;

@Service
public class CadastroDepartamentoService {

	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	@Autowired
	private HistoricoFuncionarioRepository historicoFuncionarioRepository;
	
	@Autowired
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	public Departamento consultarPorId(Long departamentoId) {
		
		Optional<Departamento> optional = departamentoRepository.findById(departamentoId);
		
		return optional.orElseThrow(() -> new DepartamentoNaoEncontradoException(departamentoId));
		
	}
	
	public DepartamentoDTO consultarResumido(Long departamentoId) {
		Departamento departamento = consultarPorId(departamentoId);
		
		DepartamentoDTO dto =  new DepartamentoDTO(
				departamento.getId(), departamento.getName(), departamento.getChefe().getName());
		
		List<FuncionarioDTO> funcionariosDTO = departamento.getFuncionarios().stream()
													.map(f -> new FuncionarioDTO(f.getId(), f.getName(), f.getCargo().getName()))
													.collect(Collectors.toList());
		
		dto.setFuncionarios(funcionariosDTO);
		
		return dto;
	}
	
	@Transactional
	public Departamento atualizar(Long departamentoId, Departamento departamento) {
		
		Departamento departamentoSalvo = consultarPorId(departamentoId);
		
		BeanUtils.copyProperties(departamento, departamentoSalvo, "id", "chefe", "funcionarios");
		
		List<Long> funcionariosRetirados = departamentoSalvo.getFuncionarios().stream()
				.filter(f -> !departamento.getFuncionarios().contains(f))
				.map(Funcionario::getId)
				.collect(Collectors.toList());
		
		historicoFuncionarioRepository.atualizarHistorico(funcionariosRetirados, departamentoId);
		
		List<Long> funcionariosNovos = departamento.getFuncionarios().stream()
				.filter(f -> !departamentoSalvo.getFuncionarios().contains(f))
				.map(Funcionario::getId)
				.collect(Collectors.toList());
		
		criarHistorico(funcionariosNovos, departamentoSalvo);
		
		departamentoSalvo.setFuncionarios(departamento.getFuncionarios());
		
		Funcionario chefe = cadastroFuncionarioService.buscarPorId(departamento.getChefe().getId());		
		
		departamentoSalvo.setChefe(chefe);
		
		return departamentoRepository.save(departamentoSalvo);
		
		
	}

	private void criarHistorico(List<Long> funcionariosNovos, Departamento departamentoSalvo) {
		
		for (Long funcionarioId : funcionariosNovos) {
			HistoricoFuncionario historico = new HistoricoFuncionario();
			historico.setFuncionario(cadastroFuncionarioService.buscarPorId(funcionarioId));
			historico.setDepartamento(departamentoSalvo);
			
			historicoFuncionarioRepository.save(historico);
		}
	}
	
}
