package ru.tilman.chambers.enterprise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public SpringSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    private void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        String USER_QUERY = "SELECT login, password, enabled FROM rry5tbl0rdvdvjoc.user WHERE login = ?";
//        String USER_ROLE_QUERY = "SELECT user.login, role.name FROM rry5tbl0rdvdvjoc.user " +
//                "JOIN (rry5tbl0rdvdvjoc.role, rry5tbl0rdvdvjoc.user_role) " +
//                "ON (user.id = user_role.user_id and role.id = user_role.role_id) WHERE user.login = ?;";
        String USER_QUERY = "SELECT login, password, enabled FROM chambers.user WHERE login = ?";
        String USER_ROLE_QUERY = "SELECT user.login, role.name FROM chambers.user   JOIN (role, user_role) " +
                "ON (user.id = user_role.user_id and role.id = user_role.role_id)   WHERE user.login = ?";
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(USER_ROLE_QUERY)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure()

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/hi", "/rest/remove")
                .hasAuthority("admins")
                .antMatchers(HttpMethod.GET, "/chamber")
                .hasAnyAuthority("admins", "user")

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .failureUrl("/loginfailed")

                .and()
                .logout()
                .logoutSuccessUrl("/chambers");
    }
}
