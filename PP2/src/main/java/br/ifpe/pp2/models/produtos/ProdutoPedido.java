package br.ifpe.pp2.models.produtos;

import br.ifpe.pp2.models.compra.Compra;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ProdutoPedido {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pedido;
	@ManyToOne
	private Produto produto;
	private Integer qtd=0;
	private Double valorUnd=0.;
	private Double valorTotal=0.;
	
	public Long getId() {
		return id_pedido;
	}
	public void setId(Long id_pedido) {
		this.id_pedido = id_pedido;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Integer getQtd() {
		return qtd;
	}
	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	public Double getValorUnd() {
		return valorUnd;
	}
	public void setValorUnd(Double valorUnd) {
		this.valorUnd = valorUnd;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
