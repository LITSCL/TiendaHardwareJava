<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Iniciar Sesi�n</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "iniciarSesion") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-formulario">
			<h1>Iniciar Sesi�n</h1>
			<%
			if (session.getAttribute("errorLogin") == "Credenciales incorrectas") {
			%>
				<strong class="alerta alerta-roja"><%=session.getAttribute("errorLogin")%></strong>
			<%
			}
			%>
			<form action="<%=request.getContextPath()%>/Usuario" method="POST">
				<label for="correo">Correo</label>
				<input type="email" name="correo"/>
				<label for="clave">Contrase�a</label>
				<input type="password" name="clave"/>
				<button type="submit" name="opcion" value="2">Iniciar Sesi�n</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("errorLogin");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Usuario?vista=iniciar_sesion");
}
%>
</body>
</html>