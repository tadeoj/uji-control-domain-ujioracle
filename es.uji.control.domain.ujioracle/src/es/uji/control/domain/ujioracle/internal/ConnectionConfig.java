/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal;

final public class ConnectionConfig {

	private String url;
	private String user;
	private String password;
	private String schema;
	private String sid;
	private boolean valid;

	public ConnectionConfig(String url, String user, String password, String schema, String sid, boolean valid) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.schema = schema;
		this.sid = sid;
		this.valid = valid;
	}
	
	public String getURL() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSchema() {
		return schema;
	}
	
	public String getSid() {
		return sid;
	}
	
	public boolean isValid() {
		return valid;
	}

}
