package cl.litscl.tiendahardwarejavawar.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import cl.litscl.tiendahardwareejb.bean.ProductoUsuarioDAOLocal;
import cl.litscl.tiendahardwareejb.model.ProductoUsuario;
import cl.litscl.tiendahardwareejb.model.Proveedor;
import cl.litscl.tiendahardwareejb.model.Usuario;
import cl.litscl.tiendahardwarejavawar.pagination.ProveedorPaginador;

/**
 * Servlet implementation class ProductoUsuarioControlador
 */
@WebServlet("/ProductoUsuario")
public class ProductoUsuarioControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private ProductoUsuarioDAOLocal daoProductoUsuario;
	
	private ProductoUsuario pu = new ProductoUsuario();
	
	private int id;
	private int productoFK; 
	private int usuarioFK;
	
	private String jpql;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoUsuarioControlador() {
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
		case "vista": {
			break;
		}
		default:
			break;
		}
		
		switch (opcion) { //Acceso a datos GET.
		case "1": { //Crear.
			JsonObjectBuilder builder = Json.createObjectBuilder();
			JsonObject objetoJSON = null;
			
			if (sesion.getAttribute("usuario") != null) {
				if ((((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador")) {
					objetoJSON = builder.add("mensaje", "SERVIDOR: Eres administrador").build();
				}
				else if ((((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Cliente")) {
					try {
						this.productoFK = Integer.parseInt(request.getParameter("id"));
					} catch (Exception ex) {
						this.productoFK = -1;
					}

					List<Object[]> resultado = daoProductoUsuario.customQuery("SELECT pu.id, pu.usuarioFK, pu.productoFK FROM ProductoUsuario pu WHERE pu.usuarioFK = " + "'" + ((Usuario)(sesion.getAttribute("usuario"))).getId() + "'" + " AND pu.productoFK = " + this.productoFK);
					
					if (resultado.size() == 0) {
						this.pu.setProductoFK(productoFK);
						this.pu.setUsuarioFK(((Usuario)(sesion.getAttribute("usuario"))).getId());
						
						if (daoProductoUsuario.save(this.pu)) {
							objetoJSON = builder.add("mensaje", "SERVIDOR: Has dado like correctamente").build();
						}
						else {
							objetoJSON = builder.add("mensaje", "SERVIDOR: No has podido dar like correctamente").build();
						}
					}
					else {
						objetoJSON = builder.add("mensaje", "SERVIDOR: No has podido dar like correctamente").build();
					}
				}
			}
			else {
				objetoJSON = builder.add("mensaje", "SERVIDOR: No estas logeado").build();
			}
			
			response.setContentType(MediaType.APPLICATION_JSON);
			try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
				pw.println(objetoJSON.toString());
			}
			break;
		}
		case "2": { //Eliminar.
			JsonObjectBuilder builder = Json.createObjectBuilder();
			JsonObject objetoJSON = null;
			
			if (sesion.getAttribute("usuario") != null) {
				if ((((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador")) {
					objetoJSON = builder.add("mensaje", "SERVIDOR: Eres administrador").build();
				}
				else if ((((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Cliente")) {
					try {
						this.productoFK = Integer.parseInt(request.getParameter("id"));
					} catch (Exception ex) {
						this.productoFK = -1;
					}

					List<Object[]> resultado = daoProductoUsuario.customQuery("SELECT pu.id, pu.usuarioFK, pu.productoFK FROM ProductoUsuario pu WHERE pu.usuarioFK = " + "'" + ((Usuario)(sesion.getAttribute("usuario"))).getId() + "'" + " AND pu.productoFK = " + this.productoFK);
					
					if (resultado.size() == 1) {
						if (daoProductoUsuario.delete((int)resultado.get(0)[0])) {
							objetoJSON = builder.add("mensaje", "SERVIDOR: Has dado dislike correctamente").build();
						}
						else {
							objetoJSON = builder.add("mensaje", "SERVIDOR: No has podido dar dislike correctamente").build();
						}
					}
					else {
						objetoJSON = builder.add("mensaje", "SERVIDOR: No has podido dar dislike correctamente").build();
					}
				}
			}
			else {
				objetoJSON = builder.add("mensaje", "SERVIDOR: No estas logeado").build();
			}
			
			response.setContentType(MediaType.APPLICATION_JSON);
			try (PrintWriter pw = new PrintWriter(response.getOutputStream())) {
				pw.println(objetoJSON.toString());
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
		
		case "1": {
			//
			break;
		}
		default:
			break;
		}
	}

}