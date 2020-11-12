package com.empresa.domain.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class HistoricoFuncionarioRepositoryImpl implements HistoricoFuncionarioRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void atualizarHistorico(List<Long> funcionarioIds, Long departamentoId) {
		
		StringBuilder jpql = new StringBuilder();
		
		jpql.append("Update HistoricoFuncionario h ")
			.append("set h.dataFim = :dataFim ")
			.append("Where h.funcionario.id in (:funcionarioIds) ")
			.append("And h.departamento.id = :departamentoId");
		
		this.manager.createQuery(jpql.toString())
			.setParameter("dataFim", LocalDate.now())
			.setParameter("funcionarioIds", funcionarioIds)
			.setParameter("departamentoId", departamentoId)
			.executeUpdate();
	}

}
