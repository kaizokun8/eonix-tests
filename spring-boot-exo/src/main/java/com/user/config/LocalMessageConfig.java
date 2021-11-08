package com.user.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class LocalMessageConfig {

    /*
    * configuration message source, emplacement et encodage
    * */
    @Bean
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource ();

        messageSource.setBasename("classpath:messages");

        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    /*
     * permet la récupération des messages à partir des clés associé aux validations
     * */
    @Bean
    @Primary
    public LocalValidatorFactoryBean getValidator() {

        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

        bean.setValidationMessageSource(messageSource());

        return bean;
    }
}
