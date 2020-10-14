package es.basic.jwtdemo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
   @Override
	protected void configure(HttpSecurity http) throws Exception {
	System.out.println("2. Permite la autenticacion a traves del /login (se carga tambien al levantar el servidor)");
	http.csrf().disable().authorizeRequests()
	    .antMatchers("/login").permitAll() //permitimos el acceso a /login a cualquiera
		.anyRequest().authenticated() //cualquier otra peticion requiere autenticacion
		.and()
		 // Las peticiones /login pasaran previamente por este filtro
		.addFilterBefore(new LoginFilter("/login", authenticationManager()),UsernamePasswordAuthenticationFilter.class)
        //Las demás peticiones pasarán por este filtro para validar el token
        .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
        
	}
	
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("1. Se carga en memoria al levantar el servidor");
    // Creamos una cuenta de usuario por default
    	
        auth.inMemoryAuthentication()
                .withUser("ask")
                .password("{noop}123")
                .roles("ADMIN");
    }
	
    

}
