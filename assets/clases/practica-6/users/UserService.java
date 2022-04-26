package com.inserta.ejercicio117.users;

import java.util.List;

public interface UserService {
	/**
	 * Devuelve una lista con todos los usuarios
	 */
	public List<User> getAll();

	/**
	 * Devuelve el usuario que tenga la id recibida o null si no existe
	 * @param id
	 * @return El usuario con esa id o null si no existe
	 */
	public User getById(int id);
	/**
	 * Devuelve el usuario que tenga el mismo email recibido, o null si no existe
	 * @param email
	 * @return El usuario con ese email o null si no existe
	 */
	public User getByEmail(String email);

	/**
	 * Devuelve una lista con los usuarios que su email empiece por email recibido, o una lista vacía si no hay
	 * @param email
	 * @return List<User> con todos los usuarios que el email empiece por el email recibido, o una lista vacía si no hay
	 */
	public List<User> getByEmails(String email);

	/**
	 * Crea y devuelve un usuario con el email indicado. La id y el pass son generados al azar
	 * @param email
	 * @return El usuario creado
	 */
	public User createUser(String email);

	/**
	 * Añade el usuario recibido a la lista, devolviendo true si fue bien o false en caso contrario
	 * @param user
	 * @return Un true si se añade bien a la lista o false en caso contrario
	 */
	public boolean add(User user);

	/**
	 * Limpia todo el contenido de la lista de usuarios, dejándola vacía
	 */
	public void deleteAll();
}
