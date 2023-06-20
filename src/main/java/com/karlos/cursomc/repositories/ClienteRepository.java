package com.karlos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karlos.cursomc.domain.Cliente;

public interface ClienteRepository extends  JpaRepository<Cliente, Integer>{

}
