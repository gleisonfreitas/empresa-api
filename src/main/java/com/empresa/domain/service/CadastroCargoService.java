package com.empresa.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.domain.exception.CargoNaoEncontradoException;
import com.empresa.domain.model.Cargo;
import com.empresa.domain.repository.CargoRepository;

@Service
public class CadastroCargoService {
	
	@Autowired
	private CargoRepository cargoRepository;

	
	public Cargo buscarPorId(Long id) {
		
		Optional<Cargo> optional = cargoRepository.findById(id);
		
		return optional.orElseThrow(() -> new CargoNaoEncontradoException(id));
	}
}
