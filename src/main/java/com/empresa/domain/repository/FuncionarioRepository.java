package com.empresa.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.empresa.domain.dto.FuncionarioDTO;
import com.empresa.domain.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query("select new com.empresa.domain.dto.FuncionarioDTO(f.id, f.name, f.cargo.name) from Funcionario f ")
	List<FuncionarioDTO> listar();
}
