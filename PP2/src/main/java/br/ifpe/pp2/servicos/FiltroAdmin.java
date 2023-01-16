package br.ifpe.pp2.servicos;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class FiltroAdmin implements Filter{
	private String[] pathsLiberadosTodos = { "/","/finalizar","/confirmarCompra","/removerProd","/addCarrinho","/alterarQtd","/carrinho","/login","/login/usuario","/admin","/entrar/admin","/pesquisarProdutos","/cadastro", "/salvar/novousuario","/foto(.*)","/h2-console(.*)" ,"/assets(.*)","/css(.*)", "/js(.*)","/projeto"};
	private String[] pathsLiberadosLogadoComum  = { "/","/confirmarCompra","/finalizar","/comprando","/removerProd","/finalizar","/confirmarCompra","/addCarrinho","/alterarQtd","/removerConta","/minhaconta","/alterardados","/carrinho","/login","/pesquisarProdutos","/login/usuario","/meuspedidos","/logout","/admin","/entrar/admin", "/cadastro", "/foto(.*)","/h2-console(.*)" ,"/css(.*)", "/js(.*)","/assets(.*)","/projeto"};
	private String[] pathsLiberadosLogadoAdmin = { "/","/alterandoPedido","/editarPedido","/modificarPedidos","/confirmarCompra","/finalizar","/comprando","/removerProd","/removerConta","/confirmarCompra","/finalizar","/addCarrinho","/alterarQtd","/modificarPedidos","/modificarcategoria","/modificarProduto","/minhaconta","/pesquisarProdutos","/alterardados","/removerConta","/carrinho","/login","/login/usuario","/meuspedidos","/logout","/admin","/admin/editarPedido(.*)","/entrar/admin", "/cadastro","/assets(.*)", "/salvar/novousuario","/criarnovotipo","/removerProduto","/gerenciamento","/criarnovoproduto", "/foto(.*)","/h2-console(.*)" ,"/css(.*)", "/js(.*)","/projeto"};

	@Override
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession sessao = req.getSession();
		String path = req.getRequestURI();
		for (String livre : pathsLiberadosTodos) {
			if (path.matches(livre)) {
				chain.doFilter(request, response);
				return;
			}
		}		
		
		if (sessao != null && sessao.getAttribute("tipo") == "admin") {
			chain.doFilter(request, response);

		}else if (sessao != null && sessao.getAttribute("usuarioLogado") != null && sessao.getAttribute("tipo").toString() == "false"){		
			for (String livre : pathsLiberadosLogadoComum) {
				if (path.matches(livre)) {
					chain.doFilter(request, response);
					return;
				}
			}

		} else if(sessao != null && sessao.getAttribute("usuarioLogado") != null && sessao.getAttribute("tipo").toString() == "true") {
			for (String livre : pathsLiberadosLogadoAdmin) {
				if (path.matches(livre)) {
					chain.doFilter(request, response);
					return;
				}
			}
		}
		else {
			res.sendRedirect("/");
		}
	}
	
	
	
	

}
