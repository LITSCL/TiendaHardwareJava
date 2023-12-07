<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrarse</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "registrarse") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-formulario">
			<h1>Registrarse</h1>
			<%
			if (session.getAttribute("registro") == "Exitoso") {
			%>
				<strong class="alerta alerta-verde">Registro exitoso</strong>
			<%
			}
			if (session.getAttribute("registro") == "Fallido") {	
			%>
				<strong class="alerta alerta-roja">Registro fallido</strong>
			<%		
			}
			%>
			<form action="<%=request.getContextPath()%>/Usuario" method="POST">
				<label for="correo">Correo</label>
				<input type="email" name="correo" required/>
				<label for="clave">Contraseña</label>
				<input type="password" name="clave" required/>
				<label for="primerNombre">Primer Nombre</label>
				<input type="text" name="primerNombre" required/>
				<label for="segundoNombre">Segundo Nombre</label>
				<input type="text" name="segundoNombre" required/>
				<label for="apellidoPaterno">Apellido Paterno</label>
				<input type="text" name="apellidoPaterno" required/>
				<label for="apellidoMaterno">Apellido Materno</label>
				<input type="text" name="apellidoMaterno" required/>
				<button type="submit" name="opcion" value="1">Registrarse</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("registro");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Usuario?vista=registrarse");
}
%>
</body>
</html>