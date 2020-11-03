package com.bitonelabs.clientesapi.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El nombre no puede ser vacio.")
	@Size(min = 4, max = 12, message = "El nombre debe tener entre 4 y 12 caracteres.")
	@Column(name = "VC_NOMBRE", nullable = false)
	private String nombre;
	
	@NotEmpty(message = "El apellido no puede ser vacio.")
	@Column(name = "VC_APELLIDO", nullable = false)
	private String apellido;
	
	@NotEmpty(message = "El email no puede ser vacio.")
	@Email(message = "El email no tiene un formato válido.")
	@Column(name = "VC_EMAIL", unique = true)
	private String email;
	
	@Column(name = "DT_CREATEAT")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	public void preCreacion() {
		setCreateAt(new Date());
	}
	
	public void preActualizacion(Cliente clienteConDatosnuevos) {
		setNombre(clienteConDatosnuevos.getNombre());
		setApellido(clienteConDatosnuevos.getApellido());
		setEmail(clienteConDatosnuevos.getEmail());
	}
}
