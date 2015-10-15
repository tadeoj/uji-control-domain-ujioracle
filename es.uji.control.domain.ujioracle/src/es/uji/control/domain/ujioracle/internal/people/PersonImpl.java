package es.uji.control.domain.ujioracle.internal.people;

import java.sql.Connection;
import java.util.Set;

import es.uji.control.domain.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.subsystem.people.ILinkage;
import es.uji.control.domain.subsystem.people.IPersonIdentifier;
import es.uji.control.domain.subsystem.people.IPersonService;
import es.uji.control.domain.subsystem.people.IPersonStream;
import es.uji.control.domain.subsystem.people.IPhotoStream;

public class PersonImpl implements IPersonService {

	public PersonImpl(Connection connection) throws ControlConnectionException {
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
