package com.bitonelabs.clientesapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bitonelabs.clientesapi.exception.ClienteNoEncontradoException;
import com.bitonelabs.clientesapi.model.entity.Cliente;
import com.bitonelabs.clientesapi.model.service.IClienteService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/clientes")
	public List<Cliente> get() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Cliente clienteEncontrado = null;
		Map<String, Object> response = new HashMap<>();
		try {
			clienteEncontrado = clienteService.findById(id);
			return new ResponseEntity<Cliente>(clienteEncontrado, HttpStatus.OK);
		} catch (ClienteNoEncontradoException e) {
			response.put("mensaje", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			response.put("mensaje", "Ocurrió un error al obtener el usuario.");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult resultadoValidacion) {
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = null;
		
		if (resultadoValidacion.hasErrors()) {
			List<String> errores = resultadoValidacion.getFieldErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			status = HttpStatus.BAD_REQUEST;
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, status);
		}
		
		try {
			Cliente clienteAgregado = clienteService.save(cliente);
			response.put("mensaje", "Cliente agregado correctamente.");
			response.put("cliente", clienteAgregado);
			status = HttpStatus.CREATED;
		} catch (Exception e) {
			response.put("mensaje", "Ocurrió un error al crear al cliente.");
			response.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(response, status);
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult resultadoValidacion ,@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = null;
		
		if (resultadoValidacion.hasErrors()) {
			List<String> errores = resultadoValidacion.getFieldErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.toList());
			status = HttpStatus.BAD_REQUEST;
			response.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(response, status);
		}
		
		try {
			Cliente clienteActualizado = clienteService.update(id, cliente);
			response.put("mensaje", "Cliente actualizado correctamente.");
			response.put("cliente", clienteActualizado);
			status = HttpStatus.OK;
		} catch (ClienteNoEncontradoException e) {
			response.put("mensaje", e.getMessage());
			status = HttpStatus.NOT_FOUND;
		} catch (Exception e) {
			response.put("mensaje", "Ocurrió un error al actualizar los datos del cliente.");
			response.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(response, status);
	}

	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		HttpStatus status = null;
		try {
			clienteService.delete(id);
			response.put("mensaje", "Cliente eliminado correctamente.");
			status = HttpStatus.OK;
		} catch (Exception e) {
			response.put("mensaje", "Ocurrió un error al eliminar el cliente.");
			response.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(response, status);
	}

}
