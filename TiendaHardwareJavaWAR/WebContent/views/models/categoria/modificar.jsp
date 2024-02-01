<%@page import="cl.litscl.tiendahardwareejb.model.Categoria"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Modificar Categoría</title>
	<%@ include file='../../includes/head_styles.jsp' %>
	<%@ include file='../../includes/head_scripts.jsp' %>
	<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "modificar") {
	Categoria categoria = (Categoria)session.getAttribute("categoria");
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-formulario">
			<h1>Modificar Categoría</h1>
			<%
			if (session.getAttribute("modificarCategoria") == "Exitoso") {
			%>
				<strong class="alerta alerta-verde">Categoría modificada exitosamente</strong>
			<%
			}
			if (session.getAttribute("modificarCategoria") == "Fallido") {	
			%>
				<strong class="alerta alerta-roja">Error al modificar la categoría</strong>
			<%		
			}
			%>
			<form action="<%=request.getContextPath()%>/Categoria" method="POST">
				<input type="hidden" name="id" value="<%=categoria.getId()%>" readonly/>
				<label for="nombre">Nombre</label>
				<input type="text" name="nombre" value="<%=categoria.getNombre()%>" required/>
				<button type="submit" name="opcion" value="2">Modificar</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("modificarCategoria");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Categoria?vista=modificar&id=" + request.getParameter("id"));
}
%>
</body>
</html>