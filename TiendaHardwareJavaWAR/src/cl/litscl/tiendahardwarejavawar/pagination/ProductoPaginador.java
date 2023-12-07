package cl.litscl.tiendahardwarejavawar.pagination;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cl.litscl.tiendahardwareejb.bean.ProductoUsuarioDAO;
import cl.litscl.tiendahardwareejb.bean.ProductoUsuarioDAOLocal;
import cl.litscl.tiendahardwareejb.fk.ProductoFK;
import cl.litscl.tiendahardwareejb.model.Producto;
import cl.litscl.tiendahardwareejb.model.ProductoUsuario;
import cl.litscl.tiendahardwareejb.model.Usuario;

public class ProductoPaginador {
	private int paginaActual = 0;
	private int totalPaginas = 0;
	private int totalResultados = 0;
	private int resultadosPorPagina = 0;
	private int indice = 0;
	
	private List<Producto> productos = new ArrayList<Producto>();
	
	public ProductoPaginador(int paginaActual, int resultadosPorPagina, List<Producto> productos) {
		this.paginaActual = paginaActual;
		this.totalResultados = productos.size();
		this.resultadosPorPagina = resultadosPorPagina;
		this.totalPaginas = totalResultados / resultadosPorPagina;
		this.indice = (this.paginaActual - 1) * (this.resultadosPorPagina);
		
		if (this.totalResultados > this.totalPaginas * this.resultadosPorPagina) {
			this.totalPaginas++;
		}
		
		this.productos = productos;
	}
	
