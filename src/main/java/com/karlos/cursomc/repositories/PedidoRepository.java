package com.karlos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karlos.cursomc.domain.Pedido;

public interface PedidoRepository extends  JpaRepository<Pedido, Integer>{

}
