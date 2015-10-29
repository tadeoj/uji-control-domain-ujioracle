/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal.people.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="PER_FOTOS", schema = "GRI_PER")
public class UJIPhoto implements Serializable {

	private static final long serialVersionUID = -5652875891581018834L;

	@Id
	@Column(name="PER_ID")
	private long perId;

	@Column(name="AUTORIZA_AULAVIRTUAL")
	private String autorizaAulavirtual;

	@Column(name="AUTORIZA_LDAP")
	private String autorizaLdap;

	@Column(name="BINARIO")
	@Lob
	private byte[] binario;

	@Column(name="FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Column(name="FECHA_RECHAZO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRechazo;

	@Column(name="MIME_TYPE")
	private String mimeType;

	@Column(name="NOMBRE_FICHERO")
	private String nombreFichero;

	@Column(name = "PROCEDENCIA")
	private String procedencia;

	public UJIPhoto() {
	}

	public long getPerId() {
		return this.perId;
	}

	public void setPerId(long perId) {
		this.perId = perId;
	}

	public String getAutorizaAulavirtual() {
		return this.autorizaAulavirtual;
	}

	public void setAutorizaAulavirtual(String autorizaAulavirtual) {
		this.autorizaAulavirtual = autorizaAulavirtual;
	}

	public String getAutorizaLdap() {
		return this.autorizaLdap;
	}

	public void setAutorizaLdap(String autorizaLdap) {
		this.autorizaLdap = autorizaLdap;
	}

	public byte[] getBinario() {
		return this.binario;
	}

	public void setBinario(byte[] binario) {
		this.binario = binario;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaRechazo() {
		return this.fechaRechazo;
	}

	public void setFechaRechazo(Date fechaRechazo) {
		this.fechaRechazo = fechaRechazo;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getNombreFichero() {
		return this.nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getProcedencia() {
		return this.procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

}