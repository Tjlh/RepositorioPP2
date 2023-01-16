package br.ifpe.pp2.Controller;
 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.ifpe.pp2.models.produtos.Produto;
import br.ifpe.pp2.models.produtos.ProdutoDAO;
import br.ifpe.pp2.models.compra.CompraDAO;
import br.ifpe.pp2.models.produtos.CategoriasDAO;
import br.ifpe.pp2.models.usuarios.Usuarios;
import br.ifpe.pp2.models.usuarios.UsuariosDAO;
import jakarta.servlet.http.HttpSession;

@Controller
public class CardapioController {
	
	@Autowired
	private UsuariosDAO usuariosdao;
	@Autowired 
	private ProdutoDAO produtosdao;
	@Autowired
	private CategoriasDAO categoriadao;
	@Autowired
	private CompraDAO compradao;
	
	@GetMapping("/")
	public String produtos(Produto produto, Model model) {
		model.addAttribute("listarProdutos", this.produtosdao.findAll());
		model.addAttribute("mostrarTipos", this.categoriadao.findAll());		
		return "home";
	}
	
	//Pesquisar produtos		
	@PostMapping("/pesquisarProdutos")
	public String pesquisarProdutos(String pesquisa, Model model) {
		List<Produto> resultado = this.produtosdao.findByNomeContainingIgnoreCase(pesquisa);
		model.addAttribute("listarProdutos", resultado);
		return "home";
	}
	
	//Mostrar imagem
	@GetMapping("/foto/{idprod}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("idprod") Long idprod) {
		Produto produto = this.produtosdao.findById(idprod).orElse(null);
		return produto.getImagem();
	}
	
	@GetMapping("/meuspedidos")
	public String meusPedidos(Model model) {
		model.addAttribute("MostrarPedidos", compradao.findAll());
		return "meusPedidos";
	}
	
	@GetMapping("/minhaconta")
	public String conta() {
		return "minhaConta";
	}
	
	@PostMapping("/removerConta")
	public String removerConta(HttpSession session) {
		String id = session.getAttribute("id").toString();
		long codigo = Long.parseLong(id);
		usuariosdao.deleteById(codigo);
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login(Usuarios usuarios) {
		return "login";
	}
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
	

