<%@page import="cl.litscl.tiendahardwareejb.model.Proveedor"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modificar Proveedor</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "modificar") {
	Proveedor p = (Proveedor)session.getAttribute("proveedor");
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-formulario">
			<h1>Crear Proveedor</h1>
			<%
			if (session.getAttribute("modificarProveedor") == "Exitoso") {
			%>
				<strong class="alerta alerta-verde">Proveedor modificado exitosamente</strong>
			<%
			}
			if (session.getAttribute("modificarProveedor") == "Fallido") {	
			%>
				<strong class="alerta alerta-roja">Error al modificar el proveedor</strong>
			<%		
			}
			%>
			<form action="<%=request.getContextPath()%>/Proveedor" method="POST">
				<input type="hidden" name="id" value="<%=p.getId()%>" readonly/>
				<label for="nombre">Nombre</label>
				<input type="text" name="nombre" value="<%=p.getNombre()%>" required/>
				<label for="telefono">Telefono</label>
				<input type="text" name="telefono" value="<%=p.getTelefono()%>" required/>
				<label for="correo">Correo</label>
				<input type="email" name="correo" value="<%=p.getCorreo()%>" required/>
				<button type="submit" name="opcion" value="2">Modificar</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("proveedor");
	session.removeAttribute("modificarProveedor");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Proveedor?vista=modificar&id=" + request.getParameter("id"));
}
%>
</body>
</html>