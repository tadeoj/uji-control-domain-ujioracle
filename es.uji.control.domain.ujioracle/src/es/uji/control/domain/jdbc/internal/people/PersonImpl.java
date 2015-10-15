package es.uji.control.domain.jdbc.internal.people;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import es.uji.control.domain.ControlConnectionException;
import es.uji.control.domain.jdbc.internal.StatementCleaner;
import es.uji.control.domain.people.ILinkage;
import es.uji.control.domain.people.IPersonIdentifier;
import es.uji.control.domain.people.IPersonService;
import es.uji.control.domain.people.IPersonStream;
import es.uji.control.domain.people.IPhotoStream;

public class PersonImpl implements IPersonService {

	private Connection connection;
	private PreparedStatement getStatement;
	
	private StatementCleaner sc = new StatementCleaner();
	
	public PersonImpl(Connection connection) throws ControlConnectionException {
		this.connection = connection;
		try {
			this.sc.add(getStatement = this.connection.prepareStatement("SELECT ID FROM APP.MBE_ALARMS WHERE INACTIVETS IS NULL"));
		} catch (SQLException sqlEx) {
			throw new ControlConnectionException("Imposible crear las sentencias JDBC", sqlEx);
		}
	}

	public void reset() {
		sc.clear();
	}

	@Override
	public IPersonStream getAllPersons() throws ControlConnectionException {
//		try {
//			getStatement.setLong(1, alarmIdentifier.getId());
//			ResultSet rset = getStatement.executeQuery();
//			if (rset.next()) {
//				try {
//	        		return AlarmData.createFromTable(
//							rset.getInt(1),						// objectType
//							rset.getLong(2),					// objectId
//							rset.getTimestamp(3),				// activeTs
//							rset.getTimestamp(4),				// inactiveTs
//							rset.getInt(5),						// alarmFamily
//							rset.getInt(6),						// alarmType
//							rset.getString(7)					// message
//						);
//				} catch (InvalidParameterException e) {
//					throw instantiateEngineDAOException("Se ha encontado un registro en la tabla con valores incompatibles", e);
//				}
//			} else {
//				throw instantiateEngineDAOException("No se ha encontradro una alarma para el Id indicado.", null);
//			}
//		} catch (SQLException sqlEx) {
//			throw instantiateEngineDAOException("No se puede obtener la alarma solicitada.", sqlEx);
//		}
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
