package com.apress.integration.bean;

import io.quarkus.arc.Unremovable;
import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.Tokens;
import org.apache.camel.Message;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Unremovable
@Named("tokenHandlerBean")
public class TokenHandlerBean {

    @Inject
    OidcClient client;

    volatile Tokens currentTokens;

    @PostConstruct
    public void init() {
        currentTokens = client.getTokens().await().indefinitely();
    }

    public void insertToken(Message message){

        Tokens tokens = currentTokens;
        if (tokens.isAccessTokenExpired()) {
            tokens = client.refreshTokens(tokens.getRefreshToken()).await().indefinitely();
            currentTokens = tokens;
        }

        message.setHeader("Authorization", "Bearer " + tokens.getAccessToken() );
    }
}
