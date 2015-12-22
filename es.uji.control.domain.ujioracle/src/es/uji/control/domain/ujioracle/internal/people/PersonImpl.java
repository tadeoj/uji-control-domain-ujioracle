/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal.people;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import es.uji.control.domain.people.IAccreditation;
import es.uji.control.domain.people.IPersonIdentifier;
import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.subsystem.people.IPersonService;
import es.uji.control.domain.provider.subsystem.people.IPersonStream;
import es.uji.control.domain.provider.subsystem.people.IPhotoStream;

public class PersonImpl implements IPersonService {

	final private PersonConnectionContext context;

	public PersonImpl(Connection connection) throws ControlConnectionException {
		try {
			this.context = new PersonConnectionContext(connection);
		} catch (SQLException e) {
			throw new ControlConnectionException("Impopsible obtener los recursos necesarios para conextar con la base de datos", e);
		}
	}

	@Override
	public void getAllPersons(IPersonStream personStream) throws ControlConnectionException {
		AllPersonCall call = new AllPersonCall(context, personStream);
		call.run();
	}
	
	@Override
	public void getPerson(IAccreditation accreditation, IPersonStream personStream) throws ControlConnectionException {
		throw new ControlConnectionException("No implementado.");
	}

	@Override
	public void getAllPhotos(IPhotoStream photoStream) throws ControlConnectionException {
		throw new ControlConnectionException("No implementado.");
	}

	@Override
	public void getPhoto(IPersonIdentifier personIdentifier, IPhotoStream photoStream) throws ControlConnectionException {
		throw new ControlConnectionException("No implementado.");
	}
	
	public void reset() {
		try {
			context.close();
		} catch (IOException e) {
		}
	}

}
