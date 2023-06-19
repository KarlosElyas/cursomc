package com.karlos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karlos.cursomc.domain.Categoria;

// @Repository já é herdado de JpaRepository
public interface CategoriaRepository extends  JpaRepository<Categoria, Integer>{

}
