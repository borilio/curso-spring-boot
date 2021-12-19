package com.ejemplo.crud.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ejemplo.crud.usuarios.Usuario;
import com.ejemplo.crud.usuarios.UsuarioService;

@Controller
public class LoginController {

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/login/usuario")
	public String loginUsuario(

		/*
		Recibes por post los parámetros email y pass.
		Habrá que usar un método del servicio para autenticar esos
		valores y tirar para un sitio u otro.
		
		*/
		
		return "";
	}
	
}
