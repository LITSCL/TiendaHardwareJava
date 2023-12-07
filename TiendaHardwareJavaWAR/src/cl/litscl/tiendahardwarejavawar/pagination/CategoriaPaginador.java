package cl.litscl.tiendahardwarejavawar.pagination;

import java.util.ArrayList;
import java.util.List;

import cl.litscl.tiendahardwareejb.model.Categoria;

public class CategoriaPaginador {
	private int paginaActual = 0;
	private int totalPaginas = 0;
	private int totalResultados = 0;
	private int resultadosPorPagina = 0;
	private int indice = 0;
	
	private List<Categoria> categorias = new ArrayList<Categoria>();
	
	public CategoriaPaginador(int paginaActual, int resultadosPorPagina, List<Categoria> categorias) {
		this.paginaActual = paginaActual;
		this.totalResultados = categorias.size();
		this.resultadosPorPagina = resultadosPorPagina;
		this.totalPaginas = totalResultados / resultadosPorPagina;
		this.indice = (this.paginaActual - 1) * (this.resultadosPorPagina);
		
		if (this.totalResultados > this.totalPaginas * this.resultadosPorPagina) {
			this.totalPaginas++;
		}
		
		this.categorias = categorias;
	}
	
	public String generarRegistros(String formato, String raiz, String servlet, String vista) {
		String resultado = "";
		
		if (categorias.size() != 0) {
			if (this.paginaActual <= this.totalPaginas) {
				if (formato.equalsIgnoreCase("tarjeta")) {	
					int contador = 0;
					for (Categoria c : this.categorias) {	
						if (contador >= this.indice) {
							if (contador - this.indice == this.resultadosPorPagina) {
								break;
							}
							//
							//
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
					resultado+=         "<th>Acción 1</th>";
					resultado+=         "<th>Acción 2</th>";
					resultado+=    "</tr>";	
					
					int contador = 0;
					for (Categoria c : this.categorias) {
						if (contador >= this.indice) {
							if (contador - this.indice == this.resultadosPorPagina) {
								break;
							}
							resultado+="<tr>";
							resultado+=    "<td>" + c.getId() + "</td>";
							resultado+=    "<td>" + c.getNombre() + "</td>";
							resultado+=    "<td>" + "<a class='boton boton-amarillo'" + " href='" + raiz + "/" + servlet + "?vista=modificar" + "&id=" + c.getId() + "'>" + "Modificar" + "</a>" + "</td>";
							resultado+=    "<td>" + "<a class='boton boton-rojo'" + " href='" + raiz + "/" + servlet + "?opcion=1" + "&id=" + c.getId() + "'>" + "Eliminar" + "</a>" + "</td>";
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
	
	public String generarNumeros(String raiz, String servlet, String vista) {
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
				resultado+="<li><a " + actual + " href='" + raiz + "/" + servlet + "?vista=" + vista + "&pagina=" + i + "'>" + i + "</a></li>";
			}
			resultado+="</ul>";
		}
		
		return resultado;
	}
}