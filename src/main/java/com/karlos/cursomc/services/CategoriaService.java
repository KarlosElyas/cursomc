package com.karlos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.karlos.cursomc.domain.Categoria;
import com.karlos.cursomc.repositories.CategoriaRepository;
import com.karlos.cursomc.services.exceptions.DataIntegrityException;
import com.karlos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo; // OBJETO de acesso a dados
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}

	public Categoria insert(Categoria obj) {
		obj.setId(null); // IMPORTANTE pois é impossivel definir um id especifico
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId()); // exceção lançada caso nao exista no banco
		return repo.save(obj); 
		// se o id recebido no getId() não for NULL vai dar UPDATE do contrario INSERT
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma Categoria que contém produtos.");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest= PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest); // sobrecarga
	}
	
}
