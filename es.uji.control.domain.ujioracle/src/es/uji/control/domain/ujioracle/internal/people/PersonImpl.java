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

import es.uji.control.domain.people.AccreditationBuilder;
import es.uji.control.domain.people.AccreditationType;
import es.uji.control.domain.people.IAccreditation;
import es.uji.control.domain.people.IPersonIdentifier;
import es.uji.control.domain.people.IPhotoStream;
import es.uji.control.domain.people.LinkageBuilder;
import es.uji.control.domain.people.PersonBuilder;
import es.uji.control.domain.people.PersonIdentifierBuilder;
import es.uji.control.domain.people.PersonIdentifierType;
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
	public void getAllPersons(IPersonStream personStream) throws ControlConnectionException {

		List<UJIPerson> ujiPersonList = new ArrayList<>(1);
		List<UJICard> ujiCardList = new ArrayList<>(1);
		List<UJILinkage> ujiLinkageList = new ArrayList<>(1);

		try {
			entityManager.getTransaction().begin();

			TypedQuery<UJIPerson> personsQuery = entityManager.createQuery("select x from UJIPerson x order by x.perId", UJIPerson.class);
			ujiPersonList = personsQuery.getResultList();

			TypedQuery<UJICard> cardsQuery = entityManager.createQuery("select x from UJICard x", UJICard.class);
			ujiCardList = cardsQuery.getResultList();

			TypedQuery<UJILinkage> linkagesQuery = entityManager.createQuery("select x from UJILinkage x", UJILinkage.class);
			ujiLinkageList = linkagesQuery.getResultList();

			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			entityManager.clear();
			throw new ControlConnectionException("No se ha podido consultar la B.D.", ex.getCause());
		}
		
		for (UJIPerson ujiPerson : ujiPersonList) {
			
			PersonIdentifierBuilder idBuilder = new PersonIdentifierBuilder();
			idBuilder.setType(PersonIdentifierType.GENERAL_LONG_ID);
			idBuilder.setRaw(Long.toString(ujiPerson.getPerId()));
			
			PersonBuilder personBuilder = new PersonBuilder();
			personBuilder.setId(idBuilder.build());
			personBuilder.setName(ujiPerson.getNombre());
			personBuilder.setFirstLastName(ujiPerson.getApellido1());
			personBuilder.setSecondLastName(ujiPerson.getApellido2());
			personBuilder.setIdentification(ujiPerson.getIdentificacion());
			
			for (UJICard ujiCard : ujiCardList) {
				
				if (ujiCard.getPerId() == ujiPerson.getPerId()) {
					AccreditationBuilder accreditationBuilder = new AccreditationBuilder();
					accreditationBuilder.setType(AccreditationType.MIFARE_SERIAL_NUMBER);
					accreditationBuilder.setRaw(Long.toString(ujiCard.getSerialNumber()));
					accreditationBuilder.setEmisionDate(ujiCard.getFEmision());
					accreditationBuilder.setCancelationDate(ujiCard.getFCancelacion());
					
					personBuilder.addAccreditation(accreditationBuilder.build());
				}
				
			}
			
			for (UJILinkage ujiLinkage : ujiLinkageList) {
				
				if (ujiLinkage.getId().getId() == ujiPerson.getPerId()) {
					LinkageBuilder linkageBuilder = new LinkageBuilder();
					linkageBuilder.setName(ujiLinkage.getSviNombre());
					
					personBuilder.addLinkage(linkageBuilder.build());
				}
				
			}
			
		}

	}


	@Override
	public void getPerson(IAccreditation accreditation, IPersonStream personStream) throws ControlConnectionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAllPhotos(IPhotoStream photoStream) throws ControlConnectionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPhoto(IPersonIdentifier personIdentifier, IPhotoStream photoStream)
			throws ControlConnectionException {
		// TODO Auto-generated method stub
		
	}

}
