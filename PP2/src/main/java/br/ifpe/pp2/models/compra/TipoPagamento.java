package br.ifpe.pp2.models.compra;

public enum TipoPagamento {

	Dinheiro("Dinheiro"),
	Cartao("Cartão"),
	Pix("Pix");
	private String nome;
	
	TipoPagamento(String nome){
		this.nome = nome;
	}
	public String getNome() {
		return this.nome;
	}
	
	
}
