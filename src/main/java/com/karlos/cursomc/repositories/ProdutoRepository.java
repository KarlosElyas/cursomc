package com.karlos.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.karlos.cursomc.domain.Categoria;
import com.karlos.cursomc.domain.Produto;

public interface ProdutoRepository extends  JpaRepository<Produto, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj "
			+ "FROM Produto obj INNER JOIN obj.categorias cats "
			+ "WHERE obj.nome LIKE %:nome% AND cats IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

}
