package es.uji.control.domain.ujioracle.internal.people;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.uji.control.domain.people.AccreditationBuilder;
import es.uji.control.domain.people.AccreditationInfoBuilder;
import es.uji.control.domain.people.AccreditationType;
import es.uji.control.domain.people.IAccreditation;
import es.uji.control.domain.people.IAccreditationInfo;
import es.uji.control.domain.people.ILinkage;
import es.uji.control.domain.people.IPerson;
import es.uji.control.domain.people.IPersonIdentifier;
import es.uji.control.domain.people.LinkageBuilder;
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
			ResultSet rset = context.getAllPersonsResultSet();

			for (;;) {
				List<IPerson> block = getBlockOfPersons(rset);
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

	private List<IPerson> getBlockOfPersons(ResultSet rset) throws Exception {
		ArrayList<IPerson> persons = new ArrayList<>();
		
		while (rset.next() && persons.size() < BLOCK_SIZE) {

			long perId = rset.getLong(1);

			List<IAccreditationInfo> accreditationsInfo = getAcreditationsInfo(perId);

			List<ILinkage> linkages = getLinkages(perId);

			IPersonIdentifier personIdentifier = new PersonIdentifierBuilder()
					.setType(PersonIdentifierType.GENERAL_LONG_ID)
					.setRaw(PersonIdentifierType.generalLongIdToBytes(perId))
					.build();

			String name = rset.getString(5);
			if (rset.wasNull()) {
				name = "";
			}
			String firstLastName = rset.getString(3);
			if (rset.wasNull()) {
				firstLastName = "";
			}
			String secondLastName = rset.getString(4);
			if (rset.wasNull()) {
				secondLastName = "";
			}
			String identification = rset.getString(2);
			if (rset.wasNull()) {
				identification = "";
			}
			IPerson person = new PersonBuilder()
					.setId(personIdentifier)
					.setAccreditationsInfo(accreditationsInfo)
					.setLinkages(linkages)
					.setName(name)
					.setFirstLastName(firstLastName)
					.setSecondLastName(secondLastName)
					.setIdentification(identification)
					.build();

			persons.add(person);
		}

		return Collections.unmodifiableList(persons);
	}

	private List<IAccreditationInfo> getAcreditationsInfo(long perId) throws Exception {
		ArrayList<IAccreditationInfo> acreditationsInfo = new ArrayList<>();

		ResultSet rset = context.getAccreditationInfoResultSet(perId);

		while (rset.next()) {

			long raw = rset.getLong(2);
			
			IAccreditation accreditation = new AccreditationBuilder()
					.setType(AccreditationType.MIFARE_SERIAL_NUMBER)
					.setRaw(AccreditationType.generalLongIdToBytes(raw))
					.build();

			Date emisionDate = rset.getDate(3);
			Date cancelationDate = rset.getDate(4);
						
			LocalDateTime emision = null;
			LocalDateTime cancelation = null;
			
			if (emisionDate != null) {
				emision = LocalDateTime.ofInstant(Instant.ofEpochMilli(emisionDate.getTime()), ZoneId.systemDefault());
			}
			
			if (cancelationDate != null) {
				cancelation = LocalDateTime.ofInstant(Instant.ofEpochMilli(cancelationDate.getTime()), ZoneId.systemDefault());
			}
			
			IAccreditationInfo accreditationsInfo = new AccreditationInfoBuilder()
					.setEmisionDate(emision)
					.setCancelationDate(cancelation)
					.setAccreditation(accreditation)
					.build();
					
			acreditationsInfo.add(accreditationsInfo);
		}

		return Collections.unmodifiableList(acreditationsInfo);
	}

	private List<ILinkage> getLinkages(long perId) throws Exception {
		ArrayList<ILinkage> linkages = new ArrayList<>();
		
		ResultSet rset = context.getLinkagesStatementResultSet(perId);

		while (rset.next()) {
						
			String name = rset.getString(2);
			if (rset.wasNull()) {
				name = "";
			}
			
			ILinkage linkage = new LinkageBuilder()
					.setName(name)
					.build();

			linkages.add(linkage);
		}
		
		return Collections.unmodifiableList(linkages);
	}

}
