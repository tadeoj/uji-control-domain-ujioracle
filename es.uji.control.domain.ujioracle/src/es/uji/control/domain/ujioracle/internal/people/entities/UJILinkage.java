/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal.people.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PER_VW_PERSONAS_SUBVINCULOS", schema = "GRI_PER")
public class UJILinkage implements Serializable {

	private static final long serialVersionUID = -5142312228511520466L;

	@EmbeddedId
	private UJILinkagePK id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

	@Column(name = "IDENTIFICACION")
	private String identificacion;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name="SVI_NOMBRE")
	private String sviNombre;

	@Column(name="VIN_ID")
	private long vinId;

	@Column(name="VIN_NOMBRE")
	private String vinNombre;

	public UJILinkage() {
	}

	public UJILinkagePK getId() {
		return this.id;
	}

	public void setId(UJILinkagePK id) {
		this.id = id;
	}
	
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
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

	public String getSviNombre() {
		return this.sviNombre;
	}

	public void setSviNombre(String sviNombre) {
		this.sviNombre = sviNombre;
	}

	public long getVinId() {
		return this.vinId;
	}

	public void setVinId(long vinId) {
		this.vinId = vinId;
	}

	public String getVinNombre() {
		return this.vinNombre;
	}

	public void setVinNombre(String vinNombre) {
		this.vinNombre = vinNombre;
	}

}