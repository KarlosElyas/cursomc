package com.karlos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.karlos.cursomc.domain.Cidade;
import com.karlos.cursomc.domain.Cliente;
import com.karlos.cursomc.domain.Endereco;
import com.karlos.cursomc.domain.enums.TipoCliente;
import com.karlos.cursomc.dto.ClienteDTO;
import com.karlos.cursomc.dto.ClienteNewDTO;
import com.karlos.cursomc.repositories.ClienteRepository;
import com.karlos.cursomc.repositories.EnderecoRepository;
import com.karlos.cursomc.services.exceptions.DataIntegrityException;
import com.karlos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo; // OBJETO de acesso a dados
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id); 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
	
	// Transactional garante que salve cliente e endereços juntos numa mesma TRANSAÇÃO
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		//a tabela telefone é criada em cliente e ja salva junto com ele
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos()); // salva os endereços na mesma TRANSAÇÃO
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId()); // recebe o cliente cadastrado
		updateData(newObj, obj); // atualiza o ja cadastrado com o nome e email que veio na requisição
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir pois há pedidos relacionados");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest= PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		//throw new UnsupportedOperationException(); // ainda não implementado
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	//SobreCarga 	--	CRIANDO UM CLIENTE com seus respectivos endereço/telefones
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		// o springboot cria uma referencia do id da CIDADE no endereço
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1()); // obrigatorio
		if (objDto.getTelefone2()!= null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!= null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
