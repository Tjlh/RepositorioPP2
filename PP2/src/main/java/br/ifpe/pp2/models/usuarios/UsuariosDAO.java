package br.ifpe.pp2.models.usuarios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuariosDAO extends JpaRepository <Usuarios, Long>{
	
	 public Usuarios findByEmail(String email);

	 public Usuarios findByEmailAndSenha(String email, String senha);

}
