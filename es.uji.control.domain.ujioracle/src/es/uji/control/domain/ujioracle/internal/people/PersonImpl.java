/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal.people;

import java.util.Set;

import javax.persistence.EntityManager;

import es.uji.control.domain.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.subsystem.people.ILinkage;
import es.uji.control.domain.subsystem.people.IPersonIdentifier;
import es.uji.control.domain.subsystem.people.IPersonService;
import es.uji.control.domain.subsystem.people.IPersonStream;
import es.uji.control.domain.subsystem.people.IPhotoStream;

public class PersonImpl implements IPersonService {

	private EntityManager entityManager;

	public PersonImpl(EntityManager entityManager) throws ControlConnectionException {
		this.entityManager = entityManager;
	}

	@Override
	public IPersonStream getAllPersons() throws ControlConnectionException {
		return null; // TODO: Implementar
	}

	@Override
	public IPersonStream getPerson(IPersonIdentifier personIdentifier) throws ControlConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPhotoStream getAllPhotos() throws ControlConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ILinkage> getLinkages() throws ControlConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

}
