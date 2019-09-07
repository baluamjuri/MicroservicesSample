package org.balu.learn.security.auth.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("balu.security.oauth2")
@Getter
@Setter
public class BaluOauth2Properties {

	private List<Client> clients = new ArrayList<>();
	private String jwtKey;
	
	@Getter
	@Setter
	public static class Client{
		private String clientId;
	    private String clientSecret;
	    private List<String> authorizedGrantTypes; 
	    private List<String> scope;
	    private int accessTokenValiditySeconds;
	    private int refreshTokenValiditySeconds;
	    private BaseClientDetails clientDetails = new BaseClientDetails();
	    
	    public ClientDetails getClientDetails() {
	    	clientDetails.setClientId(clientId);
	    	clientDetails.setClientSecret(clientSecret);
	    	clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
	    	clientDetails.setScope(scope);
	    	clientDetails.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
	    	clientDetails.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
	    	return clientDetails;
	    }
	}
}
