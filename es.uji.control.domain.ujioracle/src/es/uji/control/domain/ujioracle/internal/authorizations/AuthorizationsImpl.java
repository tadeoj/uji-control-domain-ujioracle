/*******************************************************************************
 * Copyright © Universitat Jaume I de Castelló 2015.
 * Aquest programari es distribueix sota les condicions de llicència EUPL 
 * o de qualsevol altra que la substituisca en el futur.
 * La llicència completa es pot descarregar de 
 * https://joinup.ec.europa.eu/community/eupl/og_page/european-union-public-licence-eupl-v11
 *******************************************************************************/
package es.uji.control.domain.ujioracle.internal.authorizations;

import java.sql.Connection;

import es.uji.control.domain.provider.service.connectionfactory.ControlConnectionException;
import es.uji.control.domain.provider.subsystem.authorizations.IAuthorizationsService;

public class AuthorizationsImpl implements IAuthorizationsService {

	public AuthorizationsImpl(Connection connection) throws ControlConnectionException {
		throw new ControlConnectionException("No implementado.");
	}
}
