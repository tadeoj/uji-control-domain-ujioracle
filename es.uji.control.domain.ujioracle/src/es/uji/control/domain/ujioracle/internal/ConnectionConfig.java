package es.uji.control.domain.ujioracle.internal;

final public class ConnectionConfig {

	private String url;
	private String user;
	private String password;
	private String schema;
	private String sid;

	public ConnectionConfig(String url, String user, String password, String schema, String sid) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.schema = schema;
		this.sid = sid;
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
		return false;
	}

}
