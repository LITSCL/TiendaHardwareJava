<%@page import="cl.litscl.tiendahardwarejavawar.util.CarritoUtil"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="cl.litscl.tiendahardwareejb.model.Producto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mostrar Carrito</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "mostrar") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div id="contenedorMostrarCarrito" class="contenedor-pagina-centrada">
		<%
		if (session.getAttribute("limiteUnidades") == "Limite superado") {
		%>
			<strong class="alerta alerta-amarilla">Lo sentimos, no tenemos suficientes unidades de ese producto</strong>
		<%
		}
		if (session.getAttribute("carrito") != null && ((List<Object[]>)session.getAttribute("carrito")).size() >= 1) {
			List<Object[]> carrito = (List<Object[]>)session.getAttribute("carrito");
		%>
			<table class="tabla">
				<tr>
					<th>Imagen</th>
					<th>Nombre</th>
					<th>Precio</th>
					<th>Unidades</th>
					<th>Acción</th>
				</tr>
			<%
			for (int i = 0; i < carrito.size(); i++) {
			%>
				<tr>
					<td>
						<img class="imagen-carrito" src="<%=request.getContextPath() + "/uploads/models/producto/images/" + ((Producto)(carrito.get(i)[3])).getImagen()%>"/>
					</td>
					<td>
						<a href="#"><%=((Producto)(carrito.get(i)[3])).getNombre()%></a>
					</td>
					<td>
						<%="$" + new DecimalFormat().format(((Producto)(carrito.get(i)[3])).getPrecio())%>
					</td>
					<td>
						<%=carrito.get(i)[2]%>
						<div id="cambiarUnidades"><%%>
							<a class="boton boton-verde" href="<%=request.getContextPath() + "/Carrito?opcion=4" + "&indice=" + i%>">+</a>
							<a class="boton boton-verde" href="<%=request.getContextPath() + "/Carrito?opcion=5" + "&indice=" + i%>">-</a>
						</div>			
					</td>
					<td>
						<a class="boton boton-carrito boton-rojo" href="<%=request.getContextPath() + "/Carrito?opcion=2" + "&indice=" + i%>">Eliminar</a>
					</td>
				</tr>
			<%
			}
			%>
			</table>
			
			<div id="vaciarCarrito">
				<a class="boton boton-vaciar boton-rojo" href="<%=request.getContextPath() + "/Carrito?opcion=3"%>">Vaciar carrito</a>
			</div>
			
			<div id="totalCarrito">
				<a class="boton boton-pedido" href="<%=request.getContextPath() + "/pedido/hacer"%>">Hacer pedido</a>
				<h3 id="total">Total: $<%=new DecimalFormat().format((new CarritoUtil().obtenerTotal(carrito)))%></h3>
			</div>
		<%
		}
		else {
		%>
			<h1 class="titulo-pagina-centrada">El carrito esta vacío</h1>
		<% 
		}
		%>
		</div>
		
	</div>
<%
	session.removeAttribute("limiteUnidades");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Carrito?vista=mostrar");
}
%>
</body>
</html>