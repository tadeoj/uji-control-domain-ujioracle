package es.uji.control.domain.ujioracle.internal.authorizations;

import javax.persistence.EntityManager;

import es.uji.control.domain.subsystem.authorizations.IAuthorizationsService;

public class AuthorizationsImpl implements IAuthorizationsService {

	private EntityManager entityManager;

	public AuthorizationsImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
