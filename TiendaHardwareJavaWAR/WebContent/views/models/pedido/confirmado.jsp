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
	<title>Pedido Confirmado</title>
	<%@ include file='../../includes/head_styles.jsp' %>
	<%@ include file='../../includes/head_scripts.jsp' %>
	<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "confirmado") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div id="contenedorPedidoConfirmado" class="contenedor-pagina-centrada">
			<h1 class="titulo-pagina-centrada">Resultado del pedido</h1>
		<%
		if (session.getAttribute("crearPedido") == "Exitoso") {
		%>
			<strong class="alerta alerta-verde">Pedido realizado exitosamente</strong>
			<p>
				Una vez que recibamos tu pago en la cuenta bancaria <strong>56283715001</strong>, el pedido será procesado y despachado a tu domicilio.
			</p>
			<%
			if (session.getAttribute("datosPedido") != null) {
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
		<%
		}
		if (session.getAttribute("crearPedido") == "Fallido") {
		%>
			<strong class="alerta alerta-roja">Error al realizar el pedido</strong>
			<p>
				Por favor, intenta realizar el pedido mas tarde.
			</p>
		<%
		}
		%>
		</div>
		
	</div>
<%
	session.removeAttribute("crearPedido");
	session.removeAttribute("datosPedido");
	session.removeAttribute("productosPedido");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Pedido?vista=confirmado");
}
%>
</body>
</html>