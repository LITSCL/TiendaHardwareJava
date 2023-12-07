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

import cl.litscl.tiendahardwareejb.bean.CategoriaDAOLocal;
import cl.litscl.tiendahardwareejb.bean.ProductoDAOLocal;
import cl.litscl.tiendahardwareejb.model.Categoria;
import cl.litscl.tiendahardwareejb.model.Producto;
import cl.litscl.tiendahardwareejb.model.Usuario;
import cl.litscl.tiendahardwarejavawar.pagination.CategoriaPaginador;
import cl.litscl.tiendahardwarejavawar.pagination.ProductoPaginador;

/**
 * Servlet implementation class CategoriaControlador
 */
@WebServlet("/Categoria")
public class CategoriaControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private CategoriaDAOLocal daoCategoria;
	@Inject
	private ProductoDAOLocal daoProducto;
	
	private Categoria c = new Categoria();
	
	private int id;
	private String nombre;
	
	private CategoriaPaginador categoriaPaginador;
	private ProductoPaginador productoPaginador;
	private String registros;
	private String numeros;
	
	private String jpql;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoriaControlador() {
	    super();
	    // TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		
		String vista = request.getParameter("vista");
		String opcion = request.getParameter("opcion");
		String asincrono = request.getParameter("asincrono");
		
		if (vista == null) {
			vista = "";
		}
		if (opcion == null) {
			opcion = "";
		}
		if (asincrono == null) {
			asincrono = "";
		}
		
		switch (vista) { //Renderización de vistas.
		case "crear": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				sesion.setAttribute("renderizarVista", "crear");
				response.sendRedirect(request.getContextPath() + "/categoria/crear");
			}
			break;
		}
		case "listar": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				List<Categoria> categorias = daoCategoria.getAll();
				
				boolean raiz = false;
				int paginaActual;
				try {
					paginaActual = Integer.parseInt(request.getParameter("pagina"));
				} catch (Exception ex) {
					paginaActual = 1;
					raiz = true;
				}		

				categoriaPaginador = new CategoriaPaginador(paginaActual, 15, categorias);
				
				registros = categoriaPaginador.generarRegistros("tabla", request.getContextPath(), "Categoria", "listar");
				numeros = categoriaPaginador.generarNumeros(request.getContextPath(), "Categoria", "listar");
				
				sesion.setAttribute("registros", registros);
				sesion.setAttribute("numeros", numeros);
				sesion.setAttribute("renderizarVista", "listar");
				
				if (raiz == true) {
					response.sendRedirect(request.getContextPath() + "/categoria/listar");
				}
				else {
					response.sendRedirect(request.getContextPath() + "/categoria/listar" + "?pagina=" + paginaActual);
				}	
			}
			break;
		}
		case "modificar": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				try {
					this.id = Integer.parseInt(request.getParameter("id"));
				} catch (Exception ex) {
					this.id = -1;
				}
					
				if (this.id != -1 && daoCategoria.find(this.id) != null) {
					this.c = daoCategoria.find(this.id);
					
					sesion.setAttribute("categoria", this.c);
					sesion.setAttribute("renderizarVista", "modificar");
					response.sendRedirect(request.getContextPath() + "/categoria/modificar" + "?id=" + this.c.getId());
				}
				else {
					response.sendRedirect(request.getContextPath());
				}
			}
			break;
		}
		case "mostrar": {
			try {
				this.id = Integer.parseInt(request.getParameter("id"));
			} catch (Exception ex) {
				this.id = -1;
			}
			
			boolean raiz = false;
			int paginaActual;
			try {
				paginaActual = Integer.parseInt(request.getParameter("pagina"));
			} catch (Exception ex) {
				paginaActual = 1;
				raiz = true;
			}
					
			List<Producto> productos = daoProducto.findAll("categoriaFK", Integer.toString(this.id));
			
			productoPaginador = new ProductoPaginador(paginaActual, 15, productos);
			
			registros = productoPaginador.generarRegistros("tarjeta", request.getContextPath(), "Categoria", "mostrar", (Usuario)sesion.getAttribute("usuario"));
			numeros = productoPaginador.generarNumeros(request.getContextPath(), "Categoria", "mostrar", "id", Integer.toString(this.id));
			
			sesion.setAttribute("registros", registros);
			sesion.setAttribute("numeros", numeros);
			sesion.setAttribute("renderizarVista", "mostrar");
			
			if (raiz == true) {
				response.sendRedirect(request.getContextPath() + "/categoria/mostrar" + "?id=" + this.id);
			}
			else {
				response.sendRedirect(request.getContextPath() + "/categoria/mostrar" + "?id=" + this.id + "&pagina=" + paginaActual);
			}	
			break;
		}
		default:
			break;
		}
		
		switch (opcion) { //Acceso a datos GET.
		case "1": { //Eliminar.
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				try {
					this.id = Integer.parseInt(request.getParameter("id"));
				} catch (Exception ex) {
					this.id = -1;
				}
				
				if (this.id != -1) {
					if (daoCategoria.delete(this.id) == true) {
						sesion.setAttribute("eliminarCategoria", "Exitoso");
					}
					else {
						sesion.setAttribute("eliminarCategoria", "Fallido");
					}
					response.sendRedirect(request.getContextPath() + "/categoria/listar");
				}
				else {
					response.sendRedirect(request.getContextPath());
				}
			}
			break;
		}
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		
		String opcion = request.getParameter("opcion");
		
		switch (opcion) { //Acceso a datos POST.
		case "1": { //Crear.	
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				this.nombre = request.getParameter("nombre");		
				if (this.nombre != null) {
					this.c.setNombre(nombre);
					
					if (daoCategoria.save(this.c)) {
						sesion.setAttribute("crearCategoria", "Exitoso");
					}
					else {
						sesion.setAttribute("crearCategoria", "Fallido");
					}			
					response.sendRedirect(request.getContextPath() + "/categoria/crear");		
				}
				else {
					response.sendRedirect(request.getContextPath());
				}
			}
			break;
		}
		case "2": { //Modificar
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				try {
					this.id = Integer.parseInt(request.getParameter("id"));
				} catch (Exception ex) {
					this.id = -1;
				}
				
				this.nombre = request.getParameter("nombre");
				
				if (this.id != -1 && this.nombre != null) {
					if (daoCategoria.find(this.id) != null) {
						this.c.setId(id);
						this.c.setNombre(nombre);
						
						if (daoCategoria.update(this.c)) {
							sesion.setAttribute("modificarCategoria", "Exitoso");
						}
						else {
							sesion.setAttribute("modificarCategoria", "Fallido");
						}
						response.sendRedirect(request.getContextPath() + "/categoria/modificar" + "?id=" + this.c.getId());
					}
					else {
						response.sendRedirect(request.getContextPath());
					}
				}
				else {
					response.sendRedirect(request.getContextPath());
				}
			}	
			break;
		}
		default:
			break;
		}
	}

}