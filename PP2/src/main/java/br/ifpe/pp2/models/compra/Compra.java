package br.ifpe.pp2.models.compra;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import br.ifpe.pp2.models.produtos.Produto;
import br.ifpe.pp2.models.usuarios.Usuarios;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;
	private StatusPedido status;
	@ManyToOne
	private Usuarios usuario;
	@DateTimeFormat(iso = ISO.DATE)
	private Date dataCompra = new Date();
	private String tipopagamento;
	private Double total=0.;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public StatusPedido getStatus() {
		return status;
	}
	public void setStatus(StatusPedido status) {
		this.status = status;
	}
	public Usuarios getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	public Date getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	public String getTipopagamento() {
		return tipopagamento;
	}
	public void setTipopagamento(String tipopagamento) {
		this.tipopagamento = tipopagamento;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

}
