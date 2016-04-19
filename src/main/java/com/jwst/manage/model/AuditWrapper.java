package com.jwst.manage.model;

public class AuditWrapper {

	SessionOutput sessionOutput;

	public AuditWrapper(SessionOutput sessionOutput) {
		this.sessionOutput = sessionOutput;
	}

	public SessionOutput getSessionOutput() {
		return sessionOutput;
	}

	public void setSessionOutput(SessionOutput sessionOutput) {
		this.sessionOutput = sessionOutput;
	}
}
