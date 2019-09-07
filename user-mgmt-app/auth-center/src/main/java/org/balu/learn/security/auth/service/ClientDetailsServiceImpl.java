package org.balu.learn.security.auth.service;

import java.util.Optional;

import org.balu.learn.security.auth.repository.BaluOauth2Properties;
import org.balu.learn.security.auth.repository.BaluOauth2Properties.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public class ClientDetailsServiceImpl implements ClientDetailsService {
	
	@Autowired
	private BaluOauth2Properties brandixSecurityOauth2Properties;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Optional<Client> optionalClient = 
			brandixSecurityOauth2Properties
				.getClients()
					.stream()
					.filter(client -> clientId.equalsIgnoreCase(client.getClientId()))
					.findFirst();
		
		if(optionalClient.isPresent()) {
			return optionalClient.get().getClientDetails();
		}
		return null;
	}

}
