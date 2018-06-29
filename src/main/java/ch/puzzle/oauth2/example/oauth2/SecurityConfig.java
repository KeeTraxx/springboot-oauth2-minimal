package ch.puzzle.oauth2.example.oauth2;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_ANT_MATCHERS = {"/", "/login**", "/webjars/**", "/error**"};

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // unprotect PUBLIC
                .antMatchers(PUBLIC_ANT_MATCHERS)
                .permitAll()

                // others need to be authenticated
                .anyRequest()
                .authenticated();
    }
}
