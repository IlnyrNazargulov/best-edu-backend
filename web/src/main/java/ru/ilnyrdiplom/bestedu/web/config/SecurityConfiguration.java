package ru.ilnyrdiplom.bestedu.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import ru.ilnyrdiplom.bestedu.web.services.JwtAuthenticationConverter;

@PropertySource("classpath:security.properties")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .cacheControl().disable()
                .frameOptions().disable()
                .httpStrictTransportSecurity().disable()
                .xssProtection().disable()
                .and()
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .jee().disable()
                .portMapper().disable()
                .sessionManagement().disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(@Value("${ru.ilnyr.sticker-replacer.security.signing-key}") String signingKey) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }
}
