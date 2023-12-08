<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Iniciar Sesión</title>
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
			<h1>Iniciar Sesión</h1>
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
				<label for="clave">Contraseña</label>
				<input type="password" name="clave"/>
				<button type="submit" name="opcion" value="2">Iniciar Sesión</button>
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