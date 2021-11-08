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
     * Desactive de validation au moment de la persistence pour ne la conserver qu'à l'appel des controleurs Rest
     * */
    @Bean
    @Lazy
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> hibernateProperties.put("javax.persistence.validation.mode", "none");
    }

}