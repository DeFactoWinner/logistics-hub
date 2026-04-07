package com.winner.client.global.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@ConditionalOnClass(name = "jakarta.persistence.EntityManagerFactory")
public class JpaAuditingConfig {

}
