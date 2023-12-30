package cl.litscl.tiendahardwarejavawar.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import cl.litscl.tiendahardwareejb.bean.CategoriaDAOLocal;
import cl.litscl.tiendahardwareejb.bean.ProductoDAOLocal;
import cl.litscl.tiendahardwareejb.bean.ProductoUsuarioDAOLocal;
import cl.litscl.tiendahardwareejb.model.Producto;
import cl.litscl.tiendahardwareejb.model.ProductoUsuario;
import cl.litscl.tiendahardwareejb.model.Usuario;
import cl.litscl.tiendahardwarejavawar.pagination.ProductoPaginador;
import cl.litscl.tiendahardwarejavawar.util.ArchivoUtil;

/**
 * Servlet implementation class ProductoControlador
 */
@MultipartConfig
@WebServlet("/Producto")
public class ProductoControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject
	private ProductoDAOLocal daoProducto;
	@Inject
	private CategoriaDAOLocal daoCategoria;
	@Inject
	private ProductoUsuarioDAOLocal daoProductoUsuario;
	
	private Producto p = new Producto();
	
	private int id;
	private String nombre;
	private String descripcion;
	private double precio;
	private int stock;
	private String imagen = "";
	private int categoriaFK;
	
	private Part archivo;
	private String rutaArchivos;
	private File rutaArchivoDestino;
	private String[] formatosSoportados = {".ico", ".png", ".jpg", ".jpeg"};
	
	private ArchivoUtil archivoUtil = new ArchivoUtil();
	
	private ProductoPaginador productoPaginador;
	private String registros;
	private String numeros;
	
	private String jpql;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoControlador() {
	    super();
	    // TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		
		this.rutaArchivos = getServletContext().getRealPath("/uploads/models/producto/images/");
		this.rutaArchivoDestino = new File(rutaArchivos);
		
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
					sesion.setAttribute("categorias", daoCategoria.getAll());
					
					sesion.setAttribute("renderizarVista", "crear");
					response.sendRedirect(request.getContextPath() + "/producto/crear");
				}
				break;
			}
			case "listar": {
				if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
					response.sendRedirect(request.getContextPath());
				}
				else {
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
					
					registros = productoPaginador.generarRegistros("tabla", request.getContextPath(), "Producto", "listar", null);
					numeros = productoPaginador.generarNumeros(request.getContextPath(), "Producto", "listar", null, null);
					
					sesion.setAttribute("registros", registros);
					sesion.setAttribute("numeros", numeros);
					sesion.setAttribute("renderizarVista", "listar");
					
					if (raiz == true) {
						response.sendRedirect(request.getContextPath() + "/producto/listar");
					}
					else {
						response.sendRedirect(request.getContextPath() + "/producto/listar" + "?pagina=" + paginaActual);
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
						if (daoProducto.find(this.id) != null) {
							this.p = daoProducto.find(this.id);
							
							sesion.setAttribute("categorias", daoCategoria.getAll());
							sesion.setAttribute("producto", this.p);
							sesion.setAttribute("renderizarVista", "modificar");
							response.sendRedirect(request.getContextPath() + "/producto/modificar" + "?id=" + this.p.getId());
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
			case "favoritos": {
				if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Cliente") == false) {
						response.sendRedirect(request.getContextPath());
				}
				else {
					List<Producto> productos = new ArrayList<Producto>();
					List<ProductoUsuario> productosUsuario = daoProductoUsuario.getAll();
					
					for (ProductoUsuario pu : productosUsuario) {
						if (pu.getUsuarioFK() == ((Usuario)sesion.getAttribute("usuario")).getId()) {
							productos.add(daoProducto.find(pu.getProductoFK()));
						}
					}
					
					boolean raiz = false;
					int paginaActual;
					try {
						paginaActual = Integer.parseInt(request.getParameter("pagina"));
					} catch (Exception ex) {
						paginaActual = 1;
						raiz = true;
					}		

					productoPaginador = new ProductoPaginador(paginaActual, 15, productos);
					
					registros = productoPaginador.generarRegistros("tarjeta", request.getContextPath(), "Producto", "favoritos", (Usuario)sesion.getAttribute("usuario"));
					numeros = productoPaginador.generarNumeros(request.getContextPath(), "Producto", "favoritos", null, null);
					
					sesion.setAttribute("registros", registros);
					sesion.setAttribute("numeros", numeros);
					sesion.setAttribute("renderizarVista", "favoritos");
					
					if (raiz == true) {
						response.sendRedirect(request.getContextPath() + "/producto/favoritos");
					}
					else {
						response.sendRedirect(request.getContextPath() + "/producto/favoritos" + "?pagina=" + paginaActual);
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
						if (daoProducto.delete(this.id) == true) {
							sesion.setAttribute("eliminarProducto", "Exitoso");
						}
						else {
							sesion.setAttribute("eliminarProducto", "Fallido");
						}
						response.sendRedirect(request.getContextPath() + "/producto/listar");
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
		
		this.rutaArchivos = getServletContext().getRealPath("/uploads/models/producto/images/");
		this.rutaArchivoDestino = new File(rutaArchivos);
		
		String opcion = request.getParameter("opcion");
		
		switch (opcion) { //Acceso a datos POST.
			case "1": { //Crear.
				if (sesion.getAttribute("usuario") == null || (((Usuario)(sesion.getAttribute("usuario"))).getTipo()).equals("Administrador") == false) {
					response.sendRedirect(request.getContextPath());
				}
				else {
					this.nombre = request.getParameter("nombre");
					this.descripcion = request.getParameter("descripcion");
					
					try {
						this.precio = Double.parseDouble(request.getParameter("precio"));
					} catch (Exception ex) {
						this.precio = -1;
					}

					try {
						this.stock = Integer.parseInt(request.getParameter("stock"));
					} catch (Exception ex) {
						this.stock = -1;
					}

					try {
						this.categoriaFK = Integer.parseInt(request.getParameter("categoria"));
					} catch (Exception ex) {
						this.categoriaFK = -1;
					}	
					
					archivo = request.getPart("imagen");
					
					if (this.nombre != null && this.descripcion != null && this.precio != -1 && this.stock != -1 && this.categoriaFK != -1) {
						if (archivo.getSubmittedFileName().equals("") == false && archivoUtil.validarFormato(archivo.getSubmittedFileName(), formatosSoportados) == true) {			
							this.imagen = archivoUtil.guardarArchivo(archivo, rutaArchivoDestino);
							
							this.p.setNombre(nombre);
							this.p.setDescripcion(descripcion);
							this.p.setPrecio(precio);
							this.p.setStock(stock);
							this.p.setImagen(imagen);
							this.p.setCategoriaFK(categoriaFK);
							
							if (daoProducto.save(this.p)) {
								sesion.setAttribute("crearProducto", "Exitoso");
							}
							else {
								sesion.setAttribute("crearProducto", "Fallido");
							}	
						}
						else {
							sesion.setAttribute("crearProducto", "Fallido");
						}	
						response.sendRedirect(request.getContextPath() + "/producto/crear");
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
					this.descripcion = request.getParameter("descripcion");
					
					try {
						this.precio = Double.parseDouble(request.getParameter("precio"));
					} catch (Exception ex) {
						this.precio = -1;
					}

					try {
						this.stock = Integer.parseInt(request.getParameter("stock"));
					} catch (Exception ex) {
						this.stock = -1;
					}

					try {
						this.categoriaFK = Integer.parseInt(request.getParameter("categoria"));
					} catch (Exception ex) {
						this.categoriaFK = -1;
					}
					
					archivo = request.getPart("imagen");
					
					if (this.id != -1 && this.nombre != null && this.descripcion != null && this.precio != -1 && this.stock != -1 && this.categoriaFK != -1) {
						if (archivo.getSubmittedFileName().equals("") == true && daoProducto.find(this.id) != null) {
							this.p.setId(id);
							this.p.setNombre(nombre);
							this.p.setDescripcion(descripcion);
							this.p.setPrecio(precio);
							this.p.setStock(stock);
							this.p.setImagen(daoProducto.find(this.id).getImagen());
							this.p.setCategoriaFK(categoriaFK);			
							
							if (daoProducto.update(this.p)) {
								sesion.setAttribute("modificarProducto", "Exitoso");
							}
							else {
								sesion.setAttribute("modificarProducto", "Fallido");
							}
							response.sendRedirect(request.getContextPath() + "/producto/modificar" + "?id=" + this.p.getId());
						}
						else if (archivo.getSubmittedFileName().equals("") == false && daoProducto.find(this.id) != null) {		
							if (archivoUtil.validarFormato(archivo.getSubmittedFileName(), formatosSoportados) == true) {	
								File archivoAntiguo = new File(rutaArchivos + daoProducto.find(this.id).getImagen());
								archivoAntiguo.delete();
								
								this.p.setId(id);
								this.p.setNombre(nombre);
								this.p.setDescripcion(descripcion);
								this.p.setPrecio(precio);
								this.p.setStock(stock);
								this.p.setImagen(archivoUtil.guardarArchivo(archivo, rutaArchivoDestino));
								this.p.setCategoriaFK(categoriaFK);	
								
								if (daoProducto.update(this.p)) {
									sesion.setAttribute("modificarProducto", "Exitoso");
								}
								else {
									sesion.setAttribute("modificarProducto", "Fallido");
								}
							}
							response.sendRedirect(request.getContextPath() + "/producto/modificar" + "?id=" + this.p.getId());
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