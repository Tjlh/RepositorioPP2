package br.ifpe.pp2.models.produtos;

import jakarta.persistence.Id;
import java.util.Arrays;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
 
@Entity
public class Produto {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id_produto;
	private String nome;
	private String descricao;
	private Integer estoque;
	private Double preco;
	@Lob
	private byte[] imagem;
	@ManyToOne
	private Categorias categorias;

	public Long getId_produto() {
		return id_produto;
	}
	public void setId_produto(Long id_produto) {
		this.id_produto = id_produto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public byte[] getImagem() {
		return imagem;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	public Categorias getCategorias() {
		return categorias;
	}
	public void setCategorias(Categorias categorias) {
		this.categorias = categorias;
	}
	@Override
	public String toString() {
		return "Produto [id_produto=" + id_produto + ", nome=" + nome + ", descricao=" + descricao + ", estoque="
				+ estoque + ", preco=" + preco + ", imagem=" + Arrays.toString(imagem) + ", categorias=" + categorias
				+ "]";
	}
	
	

}
