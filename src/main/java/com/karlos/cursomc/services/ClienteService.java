package com.karlos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karlos.cursomc.domain.Cliente;
import com.karlos.cursomc.repositories.ClienteRepository;
import com.karlos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo; // OBJETO de acesso a dados
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
}