	public String generarRegistros(String formato, String raiz, String servlet, String vista, Usuario usuarioConectado) {
		String resultado = "";
		
		if (productos.size() != 0) {
			if (this.paginaActual <= this.totalPaginas) {
				if (formato.equalsIgnoreCase("tarjeta")) {	
					int contador = 0;
					for (Producto p : this.productos) {	
						if (contador >= this.indice) {
							if (contador - this.indice == this.resultadosPorPagina) {
								break;
							}
							
							List<ProductoUsuario> productosUsuario = new ProductoUsuarioDAO().getAll();
							
							boolean favorito = false;
							if (usuarioConectado != null && usuarioConectado.getTipo().equals("Cliente") == true) {
								for (ProductoUsuario pu: productosUsuario) {
									if (pu.getProductoFK() == p.getId() && pu.getUsuarioFK() == usuarioConectado.getId()) {
										favorito = true;
									}
								}
							}
							resultado+="<div class='tarjeta'>";
							resultado+=    "<div class='tarjeta-cabecera'>";
							resultado+=        "<div class='contenedor-corazon'>";
							if (favorito == true) {
								resultado+=        "<img class='boton-like' data-idProducto='" + p.getId() + "'" + " src='" + raiz + "/assets/img/heart-red.png" + "'>";
							}
							else {
								resultado+=        "<img class='boton-dislike' data-idProducto='" + p.getId() + "'" + " src='" + raiz + "/assets/img/heart-gray.png" + "'>";
							}		
							resultado+=        "</div>";
							resultado+=        "<h1>" + p.getNombre() + "</h1>";
							resultado+=    "</div>";
							resultado+=        "<div class='tarjeta-cuerpo'>";
							resultado+=            "<img src='" + raiz + "/uploads/models/producto/images/" + p.getImagen() + "'>";
							resultado+=            "<h1>Descripción</h1>";
							resultado+=            "<ul>";
							String[] descripcionSeparada = p.getDescripcion().split(";");
							for (int i = 0; i < descripcionSeparada.length; i++) {
								resultado+=            "<li>" + descripcionSeparada[i] + "</li>";
							}
							resultado+=            "</ul>";
							resultado+=            "<div class='contenedor-precio'>";
							resultado+=                "$" + new DecimalFormat().format(p.getPrecio());
							resultado+=            "</div>";
							resultado+=            "<div class='clearfix'></div>";
							resultado+=            "<div class='contenedor-disponibilidad'>";
							resultado+=                "<span>Disponibilidad: </span>" + p.getStock();
							resultado+=        	   "</div>";
							resultado+=        "</div>";
							resultado+=    "<div class='tarjeta-pie'>";
							resultado+=        "<div>";
							resultado+=            "<a class='boton boton-verde'" + " href='" + raiz + "/Carrito?opcion=1" + "&id=" + p.getId() + "'>" + "Agregar al carrito" + "</a>";
							resultado+=        "</div>";
							resultado+=    "</div>";
							resultado+="</div>";
						}
						contador++;	
					}					
					return resultado;
				}
				else if (formato.equalsIgnoreCase("tabla")) {
					resultado+= "<table class='tabla'";
					resultado+=     "<tr>";
					resultado+=         "<th>ID</th>";
					resultado+=         "<th>Nombre</th>";
					resultado+=         "<th>Descripción</th>";
					resultado+=         "<th>Precio</th>";
					resultado+=         "<th>Stock</th>";
					resultado+=         "<th>Imagen</th>";
					resultado+=         "<th>Categoría</th>";
					resultado+=         "<th>Acción 1</th>";
					resultado+=         "<th>Acción 2</th>";
					resultado+=    "</tr>";	
					
					int contador = 0;
					for (Producto p : this.productos) {
						if (contador >= this.indice) {
							if (contador - this.indice == this.resultadosPorPagina) {
								break;
							}
							resultado+="<tr>";
							resultado+=    "<td>" + p.getId() + "</td>";
							resultado+=    "<td>" + p.getNombre() + "</td>";
							resultado+=    "<td>" + p.getDescripcion() + "</td>";
							resultado+=    "<td>" + "$" + new DecimalFormat().format(p.getPrecio()) + "</td>";
							resultado+=    "<td>" + p.getStock() + "</td>";
							resultado+=    "<td>" + "<img src='" + raiz + "/uploads/models/producto/images/" + p.getImagen() + "'>" + "</td>";
							resultado+=    "<td>" + new ProductoFK().getCategoriaNombre(p) + "</td>";
							resultado+=    "<td>" + "<a class='boton boton-amarillo'" + " href='" + raiz + "/" + servlet + "?vista=modificar" + "&id=" + p.getId() + "'>" + "Modificar" + "</a>" + "</td>";
							resultado+=    "<td>" + "<a class='boton boton-rojo'" + " href='" + raiz + "/" + servlet + "?opcion=1" + "&id=" + p.getId() + "'>" + "Eliminar" + "</a>" + "</td>";
							resultado+="</tr>";
						}
						contador++;				
					}		
					resultado+="</table>";

					return resultado;
				}
				else {
					return resultado;
				}
			}
			else {
				resultado = "Pagina Inexistente";
				return resultado;
			}
		}
		else {
			resultado = "Sin Registros";
			return resultado;
		}
	}
	
	public String generarNumeros(String raiz, String servlet, String vista, String nombreFiltro, String valorFiltro) {
		String resultado = "";
		String actual = "";
		
		if (this.paginaActual <= this.totalPaginas) {
			resultado = "<ul>";
			for (int i = 1; i <= this.totalPaginas; i++) {
				if (i == this.paginaActual) {
					actual = "class='actual'";
				}
				else {
					actual = "";
				}	
				if (nombreFiltro == null && valorFiltro == null) {
					resultado+="<li><a " + actual + " href='" + raiz + "/" + servlet + "?vista=" + vista + "&pagina=" + i + "'>" + i + "</a></li>";
				}
				else {
					resultado+="<li><a " + actual + " href='" + raiz + "/" + servlet + "?vista=" + vista + "&" + nombreFiltro + "=" + valorFiltro + "&pagina=" + i + "'>" + i + "</a></li>";
				}
			}
			resultado+="</ul>";
		}
		
		return resultado;
	}
}