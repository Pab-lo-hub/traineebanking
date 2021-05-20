package com.backend;

import com.backend.classes.Account;
import com.backend.classes.Transferencia;
import com.backend.classes.User;
import com.backend.interfaces.AccountRepository;
import com.backend.interfaces.TransferenciaRepository;
import com.backend.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SpringBootApplication
public class Application {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner initData(AccountRepository accountRepository, UserRepository userRepository, TransferenciaRepository transferenciaRepository) {
        return (args) -> {
            User user1 = new User(1L,"15151515","1" , passwordEncoder.encode("1515"));
            userRepository.save(user1);

            User user2 = new User(2L, "1000100", "2",passwordEncoder.encode("100"));
            userRepository.save(user2);

            User user3 = new User(3L,"9999999","3", passwordEncoder.encode("9999999"));
            userRepository.save(user3);

            Account account1 = new Account("Caja de Ahorro", 548, user1);
            accountRepository.save(account1);

            Account account2 = new Account("Caja de Ahorro en dolares", 5548, user2);
            accountRepository.save(account2);

            Account account3 = new Account("Cuenta Corriente", 48, user3);
            accountRepository.save(account3);

            Transferencia transferencia1 = new Transferencia();
            transferenciaRepository.save(transferencia1);

            Transferencia transferencia2 = new Transferencia("01/03/2018", "Pancho", 789568, "Fran", 4568, "78978", 2, 8484864, "Inmediata", "alquiler", 564841);
            transferenciaRepository.save(transferencia2);
        };
    }

    public static Account addAccount(Account newAccount){
        return newAccount;
    }

    //Login and security (Finalmente no fueron utilizados)//
    @EnableWebSecurity
    @Configuration
    class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
        @Autowired
        UserRepository userRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(inputName -> {
                User user = userRepository.findByDni(inputName);
                if (user != null) {
                    return new org.springframework.security.core.userdetails.User(user.getDni(), user.getPassword(),
                            AuthorityUtils.createAuthorityList());
                } else {
                    throw new UsernameNotFoundException("Unknown user: " + inputName);
                }
            });
        }
    }

    @Configuration
    @EnableWebSecurity
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/api").hasAuthority("USER")
                    .and()
                    .formLogin()
                    .usernameParameter("dni")
                    .passwordParameter("password")
                    .loginPage("/api/login");
            http.headers().frameOptions().disable();
            http.logout().logoutUrl("/api/logout");
            // turn off checking for CSRF tokens
            http.csrf().disable();
            // if user is not authenticated, just send an authentication failure response
            http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
            // if login is successful, just clear the flags asking for authentication
            http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
            // if login fails, just send an authentication failure response
            http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
            // if logout is successful, just send a success response
            http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        }

        private void clearAuthenticationAttributes(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }
        }
    }
}