package com.jwst.manage.model;

import java.util.Date;

/**
 * public key value object
 */
public class PublicKey {
	Long id;
	// Long userId;
	// String username;
	String keyNm;
	String publicKey;
	String type;
	String fingerprint;
	boolean enabled;
	Date createDt;
	Profile profile;
	String passphrase;
	String passphraseConfirm;

	public String getKeyNm() {
		return keyNm;
	}

	public void setKeyNm(String keyNm) {
		this.keyNm = keyNm;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassphraseConfirm() {
		return passphraseConfirm;
	}

	public void setPassphraseConfirm(String passphraseConfirm) {
		this.passphraseConfirm = passphraseConfirm;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
}
