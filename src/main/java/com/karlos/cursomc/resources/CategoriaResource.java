package com.karlos.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.karlos.cursomc.domain.Categoria;
import com.karlos.cursomc.dto.CategoriaDTO;
import com.karlos.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> listar(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	// o VALID vai validar todos as anottations da respectiva classe DTO
	@RequestMapping(method = RequestMethod.POST) // É Recebido o JSON pela url
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){ // vai converter JSON pra Objeto
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		// FALTA EMBASAMENTO PRA ENTEDER ESTAS REQUISIÇÕES "REST"
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest() // pega a url e adiciona o id gerado
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); // porfim converte para URI
		return ResponseEntity.created(uri).build(); // retorna a resposta de sucesso
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id); // (DEPRECIADO) deve garantir que o ID seja o mesmo informado na URL
		obj = service.update(obj);
		return ResponseEntity.noContent().build(); //conteudo vazio (204)
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDto = list.stream().map(
				obj -> new CategoriaDTO(obj)).collect(Collectors.toList() // converte stream pra ArrayList
		);
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
		){
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		// nesse caso o page já converte os objetos da lista para Dto de forma automatica
		return ResponseEntity.ok().body(listDto);
	}
	
}
