package com.empresa.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.empresa.domain.dto.DepartamentoDTO;
import com.empresa.domain.model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

	@Query("Select new com.empresa.domain.dto.DepartamentoDTO(d.id, d.name, d.chefe.name) from Departamento d")
	List<DepartamentoDTO> listar();
	
}
