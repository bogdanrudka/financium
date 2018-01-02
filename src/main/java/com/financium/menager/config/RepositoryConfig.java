package com.financium.menager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class RepositoryConfig {

    @Bean
    public AuditorAware auditorProvider() {
        return new AuditorAware() {
            @Override
            public Object getCurrentAuditor() {
                return "brudka";
            }
        };
    }

}
