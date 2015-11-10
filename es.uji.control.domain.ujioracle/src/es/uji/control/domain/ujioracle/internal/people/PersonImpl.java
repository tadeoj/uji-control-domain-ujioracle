/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal.people;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import es.uji.control.domain.people.IAccreditation;
import es.uji.control.domain.people.IPersonIdentifier;
import es.uji.control.domain.people.IPhotoStream;
import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.subsystem.people.IPersonService;
import es.uji.control.domain.provider.subsystem.people.IPersonStream;
import es.uji.control.domain.ujioracle.internal.people.entities.UJICard;
import es.uji.control.domain.ujioracle.internal.people.entities.UJILinkage;
import es.uji.control.domain.ujioracle.internal.people.entities.UJIPerson;

public class PersonImpl implements IPersonService {

	private EntityManager entityManager;

	public PersonImpl(EntityManager entityManager) throws ControlConnectionException {
		this.entityManager = entityManager;
	}

	@Override
	public IPersonStream getAllPersons() throws ControlConnectionException {
		
		List<UJIPerson> ujiPersonList = new ArrayList<>(1);
		List<UJICard> ujiCardList = new ArrayList<>(1);
		List<UJILinkage> ujiLinkageList = new ArrayList<>(1);
		
		try {
			entityManager.getTransaction().begin();
			
			TypedQuery<UJIPerson> personsQuery = entityManager.createQuery("select x from UJIPerson x order by x.perId", UJIPerson.class);
			ujiPersonList = personsQuery.getResultList();
			
			TypedQuery<UJICard> cardsQuery = entityManager.createQuery("select x from UJIPerson x order by x.perId", UJICard.class);
			ujiCardList = cardsQuery.getResultList();
			
//			TypedQuery<UJILinkage> personsQuery = entityManager.createQuery("select x from UJILinkage x order by x.perId", UJIPerson.class);
//			ujiPersonList = personsQuery.getResultList();
//	
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			entityManager.clear();
			throw new ControlConnectionException("No se ha podido consultar la B.D.", ex.getCause());
		}
		
		return null; // TODO: Implementar
	}

	@Override
	public IPhotoStream getAllPhotos() throws ControlConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersonStream getPerson(IAccreditation accreditation) throws ControlConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPhotoStream getPhoto(IPersonIdentifier personIdentifier) throws ControlConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

}
