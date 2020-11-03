package com.bitonelabs.clientesapi.model.service;

import java.util.List;

import com.bitonelabs.clientesapi.model.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Cliente save(Cliente cliente);
	
	public Cliente update(Long id, Cliente cliente);
	
	public Cliente findById(Long id);
	
	public void delete(Long id);
	
}
