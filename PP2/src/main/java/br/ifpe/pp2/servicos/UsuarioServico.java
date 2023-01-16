package br.ifpe.pp2.servicos;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ifpe.pp2.models.usuarios.Usuarios;
import br.ifpe.pp2.models.usuarios.UsuariosDAO;

@Service
public class UsuarioServico {

	public static String md5(String senha) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, messageDigest.digest(senha.getBytes()));
			return hash.toString(16);
		} catch (Exception e) {
			return "";
		}
	}
	

}
