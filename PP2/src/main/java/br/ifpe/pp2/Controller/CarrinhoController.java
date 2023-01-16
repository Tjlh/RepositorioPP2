package br.ifpe.pp2.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpe.pp2.models.compra.Compra;
import br.ifpe.pp2.models.compra.CompraDAO;
import br.ifpe.pp2.models.compra.StatusPedido;
import br.ifpe.pp2.models.compra.TipoPagamento;
import br.ifpe.pp2.models.produtos.Produto;
import br.ifpe.pp2.models.produtos.ProdutoDAO;
import br.ifpe.pp2.models.produtos.ProdutoPedido;
import br.ifpe.pp2.models.produtos.ProdutoPedidoDAO;
import br.ifpe.pp2.models.usuarios.Usuarios;
import br.ifpe.pp2.models.usuarios.UsuariosDAO;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {

	@Autowired
	private ProdutoDAO produtosdao;
	@Autowired
	private UsuariosDAO usuariosdao;
	@Autowired
	private CompraDAO comprasdao;
	@Autowired
	private ProdutoPedidoDAO pedidosdao;

	private List<ProdutoPedido> listaPedido = new ArrayList<ProdutoPedido>();
	private Compra compra = new Compra();
	private Usuarios usuario;
	
	private void calcularTotal() {
		compra.setTotal(0.);
		for (ProdutoPedido i : listaPedido) {
			compra.setTotal(compra.getTotal() + i.getValorTotal());
		}
	}

	@GetMapping("/carrinho")
	public String carrinho(Model model) {
		if (listaPedido == null || listaPedido.size() == 0) {			
			return "carrinhoVazio";
		} 
		calcularTotal();
		model.addAttribute("compra", compra);
		model.addAttribute("listaItens", listaPedido);
		return "carrinho";
	
	}
	
	@GetMapping("/addCarrinho")
	public String carrinho(Long id, Model model, RedirectAttributes redirect) {
		Produto produto = produtosdao.findById(id).orElse(null);
		int controle = 0;
		/*
		 * Array para ler a lista de pedidos e comparar o id do produto com o id
		 * recebido por parâmetro da url. Caso o id seja igual será atribuido o valor 1
		 * à controle e não adicionará o produto novamento na lista apenas acrescentará
		 * na quantidade com o método /alterarQtd
		 */
		for (ProdutoPedido i : listaPedido) {
			if (i.getProduto().getId_produto().equals(produto.getId_produto())) {
				i.setQtd(i.getQtd() + 1);
				i.setValorTotal(0.);
				i.setValorTotal(i.getValorTotal() + (i.getQtd() * i.getValorUnd()));
				controle = 1;
				break;
			}
		}
		if (controle == 0) {
			ProdutoPedido item = new ProdutoPedido();
			item.setProduto(produto);
			item.setValorUnd(produto.getPreco());
			item.setQtd(item.getQtd() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQtd() * item.getValorUnd()));
			listaPedido.add(item);
		}
		System.out.println("ID: " + id);
		model.addAttribute("listaItens", listaPedido);
		return "redirect:/carrinho";
	}

	@GetMapping("/alterarQtd")
	public String alterarQtd(Long id, Model model, Integer action) {
		Produto produto = produtosdao.findById(id).orElse(null);
		for (ProdutoPedido i : listaPedido) {
			if (i.getProduto().getId_produto().equals(id)) {
				if (action.equals(1) && i.getQtd() >= 1) {
					i.setQtd(i.getQtd() + 1);
					i.setValorTotal(0.);
					i.setValorTotal(i.getValorTotal() + (i.getQtd() * i.getValorUnd()));
				} else if (action == 0 && i.getQtd() > 1) {
					i.setQtd(i.getQtd() - 1);
					i.setValorTotal(0.);
					i.setValorTotal(i.getValorTotal() + (i.getQtd() * i.getValorUnd()));
				}
				break;
			}
		}
		model.addAttribute("listaItens", listaPedido);
		return "redirect:/carrinho";
	}

	@GetMapping("/removerProd")
	public String removerProdd(Long id, Model model, Integer action) {
		Produto produto = produtosdao.findById(id).orElse(null);
		for (ProdutoPedido i : listaPedido) {
			if (i.getProduto().getId_produto().equals(id)) {
				listaPedido.remove(i);
				break;
			}
		}
		model.addAttribute("listaItens", listaPedido);
		return "redirect:/carrinho";
	}

	@GetMapping("/finalizar")
	public String finalizarCompra(Model model) {
		calcularTotal();
		model.addAttribute("compra", compra);
		model.addAttribute("listaItens", listaPedido);
		model.addAttribute("usuarios", usuario);
		return "finalizar";
	}
	@GetMapping("/modificarPedidos")
	public String modificarPedidos(Model model,Compra compra) {		
		model.addAttribute("MostrarPedidos", comprasdao.findAll());
		return "listarPedidos";
	}

	@GetMapping("/editarPedido")
	public String editarPedido(Compra compra,Model model, Long codigo) {
		model.addAttribute("pedido", comprasdao.findById(codigo).orElse(null));
		return "modificarPedido";
	}
	
	@PostMapping("/alterandoPedido")
	public String alterarPedido(Long codigo,StatusPedido status) {
		Compra compra = comprasdao.findById(codigo).orElse(null);
		System.out.println(status);
		compra.setStatus(status);		
		System.out.println(compra.getStatus());
		comprasdao.save(compra);
		return "redirect:/modificarPedidos";
	}
	@PostMapping("/confirmarCompra")
	public String confirmarCompra(HttpSession session,String idd,Compra compra, String usuario,String tipopagamento, String produtoo,String valor) {
		
		System.out.println(idd);
		if(idd!=null && idd!="") {
			Long id = Long.parseLong(idd);
			Usuarios usuarios = usuariosdao.findById(id).orElse(null);
			compra.setUsuario(usuarios);
		}else {
			compra.setUsuario(null);
		}
		for (ProdutoPedido i : listaPedido) {
			compra.setProdutos(listaPedido);
			}
		compra.setStatus(br.ifpe.pp2.models.compra.StatusPedido.Andamento);
		comprasdao.save(compra);
		listaPedido.clear();
		if(session.getAttribute("usuarioLogado") != null) {
			return "redirect:/meuspedidos";
		}else {
			return "redirect:/";			
		}
	}

}
