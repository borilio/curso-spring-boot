package com.ejemplo.practica5.users;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	/**
	 * Valida un usuario bajo un estricto y complejo procedimiento :)
	 * @param usuario
	 * @return true si el usuario es válido, o false en caso contrario.
	 */
	public boolean validar(User usuario) {
		return usuario.getPassword().equals("12345");
	}

}
