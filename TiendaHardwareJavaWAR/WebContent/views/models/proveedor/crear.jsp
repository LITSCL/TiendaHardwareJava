<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Crear Proveedor</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "crear") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-formulario">
			<h1>Crear Proveedor</h1>
			<%
			if (session.getAttribute("crearProveedor") == "Exitoso") {
			%>
				<strong class="alerta alerta-verde">Proveedor creado exitosamente</strong>
			<%
			}
			if (session.getAttribute("crearProveedor") == "Fallido") {	
			%>
				<strong class="alerta alerta-roja">Error al crear el proveedor</strong>
			<%		
			}
			%>
			<form action="<%=request.getContextPath()%>/Proveedor" method="POST">
				<label for="nombre">Nombre</label>
				<input type="text" name="nombre" required/>
				<label for="telefono">Telefono</label>
				<input type="text" name="telefono" required/>
				<label for="correo">Correo</label>
				<input type="email" name="correo" required/>
				<button type="submit" name="opcion" value="1">Crear</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("crearProveedor");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Proveedor?vista=crear");
}
%>
</body>
</html>