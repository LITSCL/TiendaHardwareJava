package cl.litscl.tiendahardwarejavawar.pagination;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cl.litscl.tiendahardwareejb.model.Pedido;

public class PedidoPaginador {
	private int paginaActual = 0;
	private int totalPaginas = 0;
	private int totalResultados = 0;
	private int resultadosPorPagina = 0;
	private int indice = 0;
	
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	
	public PedidoPaginador(int paginaActual, int resultadosPorPagina, List<Pedido> pedidos) {
		this.paginaActual = paginaActual;
		this.totalResultados = pedidos.size();
		this.resultadosPorPagina = resultadosPorPagina;
		this.totalPaginas = totalResultados / resultadosPorPagina;
		this.indice = (this.paginaActual - 1) * (this.resultadosPorPagina);
		
		if (this.totalResultados > this.totalPaginas * this.resultadosPorPagina) {
			this.totalPaginas++;
		}
		
		this.pedidos = pedidos;
	}
	
	public String generarRegistros(String formato, String raiz, String servlet, String vista, String tipoUsuario) {
		String resultado = "";
		
		if (pedidos.size() != 0) {
			if (this.paginaActual <= this.totalPaginas) {
				if (formato.equalsIgnoreCase("tarjeta")) {	
					int contador = 0;
					for (Pedido p : this.pedidos) {	
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
					if (tipoUsuario.equals("Administrador")) {
						resultado+= "<table class='tabla'";
						resultado+=     "<tr>";
						resultado+=         "<th>ID</th>";
						resultado+=         "<th>Ciudad</th>";
						resultado+=         "<th>Comuna</th>";
						resultado+=         "<th>Calle</th>";
						resultado+=         "<th>Coste</th>";
						resultado+=         "<th>Estado</th>";
						resultado+=         "<th>Fecha</th>";
						resultado+=         "<th>Hora</th>";
						resultado+=         "<th>Usuario</th>";
						resultado+=         "<th>Acción 1</th>";
						resultado+=    "</tr>";	
						
						int contador = 0;
						for (Pedido p : this.pedidos) {
							if (contador >= this.indice) {
								if (contador - this.indice == this.resultadosPorPagina) {
									break;
								}
								resultado+="<tr>";
								resultado+=    "<td>" + p.getId() + "</td>";
								resultado+=    "<td>" + p.getCiudad() + "</td>";
								resultado+=    "<td>" + p.getComuna() + "</td>";
								resultado+=    "<td>" + p.getCalle() + "</td>";
								resultado+=    "<td>" + "$" + new DecimalFormat().format(p.getCoste()) + "</td>";
								resultado+=    "<td>" + p.getEstado() + "</td>";
								resultado+=    "<td>" + p.getFecha() + "</td>";
								resultado+=    "<td>" + p.getHora() + "</td>";
								resultado+=    "<td>" + p.getUsuarioFK() + "</td>";
								resultado+=    "<td>" + "<a class='boton boton-amarillo'" + " href='" + raiz + "/" + servlet + "?vista=gestionar" + "&id=" + p.getId() + "'>" + "Gestionar" + "</a>" + "</td>";
								resultado+="</tr>";
							}
							contador++;				
						}		
						resultado+="</table>";
					}
					else if (tipoUsuario.equals("Cliente")) {
						resultado+= "<table class='tabla'";
						resultado+=     "<tr>";
						resultado+=         "<th>N° Pedido</th>";
						resultado+=         "<th>Coste</th>";
						resultado+=         "<th>Fecha</th>";
						resultado+=         "<th>Estado</th>";
						resultado+=    "</tr>";	
						
						int contador = 0;
						for (Pedido p : this.pedidos) {
							if (contador >= this.indice) {
								if (contador - this.indice == this.resultadosPorPagina) {
									break;
								}
								resultado+="<tr>";
								resultado+=    "<td>" + "<a class='numeroPedidoRedireccionador'" + " href='" + raiz  + "/" + servlet + "?vista=detalle" + "&id=" + p.getId() + "'>" + p.getId() + "</a>" + "</td>";
								resultado+=    "<td>" + "$" + new DecimalFormat().format(p.getCoste()) + "</td>";
								resultado+=    "<td>" + p.getFecha() + "</td>";
								resultado+=    "<td>" + p.getEstado() + "</td>";
								resultado+="</tr>";
							}
							contador++;				
						}		
						resultado+="</table>";
					}
					else {
						return resultado;
					}
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