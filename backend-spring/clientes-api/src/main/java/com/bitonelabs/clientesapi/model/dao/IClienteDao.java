package com.bitonelabs.clientesapi.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bitonelabs.clientesapi.model.entity.Cliente;

@Repository
public interface IClienteDao extends CrudRepository<Cliente, Long> {

}
