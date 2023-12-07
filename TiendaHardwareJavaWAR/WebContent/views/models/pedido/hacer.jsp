<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hacer Pedido</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "hacer") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-formulario">
			<h1>Hacer Pedido</h1>
			<form action="<%=request.getContextPath()%>/Pedido" method="POST">
				<label for="ciudad">Ciudad</label>
				<input type="text" name="ciudad" required/>
				<label for="comuna">Comuna</label>
				<input type="text" name="comuna" required/>
				<label for="calle">Calle</label>
				<input type="text" name="calle" required/>
				<button type="submit" name="opcion" value="1">Hacer Pedido</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Pedido?vista=hacer");
}
%>
</body>
</html>