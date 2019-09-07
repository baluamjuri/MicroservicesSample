package org.balu.learn.security.auth.config;

import org.balu.learn.security.auth.repository.BaluOauth2Properties;
import org.balu.learn.security.auth.service.ClientDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;



@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
    private AuthenticationManager authenticationManager;	
	
	@Autowired
	private BaluOauth2Properties baluOauth2Properties;
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients)throws Exception {
		clients.withClientDetails(new ClientDetailsServiceImpl());
    }
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)throws Exception {
        endpoints.tokenStore(tokenStore())
        		.tokenEnhancer(jwtTokenEnhancer())
          		.authenticationManager(authenticationManager);
    }
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

	private JwtAccessTokenConverter jwtTokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(baluOauth2Properties.getJwtKey());
        return converter;
	}
	
	
	@Override
    public void configure(
      AuthorizationServerSecurityConfigurer oauthServer) 
      throws Exception {
        oauthServer
        	.tokenKeyAccess("permitAll()")
        	.checkTokenAccess("isAuthenticated()");
    }
}
