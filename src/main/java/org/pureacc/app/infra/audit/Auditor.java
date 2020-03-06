package org.pureacc.app.infra.audit;

public interface Auditor {
	void audit(AuditSuccess success);

	void audit(AuditFailure failure);
}
