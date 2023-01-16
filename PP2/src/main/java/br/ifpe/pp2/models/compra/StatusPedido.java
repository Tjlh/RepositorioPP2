package br.ifpe.pp2.models.compra;

public enum StatusPedido {
	Cancelado("Cancelado"),
	Andamento("Em andamento"),
	Concluido("Concluído");

	private String nome;
	
	StatusPedido(String nome){
		this.nome = nome;
	}
	public String getNome() {
		return this.nome;
	}
}
