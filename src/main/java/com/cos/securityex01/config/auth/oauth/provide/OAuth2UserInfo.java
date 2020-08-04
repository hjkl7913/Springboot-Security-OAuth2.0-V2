package com.cos.securityex01.config.auth.oauth.provide;

public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
