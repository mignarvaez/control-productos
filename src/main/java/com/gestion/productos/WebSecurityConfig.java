package com.gestion.productos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Clase que se encarga de implementar la configuración de seguridad web
 * 
 * @author Migue
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Se injecta el encoder encargado de encritar el password
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Método que almacena en memoria los usuarios, es un bean para que pueda usarse
	 * en el IOC container de spring
	 */
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {

		// Se crea un usuario no administrador con la contraseña 12345 encriptada
		UserDetails usuario1 = User.withUsername("miguel")
				.password("$2a$10$7VRlw1rddA9TyZ7L/RR4kO65UZOFkPVXvV3oEbWdbqPd5682BVE9q").roles("USER").build();

		// Usuario administrativo
		UserDetails usuario2 = User.withUsername("admin")
				.password("$2a$10$7VRlw1rddA9TyZ7L/RR4kO65UZOFkPVXvV3oEbWdbqPd5682BVE9q").roles("ADMIN").build();

		return new InMemoryUserDetailsManager(usuario1, usuario2);

	}

	/**
	 * Método que configura el acceso a las rutas
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Se especifica que el acceso a la raiz sea para todos los usuarios, pero el
		// acceso al formulario y eliminar solo está habilitado para administradores
		// Se indica que deben aunticarse todos los usuarios en la ruta /login y también
		// pueden desloguearse todos los usuarios
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/form/*", "/eliminar/*").hasRole("ADMIN")
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
				.permitAll();
	}
}
