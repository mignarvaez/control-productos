package com.gestion.productos;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase que genera una contraseña encriptada
 * @author Migue
 *
 */
public class PasswordGenerator {
	
	/**
	 * Método principal que encripta un password
	 * @param args
	 */
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "12345";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println(encodedPassword);
	}
 
}
