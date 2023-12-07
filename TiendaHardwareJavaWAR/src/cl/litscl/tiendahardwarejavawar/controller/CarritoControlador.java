package cl.litscl.tiendahardwarejavawar.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import cl.litscl.tiendahardwareejb.bean.ProductoDAOLocal;
import cl.litscl.tiendahardwareejb.model.Producto;

/**
 * Servlet implementation class CarritoControlador
 */
@WebServlet("/Carrito")
public class CarritoControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private ProductoDAOLocal daoProducto;
	
	private String jpql;
	   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarritoControlador() {
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
		case "mostrar": {	
			sesion.setAttribute("renderizarVista", "mostrar");
			response.sendRedirect(request.getContextPath() + "/carrito/mostrar");
			break;
		}
		default:
			break;
		}
		
		switch (opcion) { //Acceso a datos GET.
		case "1": { //Añadir.
			try {
				int idProducto = Integer.parseInt(request.getParameter("id"));
				int contador = 0;
				
				if (sesion.getAttribute("carrito") != null) {
					List<Object[]> carrito = (List<Object[]>)sesion.getAttribute("carrito");
					
					for (int i = 0; i < carrito.size(); i++) {
						if ((int)carrito.get(i)[0] == idProducto) {
							int unidadesActuales = (int)carrito.get(i)[2];
							int unidadesMaximas = ((Producto)carrito.get(i)[3]).getStock();
							if (unidadesActuales + 1 <= unidadesMaximas) {				
								carrito.get(i)[2] = unidadesActuales + 1;
								sesion.setAttribute("carrito", carrito);
								contador++;	
							}
							else {
								sesion.setAttribute("limiteUnidades", "Limite superado");
								contador++;
							}
						}
					}
				}
				
				if (sesion.getAttribute("carrito") == null) {
					List<Object[]> carrito = new ArrayList<Object[]>();
					Producto p = daoProducto.find(idProducto);
					
					Object[] productoEnCarrito = new Object[4];
					productoEnCarrito[0] = p.getId();
					productoEnCarrito[1] = p.getPrecio();
					productoEnCarrito[2] = 1;
					productoEnCarrito[3] = p;
					
					if (p != null && p.getStock() > 0) {
						carrito.add(productoEnCarrito);
						sesion.setAttribute("carrito", carrito);
					}
					else {
						sesion.setAttribute("limiteUnidades", "Limite superado");
					}
				}
				
				if (sesion.getAttribute("carrito") != null && contador == 0) {
					List<Object[]> carrito = (List<Object[]>)sesion.getAttribute("carrito");
					Producto p = daoProducto.find(idProducto);
					
					if (p != null && p.getStock() > 0) {
						Object[] productoEnCarrito = new Object[4];
						productoEnCarrito[0] = p.getId();
						productoEnCarrito[1] = p.getPrecio();
						productoEnCarrito[2] = 1;
						productoEnCarrito[3] = p;
						
						carrito.add(productoEnCarrito);
						
						if (((List<Object[]>)sesion.getAttribute("carrito")).size() == 2 && (int)((List<Object[]>)sesion.getAttribute("carrito")).get(0)[0] == (int)((List<Object[]>)sesion.getAttribute("carrito")).get(1)[0]) {
							((List<Object[]>)sesion.getAttribute("carrito")).remove(1);
						}		
						sesion.setAttribute("carrito", carrito);
					}
					else {
						sesion.setAttribute("limiteUnidades", "Limite superado");
					}
				}
				response.sendRedirect(request.getContextPath() + "/carrito/mostrar");
			} catch (Exception ex) {
				response.sendRedirect(request.getContextPath());
			}
			break;
		}
		case "2": { //Remover.
			try {
				int indice = Integer.parseInt(request.getParameter("indice"));
				List<Object[]> carrito = (List<Object[]>)sesion.getAttribute("carrito");
				
				carrito.remove(indice);
				sesion.setAttribute("carrito", carrito);
			} catch (Exception ex) {
				response.sendRedirect(request.getContextPath());
			}
			response.sendRedirect(request.getContextPath() + "/carrito/mostrar");
			break;
		}
		case "3": { //Remover todos.
			sesion.removeAttribute("carrito");
			response.sendRedirect(request.getContextPath() + "/carrito/mostrar");
			break;
		}
		case "4": { //Aumentar unidad.
			try {
				int indice = Integer.parseInt(request.getParameter("indice"));
				List<Object[]> carrito = (List<Object[]>)sesion.getAttribute("carrito");
				
				int unidadesActuales = (int)carrito.get(indice)[2];
				int unidadesMaximas = ((Producto)carrito.get(indice)[3]).getStock();
				
				if (unidadesActuales + 1 <= unidadesMaximas) {	
					carrito.get(indice)[2] = unidadesActuales + 1;
					sesion.setAttribute("carrito", carrito);
				}
				else {
					sesion.setAttribute("limiteUnidades", "Limite superado");
				}
			} catch (Exception ex) {
				response.sendRedirect(request.getContextPath());
			}
			response.sendRedirect(request.getContextPath() + "/carrito/mostrar");
			break;
		}
		case "5": { //Disminuir unidad.
			try {
				int indice = Integer.parseInt(request.getParameter("indice"));
				List<Object[]> carrito = (List<Object[]>)sesion.getAttribute("carrito");
				
				carrito.get(indice)[2] = (int)carrito.get(indice)[2] - 1;
				if ((int)carrito.get(indice)[2] == 0) {
					carrito.remove(indice);
				}
				sesion.setAttribute("carrito", carrito);
			} catch (Exception ex) {
				response.sendRedirect(request.getContextPath());
			}
			response.sendRedirect(request.getContextPath() + "/carrito/mostrar");
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