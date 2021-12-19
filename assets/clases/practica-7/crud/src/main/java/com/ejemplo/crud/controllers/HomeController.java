package com.ejemplo.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ejemplo.crud.usuarios.Usuario;
import com.ejemplo.crud.usuarios.UsuarioService;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "login";
	}
	
}
