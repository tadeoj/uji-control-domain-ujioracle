/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal.people.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SCU_V_PERSONAS", schema = "GRI_PER")
public class UJIPerson implements Serializable {

	private static final long serialVersionUID = 8279702279618471431L;

	@Column(name="APELLIDO1")
	private String apellido1;

	@Column(name="APELLIDO2")
	private String apellido2;

	@Column(name="IDENTIFICACION")
	private String identificacion;

	@Column(name="NOMBRE")
	private String nombre;

	@Id
	@Column(name="PER_ID")
	private long perId;

	@Column(name="VINCULO")
	private String vinculo;

	public UJIPerson() {
	}

	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getPerId() {
		return this.perId;
	}

	public void setPerId(long perId) {
		this.perId = perId;
	}

	public String getVinculo() {
		return this.vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

}