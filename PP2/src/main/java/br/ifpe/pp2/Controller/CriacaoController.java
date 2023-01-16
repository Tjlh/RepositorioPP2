package br.ifpe.pp2.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpe.pp2.models.compra.Compra;
import br.ifpe.pp2.models.compra.CompraDAO;
import br.ifpe.pp2.models.produtos.Categorias;
import br.ifpe.pp2.models.produtos.CategoriasDAO;
import br.ifpe.pp2.models.produtos.Produto;
import br.ifpe.pp2.models.produtos.ProdutoDAO;
import br.ifpe.pp2.models.usuarios.Usuarios;
import br.ifpe.pp2.models.usuarios.UsuariosDAO;
import br.ifpe.pp2.servicos.UsuarioServico;
import jakarta.servlet.http.HttpSession;

@Controller
public class CriacaoController {
	@Autowired
	private UsuariosDAO usuariosdao;
	@Autowired
	private ProdutoDAO produtosdao;
	@Autowired
	private CategoriasDAO categoriadao;
	@Autowired
	private CompraDAO compradao;

	@GetMapping("/cadastro")
	public String cadastro(Usuarios usuario) {
		return "cadastro";
	}

	@PostMapping("/salvar/novousuario")
	public String salvarUsuario(String senha,String email, Usuarios usuarios, RedirectAttributes redirect) {

		if (email != null && usuariosdao.findByEmail(email) == null) {
			usuarios.setSenha(UsuarioServico.md5(senha));
			usuariosdao.save(usuarios);
			redirect.addFlashAttribute("sucess", "Conta criada com sucesso.");
			return "redirect:/login";

		} else {
			redirect.addFlashAttribute("falha", "Falha ao criar conta, email existente.");
			return "redirect:/cadastro";

		}
	}
	
	@PostMapping("/login/usuario")
	public String loginUsuario(String email,String senha, RedirectAttributes ra, HttpSession session) {
		Usuarios usuario = this.usuariosdao.findByEmailAndSenha(email, UsuarioServico.md5(senha));
		if (usuario != null) {
			if(session.getAttribute("tipo") == "admin") {
				session.invalidate();
			}
			session.setAttribute("usuarioLogado", usuario);
			session.setAttribute("id", usuario.getId());
			session.setAttribute("tipo", usuario.getAdmin());
			System.out.println(usuario.getId());			
			return "redirect:/";
		} else {
			ra.addFlashAttribute("mensagemErro", "Usuário/senha inválidos");
			return "redirect:/login";
		} 

	}

	@PostMapping("/alterardados")
	public String alterarDados(Usuarios usuarios, String senha, String nome, Boolean tipo, String email, RedirectAttributes redirect,
			String numero, HttpSession session) {
		String id = session.getAttribute("id").toString();
		long codigo = Long.parseLong(id);
		System.out.println(tipo);

		Usuarios encontrado = usuariosdao.findById(codigo).orElse(null);
		if (nome != null) {
			encontrado.setNome(nome);
			redirect.addFlashAttribute("sucess", "Nome alterado com sucesso.");
		}
		if (email != null && usuariosdao.findByEmail(email) == null) {
			encontrado.setEmail(email);
			redirect.addFlashAttribute("sucess", "Email alterado com sucesso.");
		}else {
			redirect.addFlashAttribute("falha", "Email existente ou nulo.");
		}
		if (numero != null) {
			encontrado.setTelefone(numero);
			redirect.addFlashAttribute("sucess", "Número alterado com sucesso.");
		}
		if (senha != null) {
			encontrado.setSenha(UsuarioServico.md5(senha));
			redirect.addFlashAttribute("sucess", "Senha alterada com sucesso.");
		}
		usuariosdao.save(encontrado);
		session.setAttribute("usuarioLogado", encontrado);
		session.setAttribute("tipo", encontrado.getAdmin());
		session.setAttribute("id", encontrado.getId());
		return "redirect:/minhaconta";
	}

	@GetMapping("/gerenciamento")
	public String gerenciamento(Categorias categorias, Produto produtos, Model model) {
		model.addAttribute("tipoAlimento", this.categoriadao.findAll());
		model.addAttribute("todosProdutos", produtosdao.findAll());
		return "gerenciamento";
	}

	@PostMapping("/criarnovotipo")
	public String criarNovoTipo(Categorias tipo, RedirectAttributes redirect) {
		if (tipo.getNome() != null && categoriadao.findByNome(tipo.getNome()) == null) {
			categoriadao.save(tipo);
			return "redirect:/gerenciamento";
		} else {
			redirect.addFlashAttribute("mensagem", "Categoria já existe ou nula");
			return "redirect:/gerenciamento";
		}
	}

	@PostMapping("/modificarcategoria")
	public String modificarCategoria(String nome, Integer antigonome, RedirectAttributes redirect) {
		Categorias alterarNome = categoriadao.findById(antigonome).orElse(null);
		if (nome != null) {
			alterarNome.setNome(nome);
			categoriadao.save(alterarNome);
			return "redirect:/gerenciamento";
		} else {
			redirect.addFlashAttribute("mensagem", "Categoria já existe ou nula");
			return "redirect:/gerenciamento";
		}
	}

	@PostMapping("/criarnovoproduto")
	public String criarNovoProduto(Produto produtos, @RequestParam MultipartFile file) {
		try {
			produtos.setImagem(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.produtosdao.save(produtos);
		System.out.println(produtos);
		return "redirect:/gerenciamento";
	}

	@PostMapping("/modificarProduto")
	public String modificarProduto(Produto produtos, @RequestParam MultipartFile file) {
		try {
			produtos.setImagem(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		produtosdao.save(produtos);
		return "redirect:/gerenciamento";
	}

	@GetMapping("/modificarProduto")
	public String modificarProduto(Long id, Model model) {
		model.addAttribute("tipoAlimento", this.categoriadao.findAll());
		Produto produto = this.produtosdao.findById(id).orElse(null);
		model.addAttribute("produtos", produto);
		produtosdao.save(produto);
		return "modificarProduto";
	}

	@GetMapping("/removerProduto")
	public String removerProduto(Long id) {
		produtosdao.deleteById(id);
		return "redirect:/gerenciamento";
	}
	
	@GetMapping("/projeto")
	public String projeto() {
		return "admin/projeto";
	}

}
