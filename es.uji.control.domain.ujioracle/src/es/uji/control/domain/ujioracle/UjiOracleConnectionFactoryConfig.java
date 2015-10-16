package es.uji.control.domain.ujioracle;

public interface UjiOracleConnectionFactoryConfig {
	
	public abstract void setURL(String url, String user, String password, String schema, String sid);
	
	public abstract String getURL();
	public abstract String getUser();
	public abstract String getPassword();
	public abstract String getSchema();
	public abstract String getSid();
	
}
