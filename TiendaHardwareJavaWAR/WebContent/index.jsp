<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Index</title>
	<%@ include file='views/includes/head_styles.jsp' %>
	<%@ include file='views/includes/head_scripts.jsp' %>
	<%@ include file='views/includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "index") {
%>
	<%@ include file='views/includes/header.jsp' %>
		
	<div class="contenido">

		<div class="contenedor-paginacion-objetos">
		<%
		if (session.getAttribute("registros") == "Pagina Inexistente") {
		%>
			<h1 class="titulo-pagina-centrada">No se han encontrado resultados en esta página</h1>
		<%
		}
		else if (session.getAttribute("registros") == "Sin Registros") {
		%>
			<h1 class="titulo-pagina-centrada">No hay productos</h1>
		<%
		}
		else {
		%>
			<%=session.getAttribute("registros")%>
		<%
		}
		%>	
		</div>
		
		<div class="contenedor-paginacion-paginas">
			<%=session.getAttribute("numeros")%>
		</div>
		
	</div>
<%
	session.removeAttribute("registros");
	session.removeAttribute("numeros");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Index?pagina=" + request.getParameter("pagina"));
}
%>
</body>
</html>