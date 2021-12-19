package com.ejemplo.crud.usuarios;

import java.util.List;

public interface UsuarioService {
	//Con éstos métodos, tendríamos lo necesario para hacer un CRUD
	public Usuario autentificar(String email, String pass);
	public List<Usuario> obtenerTodos();
	public Usuario borrarById(int id);
	public Usuario obtenerById(int id);
	public Usuario guardar(Usuario usuario);
}
