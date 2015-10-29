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
import javax.persistence.Embeddable;

@Embeddable
public class UJILinkagePK implements Serializable {

	private static final long serialVersionUID = -7026723135464092509L;

	@Column(name = "ID")
	private long id;

	@Column(name = "SVI_ID")
	private long sviId;

	public UJILinkagePK() {
	}

	public long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public long getSviId() {
		return sviId;
	}

	public void setSviId(final long sviId) {
		this.sviId = sviId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (sviId ^ (sviId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UJILinkagePK other = (UJILinkagePK) obj;
		if (id != other.id)
			return false;
		if (sviId != other.sviId)
			return false;
		return true;
	}

}
