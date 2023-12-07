package cl.litscl.tiendahardwarejavawar.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cl.litscl.tiendahardwareejb.bean.PedidoDAOLocal;
import cl.litscl.tiendahardwareejb.bean.ProductoDAOLocal;
import cl.litscl.tiendahardwareejb.bean.ProductoPedidoDAOLocal;
import cl.litscl.tiendahardwareejb.model.Pedido;
import cl.litscl.tiendahardwareejb.model.Proveedor;
import cl.litscl.tiendahardwareejb.model.Usuario;
import cl.litscl.tiendahardwarejavawar.pagination.PedidoPaginador;
import cl.litscl.tiendahardwarejavawar.pagination.ProveedorPaginador;
import cl.litscl.tiendahardwarejavawar.util.CarritoUtil;

/**
 * Servlet implementation class PedidoControlador
 */
@WebServlet("/Pedido")
public class PedidoControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private PedidoDAOLocal daoPedido;
	@Inject
	private ProductoDAOLocal daoProducto;
	@Inject
	private ProductoPedidoDAOLocal daoProductoPedido;
	
	private Pedido p = new Pedido();
	
	private int id;
	private String ciudad;
	private String comuna;
	private String calle;
	private double coste;
	private String estado;
	private String fecha;
	private String hora;
	private int usuarioFK;
	
	private PedidoPaginador pedidoPaginador;
	private String registros;
	private String numeros;
	
	private String jpql;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PedidoControlador() {
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
		case "hacer": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Cliente") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				List<Object[]> carrito = (List<Object[]>)sesion.getAttribute("carrito");
				
				if (carrito != null && carrito.size() >= 1) {
					sesion.setAttribute("renderizarVista", "hacer");
					response.sendRedirect(request.getContextPath() + "/pedido/hacer");
				}
				else {
					response.sendRedirect(request.getContextPath());
				}
			}
			break;
		}
		case "confirmado": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Cliente") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				jpql = "SELECT p.id, p.coste, p.estado FROM Pedido p WHERE p.usuarioFK = " + "'" + ((Usuario)sesion.getAttribute("usuario")).getId() + "'" + " ORDER BY p.id DESC";
				Object[] datosPedido = daoPedido.customQuery(jpql).get(0);
				
				this.id = (int)datosPedido[0];
				
				jpql = "SELECT p, pp.unidades FROM Producto p INNER JOIN ProductoPedido pp ON p.id = pp.productoFK WHERE pp.pedidoFK = " + this.id;
				
				List<Object[]> productosPedido = daoProducto.customQuery(jpql);
				
				sesion.setAttribute("datosPedido", datosPedido);
				sesion.setAttribute("productosPedido", productosPedido);
				sesion.setAttribute("renderizarVista", "confirmado");
				response.sendRedirect(request.getContextPath() + "/pedido/confirmado");
			}
			break;
		}
		case "listar": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				List<Pedido> pedidos = daoPedido.getAll();
				
				boolean raiz = false;
				int paginaActual;
				try {
					paginaActual = Integer.parseInt(request.getParameter("pagina"));
				} catch (Exception ex) {
					paginaActual = 1;
					raiz = true;
				}		

				pedidoPaginador = new PedidoPaginador(paginaActual, 15, pedidos);
				
				registros = pedidoPaginador.generarRegistros("tabla", request.getContextPath(), "Pedido", "listar", "Administrador");
				numeros = pedidoPaginador.generarNumeros(request.getContextPath(), "Pedido", "listar");
				
				sesion.setAttribute("registros", registros);
				sesion.setAttribute("numeros", numeros);
				sesion.setAttribute("renderizarVista", "listar");
				
				if (raiz == true) {
					response.sendRedirect(request.getContextPath() + "/pedido/listar");
				}
				else {
					response.sendRedirect(request.getContextPath() + "/pedido/listar" + "?pagina=" + paginaActual);
				}		
			}
			break;
		}
		case "mis_pedidos": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Cliente") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				List<Pedido> pedidos = daoPedido.findAll("usuarioFK", Integer.toString(((Usuario)(sesion.getAttribute("usuario"))).getId()));
				
				boolean raiz = false;
				int paginaActual;
				try {
					paginaActual = Integer.parseInt(request.getParameter("pagina"));
				} catch (Exception ex) {
					paginaActual = 1;
					raiz = true;
				}		

				pedidoPaginador = new PedidoPaginador(paginaActual, 15, pedidos);
				
				registros = pedidoPaginador.generarRegistros("tabla", request.getContextPath(), "Pedido", "mis_pedidos", "Cliente");
				numeros = pedidoPaginador.generarNumeros(request.getContextPath(), "Pedido", "mis_pedidos");
				
				sesion.setAttribute("registros", registros);
				sesion.setAttribute("numeros", numeros);
				sesion.setAttribute("renderizarVista", "misPedidos");
				
				if (raiz == true) {
					response.sendRedirect(request.getContextPath() + "/pedido/mis-pedidos");
				}
				else {
					response.sendRedirect(request.getContextPath() + "/pedido/mis-pedidos" + "?pagina=" + paginaActual);
				}		
			}
			break;
		}
		case "detalle": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Cliente") == false) {
					response.sendRedirect(request.getContextPath());
			}
			else {
				try {
					this.id = Integer.parseInt(request.getParameter("id"));
					
					jpql = "SELECT p.id, p.coste, p.estado, p.usuarioFK FROM Pedido p WHERE p.id = " + this.id;
					Object[] datosPedido = daoPedido.customQuery(jpql).get(0);
					
					if ((((Usuario)sesion.getAttribute("usuario")).getId()) == (int)datosPedido[3]) {
						this.jpql = "SELECT p, pp.unidades FROM Producto p INNER JOIN ProductoPedido pp ON p.id = pp.productoFK WHERE pp.pedidoFK = " + this.id;
						
						List<Object[]> productosPedido = daoProducto.customQuery(jpql);
						
						sesion.setAttribute("datosPedido", datosPedido);
						sesion.setAttribute("productosPedido", productosPedido);
						sesion.setAttribute("renderizarVista", "detalle");
						response.sendRedirect(request.getContextPath() + "/pedido/detalle" + "?id=" + this.id);
					}
					else {
						sesion.setAttribute("errorDetallePedido", "El pedido no te pertenece");
						sesion.setAttribute("renderizarVista", "detalle");
						response.sendRedirect(request.getContextPath() + "/pedido/detalle" + "?id=" + this.id);
					}
				} catch (Exception ex) {
					sesion.setAttribute("errorDetallePedido", "El pedido no existe");
					sesion.setAttribute("renderizarVista", "detalle");
					response.sendRedirect(request.getContextPath() + "/pedido/detalle" + "?id=" + this.id);
				}
			}
			break;
		}
		case "gestionar": {
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				try {
					this.id = Integer.parseInt(request.getParameter("id"));
				} catch (Exception ex) {
					this.id = -1;
				}
				
				if (this.id != -1 && daoPedido.find(this.id) != null) {
					jpql = "SELECT p.id, p.coste, p.estado FROM Pedido p WHERE p.id = " + this.id;
					Object[] datosPedido = daoPedido.customQuery(jpql).get(0);
					
					jpql = "SELECT p, pp.unidades FROM Producto p INNER JOIN ProductoPedido pp ON p.id = pp.productoFK WHERE pp.pedidoFK = " + this.id;
					
					List<Object[]> productosPedido = daoProducto.customQuery(jpql);
					
					sesion.setAttribute("datosPedido", datosPedido);
					sesion.setAttribute("productosPedido", productosPedido);
					sesion.setAttribute("renderizarVista", "gestionar");
					response.sendRedirect(request.getContextPath() + "/pedido/gestionar" + "?id=" + this.id);
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
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Cliente") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				List<Object[]> carrito = (List<Object[]>)sesion.getAttribute("carrito");
				
				DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			    DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:ss");
				
				this.ciudad = request.getParameter("ciudad");
				this.comuna = request.getParameter("comuna");
				this.calle = request.getParameter("calle");
				this.coste = new CarritoUtil().obtenerTotal(carrito);
				this.estado = "Confirmado";
				this.fecha = fecha.format(LocalDateTime.now());
			    this.hora = hora.format(LocalDateTime.now());
				this.usuarioFK = ((Usuario)(sesion.getAttribute("usuario"))).getId();
				
				if (this.ciudad != null && this.comuna != null && this.calle != null) {
					this.p.setCiudad(ciudad);
					this.p.setComuna(comuna);
					this.p.setCalle(calle);
					this.p.setCoste(coste);
					this.p.setEstado(estado);
					this.p.setFecha(this.fecha);
					this.p.setHora(this.hora);
					this.p.setUsuarioFK(usuarioFK);
					
					if (daoPedido.save(p)) {
						jpql = "SELECT p.id, p.coste, p.estado FROM Pedido p WHERE p.usuarioFK = " + "'" + ((Usuario)sesion.getAttribute("usuario")).getId() + "'" + " ORDER BY p.id DESC";
						
						int idPedido = (int)daoPedido.customQuery(jpql).get(0)[0];

						if (daoProductoPedido.save(carrito, idPedido)) {
							sesion.setAttribute("crearPedido", "Exitoso");
							sesion.removeAttribute("carrito");
						}
						else {
							sesion.setAttribute("crearPedido", "Fallido");
						}
					}
					else {
						sesion.setAttribute("crearPedido", "Fallido");
					}
					response.sendRedirect(request.getContextPath() + "/pedido/confirmado");
				}
				else {
					response.sendRedirect(request.getContextPath());
				}
			}
			break;
		}
		case "2": { //Cambiar Estado.
			if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
				response.sendRedirect(request.getContextPath());
			}
			else {
				try {
					this.id = Integer.parseInt(request.getParameter("id"));
				} catch (Exception ex) {
					this.id = -1;
				}
					
				this.estado = request.getParameter("estado");
				
				if (this.id != -1 && this.estado != null) {
					if (daoPedido.updateOne(this.id, "estado", this.estado)) {
						response.sendRedirect(request.getContextPath() + "/pedido/gestionar" + "?id=" + this.id);
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