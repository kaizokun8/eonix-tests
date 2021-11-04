package com.user.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public
class SpringValidatorConfiguration {
    /*
     * desactive de validation au moment de la persistence
     * */
    @Bean
    @Lazy
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> hibernateProperties.put("javax.persistence.validation.mode", "none");
    }

}