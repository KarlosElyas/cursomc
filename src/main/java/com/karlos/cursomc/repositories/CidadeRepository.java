package com.karlos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karlos.cursomc.domain.Cidade;

public interface CidadeRepository extends  JpaRepository<Cidade, Integer>{

}
