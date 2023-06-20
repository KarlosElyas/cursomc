package com.karlos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karlos.cursomc.domain.Pagamento;

public interface PagamentoRepository extends  JpaRepository<Pagamento, Integer>{

}
