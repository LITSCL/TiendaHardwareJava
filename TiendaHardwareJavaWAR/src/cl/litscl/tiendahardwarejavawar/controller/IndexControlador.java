package cl.litscl.tiendahardwarejavawar.controller;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.litscl.tiendahardwareejb.bean.ProductoDAOLocal;
import cl.litscl.tiendahardwareejb.model.Producto;
import cl.litscl.tiendahardwareejb.model.Usuario;
import cl.litscl.tiendahardwarejavawar.pagination.ProductoPaginador;

/**
 * Servlet implementation class IndexControlador
 */
@WebServlet("/Index")
public class IndexControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private ProductoDAOLocal daoProducto;
	
	private ProductoPaginador productoPaginador;
	private String registros;
	private String numeros;
	
	private String jpql;
	   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexControlador() {
	    super();
	    // TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		
		List<Producto> productos = daoProducto.getAll();
		
		boolean raiz = false;
		int paginaActual;
		try {
			paginaActual = Integer.parseInt(request.getParameter("pagina"));
		} catch (Exception ex) {
			paginaActual = 1;
			raiz = true;
		}		

		productoPaginador = new ProductoPaginador(paginaActual, 15, productos);
		
		registros = productoPaginador.generarRegistros("tarjeta", request.getContextPath(), "Index", "index", (Usuario)sesion.getAttribute("usuario"));
		numeros = productoPaginador.generarNumeros(request.getContextPath(), "Index", "index", null, null);
		
		sesion.setAttribute("registros", registros);
		sesion.setAttribute("numeros", numeros);
		sesion.setAttribute("renderizarVista", "index");
		
		if (raiz == true) {
			response.sendRedirect(request.getContextPath());
		}
		else {
			response.sendRedirect(request.getContextPath() + "?pagina=" + paginaActual);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}