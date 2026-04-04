package com.winner.client.global.config.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConditionalOnClass(name = "io.jsonwebtoken.Jwts")
public class JwtConfig {

}
