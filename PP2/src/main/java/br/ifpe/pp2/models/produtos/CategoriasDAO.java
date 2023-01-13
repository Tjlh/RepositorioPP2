package br.ifpe.pp2.models.produtos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriasDAO extends JpaRepository<Categorias, Integer>{
	
	public Categorias findByNome(String nome);
}
