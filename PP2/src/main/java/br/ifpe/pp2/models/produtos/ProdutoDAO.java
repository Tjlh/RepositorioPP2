package br.ifpe.pp2.models.produtos;

import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoDAO extends JpaRepository<Produto, Long>{

	List<Produto> findByNomeContainingIgnoreCase(String nome);

}
