package es.basic.jwtdemo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.basic.jwtdemo.modelo.Usuario;

@RestController
public class UsuariosController {
	
	@GetMapping(path="/users")
	public List<Usuario> getUsers(){
		System.out.println("paso 6");
		return Arrays.asList(new Usuario(1,"Paco"),new Usuario(2,"Pedro"),new Usuario(3,"Juan"));
		
	}

}
