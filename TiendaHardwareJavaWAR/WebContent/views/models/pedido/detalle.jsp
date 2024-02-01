<%@page import="cl.litscl.tiendahardwareejb.model.Producto"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Detalle Pedido</title>
	<%@ include file='../../includes/head_styles.jsp' %>
	<%@ include file='../../includes/head_scripts.jsp' %>
	<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "detalle") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-pagina-centrada">
		<%
		if (session.getAttribute("errorDetallePedido") == "El pedido no te pertenece") {
		%>
			<h1 class="titulo-pagina-centrada">El pedido que buscas no te pertenece</h1>
		<%
		}
		else if (session.getAttribute("errorDetallePedido") == "El pedido no existe") {
		%>	
			<h1 class="titulo-pagina-centrada">El pedido que buscas no existe</h1>
		<%
		}
		else {
			Object[] datosPedido = (Object[])session.getAttribute("datosPedido");
			List<Object[]> productosPedido = (List<Object[]>)session.getAttribute("productosPedido");
		%>
			<br/>
			<h3>Datos del pedido:</h3>
			<div class="contenedor-datos-pedido">
				Numero de pedido: <%=datosPedido[0]%>
				<br/>
				Total a pagar: $<%=new DecimalFormat().format(datosPedido[1])%>
				<br/>
				Estado del pedido: <%=datosPedido[2]%>
				<br/>
				Productos:
				<table class="tabla">
					<tr>
						<th>Imagen</th>
						<th>Nombre</th>
						<th>Precio</th>
						<th>Unidades</th>
					</tr>
				<%
				for (Object[] o : productosPedido) {
				%>
					<tr>
						<td>
							<img class="imagen-carrito" src="<%=request.getContextPath() +  "/uploads/models/producto/images/" + ((Producto)o[0]).getImagen()%>"/>
						</td>
						<td>
							<%=((Producto)o[0]).getNombre()%>
						</td>
						<td>
							$<%=new DecimalFormat().format(((Producto)o[0]).getPrecio())%>
						</td>
						<td>
							<%=o[1]%>
						</td>
					</tr>
				<%
				}
				%>
				</table>
			</div>
		<%
		}
		%>
		</div>
		
	</div>
<%
	session.removeAttribute("errorDetallePedido");
	session.removeAttribute("datosPedido");
	session.removeAttribute("productosPedido");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Pedido?vista=detalle&id=" + request.getParameter("id"));
}
%>
</body>
</html>