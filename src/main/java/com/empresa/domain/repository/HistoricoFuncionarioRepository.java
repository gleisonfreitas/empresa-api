package com.empresa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empresa.domain.model.HistoricoFuncionario;

@Repository
public interface HistoricoFuncionarioRepository extends JpaRepository<HistoricoFuncionario, Long>, HistoricoFuncionarioRepositoryQuery {
	
}
