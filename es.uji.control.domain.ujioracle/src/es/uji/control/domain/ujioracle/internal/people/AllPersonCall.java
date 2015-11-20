package es.uji.control.domain.ujioracle.internal.people;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.uji.control.domain.people.IAccreditationInfo;
import es.uji.control.domain.people.ILinkage;
import es.uji.control.domain.people.IPerson;
import es.uji.control.domain.people.IPersonIdentifier;
import es.uji.control.domain.people.PersonBuilder;
import es.uji.control.domain.people.PersonIdentifierBuilder;
import es.uji.control.domain.people.PersonIdentifierType;
import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.subsystem.people.IPersonStream;

public class AllPersonCall {
	
	final static int BLOCK_SIZE = 50;
	
	final private PersonConnectionContext context;
	
	final private IPersonStream personStream;

	public AllPersonCall(PersonConnectionContext context, IPersonStream personStream) {
		this.context = context;
		this.personStream = personStream;
	}
	
	public void run() throws ControlConnectionException {
		
		try {
			for (;;) {
				List<IPerson> block = getBlockOfPersons();
				if (block.size() == 0) {
					personStream.onCompleted();
					break;
				} else {
					personStream.onNext(block);
				}
			}
		} catch (Exception ex) {
			personStream.onError(ex);
		}
		
	}
	
	private List<IPerson> getBlockOfPersons() throws Exception {
		
		ArrayList<IPerson> persons = new ArrayList<>();
		
		ResultSet rset = context.getAllPersonsResultSet();
		
		while (rset.next() && persons.size() >= BLOCK_SIZE) {
			
			// Se extrae el perId
			long perId = rset.getLong(1);
			
			// La lista de acreditaciones
			List<IAccreditationInfo> accreditationsInfo = getAcreditationsInfo(perId);
			
			// La lista de vinculaciones
			List<ILinkage> linkages = getLinkages(perId);
			
			// El identificador de la persona
			IPersonIdentifier personIdentifier = new PersonIdentifierBuilder()
					.setType(PersonIdentifierType.GENERAL_LONG_ID)
					.setRaw(PersonIdentifierType.generalLongIdToBytes(perId)).build();
			
			// La persona
			String name = rset.getString(2);if (rset.wasNull()) { name = ""; }
			String firstLastName = rset.getString(3);if (rset.wasNull()) { name = ""; }
			String secondLastName = rset.getString(4);if (rset.wasNull()) { name = ""; }
			IPerson person = new PersonBuilder()
					.setId(personIdentifier)
					.setAccreditationsInfo(accreditationsInfo)
					.setLinkages(linkages)
					.setName(name)
					.setFirstLastName(firstLastName)
					.setSecondLastName(secondLastName)
					.build();
			
			// Se incluye en la lista
			persons.add(person);
		}
		
		return Collections.unmodifiableList(persons);
	}
	
	private List<IAccreditationInfo> getAcreditationsInfo(long perId) throws Exception {
		ArrayList<IAccreditationInfo> acreditationsInfo = new ArrayList<>();
		return Collections.unmodifiableList(acreditationsInfo);
	}
	
	private List<ILinkage> getLinkages(long perId) throws Exception {
		ArrayList<ILinkage> linkages = new ArrayList<>();
		return Collections.unmodifiableList(linkages);
	}
	
}
