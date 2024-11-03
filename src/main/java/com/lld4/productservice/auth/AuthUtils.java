package com.lld4.productservice.auth;

import com.lld4.productservice.dtos.FakeStoreProductDto;
import com.lld4.productservice.dtos.UserDto;
import com.lld4.productservice.models.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/*
    The purpose of this class is to validate if used have valid token to get access
 */


@Component
public class AuthUtils {
    RestClient restClient;
    Logger logger = LoggerFactory.getLogger(AuthUtils.class);

    AuthUtils(RestClient restClient) {
        this.restClient = restClient;
    }

    public UserDto validateToken(Token token) {
        UserDto userDto = null;
        try {
            userDto = restClient.post().uri("http://localhost:8080/users/validateToken").body(token).retrieve().body(UserDto.class);
        } catch (Exception e) {
           logger.info("Error while validating token");
        }

        return userDto;
    }

}
