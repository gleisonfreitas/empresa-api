package com.empresa.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.domain.exception.EntidadeEmUsoException;
import com.empresa.domain.exception.FuncionarioNaoEncontradoException;
import com.empresa.domain.model.Cargo;
import com.empresa.domain.model.Funcionario;
import com.empresa.domain.repository.FuncionarioRepository;

@Service
public class CadastroFuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private CadastroCargoService cadastroCargoService;
	
	public Funcionario buscarPorId(Long id) {
		Optional<Funcionario> optional = funcionarioRepository.findById(id);
		
		return optional.orElseThrow(() -> new FuncionarioNaoEncontradoException(id));
	}

	@Transactional
	public Funcionario salvar(Funcionario funcionario) {
		
		Cargo cargo = cadastroCargoService.buscarPorId(funcionario.getCargo().getId());
		funcionario.setCargo(cargo);
		
		return funcionarioRepository.save(funcionario);
			
	}

	public void excluir(Long id) {
		
		try {
			Funcionario funcionario = buscarPorId(id);
			
			funcionarioRepository.delete(funcionario);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Funcionario de código %d não pode ser removido, pois está em uso.", id));
		}
	}

	public Funcionario atualizar(Long funcionarioId, Funcionario funcionario) {
		
		Funcionario funcionarioSalvo = buscarPorId(funcionarioId);
		Cargo cargo = cadastroCargoService.buscarPorId(funcionario.getCargo().getId());
		
		BeanUtils.copyProperties(funcionario, funcionarioSalvo, "id", "historicos");
		
		funcionarioSalvo.setCargo(cargo);
		
		return funcionarioRepository.save(funcionarioSalvo);
	}
}

