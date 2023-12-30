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

import cl.litscl.tiendahardwareejb.bean.ProveedorDAOLocal;
import cl.litscl.tiendahardwareejb.model.Producto;
import cl.litscl.tiendahardwareejb.model.Proveedor;
import cl.litscl.tiendahardwareejb.model.Usuario;
import cl.litscl.tiendahardwarejavawar.pagination.ProductoPaginador;
import cl.litscl.tiendahardwarejavawar.pagination.ProveedorPaginador;

/**
 * Servlet implementation class ProveedorControlador
 */
@WebServlet("/Proveedor")
public class ProveedorControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private ProveedorDAOLocal daoProveedor;
	
	private Proveedor p = new Proveedor();
	   
	private int id;
	private String nombre;
	private String telefono;
	private String correo;
	
	private ProveedorPaginador proveedorPaginador;
	private String registros;
	private String numeros;
	
	private String jpql;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProveedorControlador() {
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
			case "crear":	
				if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
					response.sendRedirect(request.getContextPath());
				}
				else {
					sesion.setAttribute("renderizarVista", "crear");
					response.sendRedirect(request.getContextPath() + "/proveedor/crear");
				}
				break;
			case "listar": {
				if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
					response.sendRedirect(request.getContextPath());
				}
				else {
					List<Proveedor> proveedores = daoProveedor.getAll();
					
					boolean raiz = false;
					int paginaActual;
					try {
						paginaActual = Integer.parseInt(request.getParameter("pagina"));
					} catch (Exception ex) {
						paginaActual = 1;
						raiz = true;
					}		

					proveedorPaginador = new ProveedorPaginador(paginaActual, 15, proveedores);
					
					registros = proveedorPaginador.generarRegistros("tabla", request.getContextPath(), "Proveedor", "listar");
					numeros = proveedorPaginador.generarNumeros(request.getContextPath(), "Proveedor", "listar");
					
					sesion.setAttribute("registros", registros);
					sesion.setAttribute("numeros", numeros);
					sesion.setAttribute("renderizarVista", "listar");
					
					if (raiz == true) {
						response.sendRedirect(request.getContextPath() + "/proveedor/listar");
					}
					else {
						response.sendRedirect(request.getContextPath() + "/proveedor/listar" + "?pagina=" + paginaActual);
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
					
					if (this.id != -1) {
						if (daoProveedor.find(this.id) != null) {
							this.p = daoProveedor.find(this.id);
							
							sesion.setAttribute("proveedor", this.p);
							sesion.setAttribute("renderizarVista", "modificar");
							response.sendRedirect(request.getContextPath() + "/proveedor/modificar" + "?id=" + this.p.getId());
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
						if (daoProveedor.delete(this.id) == true) {
							sesion.setAttribute("eliminarProveedor", "Exitoso");
						}
						else {
							sesion.setAttribute("eliminarProveedor", "Fallido");
						}
						response.sendRedirect(request.getContextPath() + "/proveedor/listar");
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
		
		switch (asincrono) { //Peticiones REST.
			case "1": {
				//
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
					this.telefono = request.getParameter("telefono");
					this.correo = request.getParameter("correo");
					
					if (this.nombre != null && this.telefono != null && this.correo != null) {
						this.p.setNombre(nombre);
						this.p.setTelefono(telefono);
						this.p.setCorreo(correo);
						
						if (daoProveedor.save(this.p)) {
							sesion.setAttribute("crearProveedor", "Exitoso");
						}
						else {
							sesion.setAttribute("crearProveedor", "Fallido");
						}		
						response.sendRedirect(request.getContextPath() + "/proveedor/crear");
					}
					else {
						response.sendRedirect(request.getContextPath());
					}
				}
				break;
			}
			case "2": { //Modificar.
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
					this.telefono = request.getParameter("telefono");
					this.correo = request.getParameter("correo");
					
					if (this.id != -1 && this.nombre != null && this.telefono != null && this.correo != null) {
						if (daoProveedor.find(this.id) != null) {
							this.p.setId(id);
							this.p.setNombre(nombre);
							this.p.setTelefono(telefono);
							this.p.setCorreo(correo);
							
							if (daoProveedor.update(this.p)) {
								sesion.setAttribute("modificarProveedor", "Exitoso");
							}
							else {
								sesion.setAttribute("modificarProveedor", "Fallido");
							}
							response.sendRedirect(request.getContextPath() + "/proveedor/modificar" + "?id=" + this.p.getId());
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