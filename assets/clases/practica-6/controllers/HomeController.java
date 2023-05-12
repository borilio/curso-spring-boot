package com.ejemplo.practica6.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ejemplo.practica6.users.User;
import com.ejemplo.practica6.users.UserService;

@Controller
public class HomeController {

	private final UserService userService;

	public HomeController(UserService userService){
		this.userService = userService;
	}
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/crear/usuario/{email}")
	public String crearUsuario(Model model, @PathVariable String email) {
		
		//Usamos el servicio para crear un usuario y guardarlo en nuestra "BD"
		User creado = userService.createUser(email);
		userService.add(creado);

		model.addAttribute("mensaje", "Usuario creado satisfactoriamente");
		model.addAttribute("userCreado", creado);
		
		return "home";
	}
	
	@GetMapping("/crear/usuarios/{numero}")
	public String crearUsuarios(Model model, @PathVariable Integer numero) {
		//Vaciamos la lista
		userService.deleteAll();
		
		//Usamos el servicio para crear varios usuarios y guardarlos en nuestra "BD"
		for (int i=1; i<=numero; i++) {
			userService.add(userService.createUser("prueba" + i + "@correo.com"));
		}

		model.addAttribute("mensaje", "Se han creado " + numero +  " nuevos usuarios automáticamente");
		
		return "home";
	}
	
}
