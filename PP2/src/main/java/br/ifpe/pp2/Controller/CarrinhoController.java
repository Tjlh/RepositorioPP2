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
import br.ifpe.pp2.models.compra.TipoPagamento;
import br.ifpe.pp2.models.produtos.Produto;
import br.ifpe.pp2.models.produtos.ProdutoDAO;
import br.ifpe.pp2.models.produtos.ProdutoPedido;
import br.ifpe.pp2.models.produtos.ProdutoPedidoDAO;
import br.ifpe.pp2.models.usuarios.Usuarios;
import br.ifpe.pp2.models.usuarios.UsuariosDAO;
import jakarta.servlet.http.HttpServletRequest;
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

	/*
	 * private void calcularTotal() { compra.setTotal(0.); for (ProdutoPedido i :
	 * listaPedido) { compra.setTotal(compra.getTotal() + i.getValorTotal()); } }
	 */
	@GetMapping("/carrinho")
	public String carrinho(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<ProdutoPedido> listaPedido = (List<ProdutoPedido>) session.getAttribute("Meu_Carrinho");
		if (listaPedido == null) {
			listaPedido = new ArrayList<>();
			return "carrinho-vazio";
		} else {
			if(listaPedido.size() == 0) {
				return "carrinho-vazio";
			}
			model.addAttribute("sessionCarrinho", listaPedido);
			model.addAttribute("compra", compra);
			model.addAttribute("listaItens", listaPedido);
			return "carrinho";
		}
	}
	@GetMapping("/carrinho-vazio")
	public String carrinhoVazio() {
		return "carrinho-vazio";
	}
	
	@GetMapping("/addCarrinho")
	public String carrinho(Long id, Model model, RedirectAttributes redirect, HttpServletRequest request) {
		// calcularTotal();
		Produto produto = produtosdao.findById(id).orElse(null);
		@SuppressWarnings("unchecked")
		List<ProdutoPedido> listaPedido = (List<ProdutoPedido>) request.getSession().getAttribute("Meu_Carrinho");
		if (listaPedido == null) {
			listaPedido = new ArrayList<>();
			request.getSession().setAttribute("Meu_Carrinho", listaPedido);
		}
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
			compra.setTotal(0.);
			for (ProdutoPedido i : listaPedido) {
				compra.setTotal(compra.getTotal() + i.getValorTotal());
			}
		}
		System.out.println("ID: " + id);
		System.out.println(compra.getTotal());
		model.addAttribute("listaItens", listaPedido);
		return "redirect:/carrinho";
	}

	@GetMapping("/alterarQtd")
	public String alterarQtd(Long id, Model model, Integer action, HttpServletRequest request) {
		Produto produto = produtosdao.findById(id).orElse(null);
		@SuppressWarnings("unchecked")
		List<ProdutoPedido> listaPedido = (List<ProdutoPedido>) request.getSession().getAttribute("Meu_Carrinho");
		if (listaPedido == null) {
			listaPedido = new ArrayList<>();
			request.getSession().setAttribute("Meu_Carrinho", listaPedido);
		}
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
		compra.setTotal(0.);
		for (ProdutoPedido i : listaPedido) {
			compra.setTotal(compra.getTotal() + i.getValorTotal());
		}
		model.addAttribute("listaItens", listaPedido);
		return "redirect:/carrinho";
	}

	@GetMapping("/removerProd")
	public String removerProdd(Long id, Model model, Integer action,HttpServletRequest request) {
		Produto produto = produtosdao.findById(id).orElse(null);
		@SuppressWarnings("unchecked")
		List<ProdutoPedido> listaPedido = (List<ProdutoPedido>) request.getSession().getAttribute("Meu_Carrinho");
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
	public String finalizarCompra(Model model, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<ProdutoPedido> listaPedido = (List<ProdutoPedido>) request.getSession().getAttribute("Meu_Carrinho");
		model.addAttribute("compra", compra);
		model.addAttribute("listaItens", listaPedido);
		model.addAttribute("usuarios", usuario);
		return "finalizar";
	}

	@PostMapping("/confirmarCompra")
	public String confirmarCompra(HttpSession session, Compra compra, String usuario, String tipopagamento,
			String produtoo, String valor) {
		System.out.println(valor);
		System.out.println(usuario);
		if (usuario.length() >= 1) {
			Long id = Long.parseLong(usuario);
			Usuarios usuarios = usuariosdao.findById(id).orElse(null);
			compra.setUsuario(usuarios);
		} else {
			compra.setUsuario(null);
		}
		for (ProdutoPedido i : listaPedido) {
			compra.setProdutos(listaPedido);
		}

		compra.setStatus(br.ifpe.pp2.models.compra.StatusPedido.Andamento);
		comprasdao.save(compra);
		return "redirect:/";
	}

	@PostMapping("/Comprar")
	public String Comprar(String pagamento, Model model,HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<ProdutoPedido> listaPedido = (List<ProdutoPedido>) request.getSession().getAttribute("Meu_Carrinho");
		model.addAttribute("compra", compra);
		model.addAttribute("listaItens", listaPedido);
		model.addAttribute("usuarios", usuario);
		compra.setUsuario(usuario);
		compra.setStatus(compra.getStatus().Andamento);
		compra.setFormaPagamento(pagamento);
		comprasdao.save(compra);
		for (ProdutoPedido c : listaPedido) {
			c.setCompra(compra);
			pedidosdao.save(c);
		}
		return "meusPedidos";
	}
}
