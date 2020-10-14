package es.basic.jwtdemo.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static java.util.Collections.emptyList;

public class JwtUtil {
	
	// Método para crear el JWT y enviarlo al cliente en el header de la respuesta
	public static void addAuthentication(HttpServletResponse res, String username) {
		System.out.println("paso 3");
	String token = Jwts.builder()
			           .setSubject(username)
			           .setExpiration(new Date(System.currentTimeMillis() + 120000))
			        // Hash con el que firmaremos la clave
			            .signWith(SignatureAlgorithm.HS512, "firma1")
			            .compact();
	
	 //agregamos al encabezado el token
     res.addHeader("Authorization", "Bearer " + token);    
			           
	}
	
	// Método para validar el token enviado por el cliente
    public static Authentication getAuthentication(HttpServletRequest request) {
		System.out.println("paso 5");	
    // Obtenemos el token que viene en el encabezado de la peticion
    String token = request.getHeader("Authorization");
    // si hay un token presente, entonces lo validamos
    if (token != null) {
        String user = Jwts.parser()
                .setSigningKey("firma1")
                .parseClaimsJws(token.replace("Bearer", "")) //este metodo es el que valida
                .getBody()
                .getSubject();
        
     // Recordamos que para las demás peticiones que no sean /login
      // no requerimos una autenticacion por username/password 
      // por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin password
        if (user != null)
			return new UsernamePasswordAuthenticationToken(user, null, emptyList());
		else
			return null;    	
    }
    return null;
        
    }
}
