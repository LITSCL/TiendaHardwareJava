<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Crear Categor�a</title>
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
			<h1>Crear Categor�a</h1>
			<%
			if (session.getAttribute("crearCategoria") == "Exitoso") {
			%>
				<strong class="alerta alerta-verde">Categor�a creada exitosamente</strong>
			<%
			}
			if (session.getAttribute("crearCategoria") == "Fallido") {	
			%>
				<strong class="alerta alerta-roja">Error al crear la categor�a</strong>
			<%		
			}
			%>
			<form action="<%=request.getContextPath()%>/Categoria" method="POST">
				<label for="nombre">Nombre</label>
				<input type="text" name="nombre" required/>
				<button type="submit" name="opcion" value="1">Crear</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("crearCategoria");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Categoria?vista=crear");
}
%>
</body>
</html>