package ru.tilman.chamberanalysis.config;

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

    private static String USER_QUERY =
            "SELECT login, password, enabled " +
                    "FROM chambers.user " +
                    "WHERE login = ?";
    private static String USER_ROLE_QUERY =
            "SELECT user.login, role.name " +
                    "FROM chambers.user JOIN (role, user_role) " +
                    "ON (user.id = user_role.user_id and role.id = user_role.role_id) " +
                    "WHERE user.login = ?;";

    private final DataSource dataSource;

    @Autowired
    public SpringSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    private void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
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
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/hi", "/chambers/add/**")
                .hasAuthority("admin")

                .antMatchers(HttpMethod.GET, "/chambers/todo")
                .hasAnyAuthority("admin", "user")

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .failureUrl("/loginfailed")
//              .successForwardUrl("/chambers")
                .and()
                .logout()
                .logoutSuccessUrl("/chambers");
    }
}
