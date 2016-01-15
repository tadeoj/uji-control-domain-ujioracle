package es.uji.control.domain.ujioracle.internal.people;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonConnectionContext implements Closeable {
	
	final private Connection connection;
	
	private PreparedStatement getAllPersonsStatement;
	private PreparedStatement getAccreditationInfoStatement;
	private PreparedStatement getLinkagesStatement;

	public PersonConnectionContext(Connection connection) throws SQLException {
		// Se anota la conexion
		this.connection = connection;
		// Se preparan las sentencias
		try {
//			getAllPersonsStatement = getConnection().prepareStatement("SELECT PER_ID, IDENTIFICACION, APELLIDO1, APELLIDO2, NOMBRE FROM GRI_PER.SCU_V_PERSONAS WHERE APELLIDO1 = 'Garcia'");
			getAllPersonsStatement = getConnection().prepareStatement("SELECT PER_ID, IDENTIFICACION, APELLIDO1, APELLIDO2, NOMBRE FROM GRI_PER.SCU_V_PERSONAS");
			getAccreditationInfoStatement = getConnection().prepareStatement("SELECT PER_ID, SERIAL_NUMBER, F_EMISION, F_CANCELACION, DESCRIPCION FROM GRI_PER.SCU_V_TARJETAS WHERE PER_ID=?");
			getLinkagesStatement = getConnection().prepareStatement("SELECT ID, SVI_NOMBRE FROM GRI_PER.PER_VW_PERSONAS_SUBVINCULOS WHERE ID=?");
		} catch (SQLException sqlEx) {
			try {
				close();
			} catch (IOException e) {
			}
			throw sqlEx;
		}
	}
	
	private Connection getConnection() {
		return connection;
	}
	
	protected ResultSet getAllPersonsResultSet() throws SQLException {
		return getAllPersonsStatement.executeQuery();
	}
	
	protected ResultSet getAccreditationInfoResultSet(long perId) throws SQLException {
		getAccreditationInfoStatement.clearParameters();
		getAccreditationInfoStatement.setLong(1, perId);
		return getAccreditationInfoStatement.executeQuery();
	}

	protected ResultSet getLinkagesStatementResultSet(long perId) throws SQLException {
		getLinkagesStatement.clearParameters();
		getLinkagesStatement.setLong(1, perId);
		return getLinkagesStatement.executeQuery();
	}

    public void close() throws IOException {
    	if (getLinkagesStatement != null) {
    		try {
				getLinkagesStatement.close();
			} catch (SQLException e) {
			} finally {
				getLinkagesStatement = null;
			}
    	}
    	if (getAccreditationInfoStatement != null) {
    		try {
    			getAccreditationInfoStatement.close();
			} catch (SQLException e) {
			} finally {
				getAccreditationInfoStatement = null;
			}
    	}
    	if (getAllPersonsStatement != null) {
    		try {
    			getAllPersonsStatement.close();
			} catch (SQLException e) {
			} finally {
				getAllPersonsStatement = null;
			}
    	}
    	if (connection != null) {
    		try {
    			connection.close();
			} catch (SQLException e) {
			} finally {
			}
    	}
    }
    
}
