package br.ifpe.pp2.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ifpe.pp2.models.usuarios.Usuarios;
import br.ifpe.pp2.models.usuarios.UsuariosDAO;

@Service
public class UsuarioServico {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UsuariosDAO usuariosdao;
	
	
	public void salvar(Usuarios usuarios) {
		 // usuarios.setSenha(passwordEncoder.encode(usuarios.getSenha()));
		
		  usuariosdao.save(usuarios);
			System.out.println( usuariosdao.save(usuarios));
	}
	

}
