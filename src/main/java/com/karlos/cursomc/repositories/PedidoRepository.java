package com.karlos.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.karlos.cursomc.domain.Cliente;
import com.karlos.cursomc.domain.Pedido;


public interface PedidoRepository extends  JpaRepository<Pedido, Integer>{

	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
