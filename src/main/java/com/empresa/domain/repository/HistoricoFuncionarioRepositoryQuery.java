package com.empresa.domain.repository;

import java.util.List;

public interface HistoricoFuncionarioRepositoryQuery {

	void atualizarHistorico(List<Long> funcionarioIds, Long departamentoId);

}
