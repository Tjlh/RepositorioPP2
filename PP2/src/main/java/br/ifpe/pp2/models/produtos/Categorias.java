package br.ifpe.pp2.models.produtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Categorias {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id_categoria;
	@Column(unique = true)
	private String nome;
	
	public Integer getId_categoria() {
		return id_categoria;
	}
	public void setId_categoria(Integer id_categoria) {
		this.id_categoria = id_categoria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
