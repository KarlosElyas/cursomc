package com.karlos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.karlos.cursomc.domain.Categoria;
import com.karlos.cursomc.domain.Produto;
import com.karlos.cursomc.repositories.CategoriaRepository;
import com.karlos.cursomc.repositories.ProdutoRepository;
import com.karlos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo; // OBJETO de acesso a dados
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	// Mantida esta Operação por ser muiito básica e de uso comum
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest= PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids); // categorias referente aos ids
		return repo.search(nome, categorias, pageRequest);
		
	}
	
}
