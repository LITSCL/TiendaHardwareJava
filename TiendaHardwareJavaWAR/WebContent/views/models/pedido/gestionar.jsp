<%@page import="java.text.DecimalFormat"%>
<%@page import="cl.litscl.tiendahardwareejb.model.Producto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestionar Pedido</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "gestionar") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-pagina-centrada">
			<h1 class="titulo-pagina-centrada">Gestionar el pedido</h1>
			<%
			if (session.getAttribute("datosPedido") != null) {
				Object[] datosPedido = (Object[])session.getAttribute("datosPedido");
				List<Object[]> productosPedido = (List<Object[]>)session.getAttribute("productosPedido");
			%>
				<br/>
				<h3>Cambiar estado del pedido:</h3>
				<form class="formulario-gestionar-pedido" action="<%=request.getContextPath()%>/Pedido" method="POST">
					<input type="hidden" name="id" value="<%=datosPedido[0]%>"/>
					<select name="estado">
						<option value="Confirmado" <% if (((String)datosPedido[2]).equals("Confirmado")) { out.print(" selected"); } else {  } %>>Confirmado</option>
						<option value="En preparación" <% if (((String)datosPedido[2]).equals("En preparación")) { out.print(" selected"); } else {  } %>>En preparación</option>
						<option value="Preparado para enviar" <% if (((String)datosPedido[2]).equals("Preparado para enviar")) { out.print(" selected"); } else {  } %>>Preparado para enviar</option>
						<option value="Enviado" <% if (((String)datosPedido[2]).equals("Enviado")) { out.print(" selected"); } else {  } %>>Enviado</option>
					</select>
					<button type="submit" name="opcion" value="2">Cambiar</button>
				</form>
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
	session.removeAttribute("datosPedido");
	session.removeAttribute("productosPedido");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Pedido?vista=gestionar&id=" + request.getParameter("id"));
}
%>
</body>
</html>