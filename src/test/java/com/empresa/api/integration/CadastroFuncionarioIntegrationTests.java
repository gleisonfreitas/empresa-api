package com.empresa.api.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.empresa.domain.dto.FuncionarioDTO;
import com.empresa.domain.exception.EntidadeNaoEcontradaException;
import com.empresa.domain.model.Cargo;
import com.empresa.domain.model.Funcionario;
import com.empresa.domain.repository.FuncionarioRepository;
import com.empresa.domain.service.CadastroFuncionarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroFuncionarioIntegrationTests {

	@Autowired
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Test
	public void testarCadastroFuncionarioComSucesso() {
		
		//cenario
		Funcionario funcionario = criarFuncionario();
		
		//ação
		funcionario = cadastroFuncionarioService.salvar(funcionario);
		
		//validação
		assertThat(funcionario).isNotNull();
		assertThat(funcionario.getId()).isNotNull();
	}
	
	@Test
	public void testarlistarFuncionariosComSucesso() {
		
		//cenario
		
		//ação
		List<FuncionarioDTO> funcionarios = funcionarioRepository.listar();
		
		//validação
		assertThat(funcionarios).isNotEmpty();
	}

	@Test
	public void testarConsultarFuncionarioComSucesso() {
		
		//cenario
		
		
		//ação
		Funcionario funcionario = cadastroFuncionarioService.buscarPorId(1L);
		
		//validação
		assertThat(funcionario).isNotNull();
		assertThat(funcionario.getId()).isNotNull();
		assertThat(funcionario.getId()).isEqualTo(1L);
	}
	
	@Test
	public void testarAtualizarFuncionarioComSucesso() {
		
		//cenario
		Funcionario funcionario = cadastroFuncionarioService.buscarPorId(1L);
		
		//ação
		Cargo cargo = new Cargo();
		cargo.setId(3L);
		funcionario.setCargo(cargo);
		
		funcionario = cadastroFuncionarioService.atualizar(1L, funcionario);
		
		//validação
		assertThat(funcionario).isNotNull();
		assertThat(funcionario.getCargo()).isNotNull();
		assertThat(funcionario.getCargo().getId()).isEqualTo(3L);
	}
	
	@Test(expected = EntidadeNaoEcontradaException.class)
	public void testarExcluirFuncionarioComSucesso() {
		
		//cenario
		Funcionario funcionario = criarFuncionario();
		
		//ação
		funcionario = cadastroFuncionarioService.salvar(funcionario);
		
		Long id = funcionario.getId();
		
		cadastroFuncionarioService.excluir(id);
		cadastroFuncionarioService.buscarPorId(id);
		
		//validação
		
	}
	
	private Funcionario criarFuncionario() {
		Funcionario funcionario = new Funcionario();
		funcionario.setName("Guilherme");
		funcionario.setBirthday(LocalDate.now());
		funcionario.setAge(31);
		funcionario.setDocument("98764452");
		Cargo cargo = new Cargo();
		cargo.setId(2L);
		funcionario.setCargo(cargo);
		return funcionario;
	}

}
