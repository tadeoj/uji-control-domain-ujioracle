/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle;

public interface UjiOracleConnectionFactoryConfig {
	
	public abstract void setURL(String url, String user, String password, String schema, String sid);
	
	public abstract String getURL();
	public abstract String getUser();
	public abstract String getPassword();
	public abstract String getSchema();
	public abstract String getSid();
	
}
