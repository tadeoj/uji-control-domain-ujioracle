package es.uji.control.domain.jdbc.internal;

import es.uji.control.domain.jdbc.IJdbcPeopleDomainConnectionGetter;

public class GetterImpl implements IJdbcPeopleDomainConnectionGetter {
	
	final private String url;
	
	public GetterImpl(IJdbcPeopleDomainConnectionGetter getter) {
		this.url = getter.getURL();
	}

	@Override
	public String getURL() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetterImpl other = (GetterImpl) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
