package es.uji.control.domain.ujioracle.internal.people;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotosConnectionContext implements Closeable {

	final private Connection connection;

	private PreparedStatement getAllPhotos;

	public PhotosConnectionContext(Connection connection) throws SQLException {
		// Se anota la conexion
		this.connection = connection;
		// Se prepara la sentencia
		try {
			getAllPhotos = getConnection().prepareStatement("SELECT * FROM GRI_PER.PER_FOTOS");
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
	
	protected ResultSet getAllPhotosResultSet() throws SQLException {
		return getAllPhotos.executeQuery();
	}

	@Override
	public void close() throws IOException {
		if (getAllPhotos != null) {
    		try {
    			getAllPhotos.close();
			} catch (SQLException e) {
			} finally {
				getAllPhotos = null;
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
