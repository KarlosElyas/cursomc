package com.karlos.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.karlos.cursomc.domain.Categoria;
import com.karlos.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
	public ResponseEntity<Categoria> listar(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST) // É Recebido o JSON pela url
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){ // vai converter JSON pra Objeto
		obj = service.insert(obj);
		// FALTA EMBASAMENTO PRA ENTEDER ESTAS REQUISIÇÕES "REST"
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest() // pega a url e adiciona o id gerado
				.path("/{id}").buildAndExpand(obj.getId()).toUri(); // porfim converte para URI
		return ResponseEntity.created(uri).build(); // retorna a resposta de sucesso
	}

	@RequestMapping(value = "/{id}" ,method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build(); //conteudo vazio (204)
	}
	
}
