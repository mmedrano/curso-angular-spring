package com.bitonelabs.clientesapi.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bitonelabs.clientesapi.exception.ClienteNoEncontradoException;
import com.bitonelabs.clientesapi.model.dao.IClienteDao;
import com.bitonelabs.clientesapi.model.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		cliente.preCreacion();
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElseThrow(
				() -> new ClienteNoEncontradoException("Cliente no encontrado."));
	}

	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	public Cliente update(Long id, Cliente cliente) {
		Cliente clienteActual = findById(id);
		clienteActual.preActualizacion(cliente);
		return clienteDao.save(clienteActual);
	}


}
