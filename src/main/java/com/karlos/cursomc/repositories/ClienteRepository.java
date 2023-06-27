package com.karlos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.karlos.cursomc.domain.Cliente;

public interface ClienteRepository extends  JpaRepository<Cliente, Integer>{

	//O Spring Data cria automaticamente o find by com o campo informado | AULA 46
	@Transactional(readOnly = true) // otimiza o método
	Cliente findByEmail(String email);
	
}
