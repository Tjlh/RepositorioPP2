package br.ifpe.pp2.models.usuarios;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;

@Entity	
public class Usuarios {
	
	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	private String nome;
	@Column(unique = true)
	private String email;
	private String senha; 
	private String telefone;
	 @Column(nullable = false)
	private Boolean admin = false;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate criacaoConta;
	
	public Long  getId() {
		return id;
	}
	public void setId(Long  id_usuario) {
		this.id = id_usuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public LocalDate getCriacaoConta() {
		return criacaoConta;
	}
	public void setCriacaoConta(LocalDate criacaoConta) {
		this.criacaoConta = criacaoConta;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

}
